package com.bank.evolve.service.impl;

import com.bank.evolve.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Recuperação de Senha - Banco Evolve");
            
            String emailContent = String.format(
                "Olá,\n\n" +
                "Você solicitou a recuperação de sua senha.\n\n" +
                "Seu código de verificação é: %s\n\n" +
                "Este código é válido por 15 minutos.\n\n" +
                "Se você não solicitou esta recuperação, ignore este email.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Banco Evolve",
                token
            );
            
            message.setText(emailContent);
            
            mailSender.send(message);
            log.info("Email de recuperação de senha enviado para: {}", to);
            
        } catch (Exception e) {
            log.error("Erro ao enviar email de recuperação de senha para {}: {}", to, e.getMessage());
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }
}
