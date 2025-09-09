package com.bank.evolve.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
}
