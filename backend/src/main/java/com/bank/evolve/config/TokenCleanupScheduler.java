package com.bank.evolve.config;

import com.bank.evolve.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void cleanupExpiredTokens() {
        try {
            passwordResetTokenRepository.deleteExpiredTokens(LocalDateTime.now());
            log.debug("Limpeza de tokens expirados executada com sucesso");
        } catch (Exception e) {
            log.error("Erro durante a limpeza de tokens expirados: {}", e.getMessage());
        }
    }
}
