package com.bank.evolve.service.impl;

import com.bank.evolve.entity.PasswordResetToken;
import com.bank.evolve.entity.User;
import com.bank.evolve.repository.PasswordResetTokenRepository;
import com.bank.evolve.repository.UserRepository;
import com.bank.evolve.service.EmailService;
import com.bank.evolve.service.PasswordResetService;
import com.bank.evolve.util.AppError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    
    private static final int TOKEN_EXPIRY_MINUTES = 15;
    private static final int OTP_LENGTH = 6;

    @Override
    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppError("Usuário não encontrado com este email", HttpStatus.NOT_FOUND));

        if (!user.getIsActive()) {
            throw new AppError("Conta inativa. Entre em contato com o suporte", HttpStatus.BAD_REQUEST);
        }

        passwordResetTokenRepository.invalidateAllTokensForUser(user);

        String otpCode = generateOTP();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(otpCode);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES));
        resetToken.setUsed(false);
        resetToken.setCreatedAt(LocalDateTime.now());

        passwordResetTokenRepository.save(resetToken);

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), otpCode);
            log.info("Código OTP de recuperação de senha enviado para o usuário: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Erro ao enviar email de recuperação para {}: {}", user.getEmail(), e.getMessage());
            throw new AppError("Erro ao enviar email de recuperação. Tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        passwordResetTokenRepository.deleteExpiredTokens(LocalDateTime.now());

        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenAndUsedFalse(token)
                .orElseThrow(() -> new AppError("Código OTP inválido ou expirado", HttpStatus.BAD_REQUEST));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppError("Código OTP expirado", HttpStatus.BAD_REQUEST);
        }

        if (resetToken.isUsed()) {
            throw new AppError("Código OTP já foi utilizado", HttpStatus.BAD_REQUEST);
        }

        User user = resetToken.getUser();

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new AppError("Nova senha não pode estar vazia", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(newPassword)) {
            throw new AppError("A senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial (@, #, $, %, &, *)", HttpStatus.BAD_REQUEST);
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        log.info("Senha resetada com sucesso para o usuário: {}", user.getEmail());
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        
        return otp.toString();
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).{8,}$";
        return password.matches(passwordPattern);
    }
}
