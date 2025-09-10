package com.bank.evolve.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String token);
}
