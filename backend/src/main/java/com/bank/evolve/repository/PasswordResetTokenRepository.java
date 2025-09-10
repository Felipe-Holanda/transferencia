package com.bank.evolve.repository;

import com.bank.evolve.entity.PasswordResetToken;
import com.bank.evolve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    Optional<PasswordResetToken> findByTokenAndUsedFalse(String token);
    
    Optional<PasswordResetToken> findByUserAndUsedFalse(User user);
    
    @Modifying
    @Query("DELETE FROM PasswordResetToken p WHERE p.expiryDate < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
    
    @Modifying
    @Query("UPDATE PasswordResetToken p SET p.used = true WHERE p.user = :user")
    void invalidateAllTokensForUser(@Param("user") User user);
}
