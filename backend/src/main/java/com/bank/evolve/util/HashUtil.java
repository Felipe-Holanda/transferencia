package com.bank.evolve.util;

import jdk.jfr.Timestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;
import java.time.Instant;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class HashUtil {
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private HashUtil() {
	}

	public static String hashPassword(String plainPassword) {
		return encoder.encode(plainPassword);
	}

	public static boolean checkPassword(String plainPassword, String hashedPassword) {
		return encoder.matches(plainPassword, hashedPassword);
	}

	public static String transactionHash(String senderAccount, String targetAccount, double amount) {
		String timestamp = Instant.now().toString();
		String data = senderAccount + "," + targetAccount + "," + amount + "-" + timestamp;

		try {
			// Cria o hash SHA-256
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

			// Converte para Base64 para ficar legível
			return Base64.getEncoder().encodeToString(hash);

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro ao gerar hash da transação");
		}
	}
}
