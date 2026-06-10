package com.example.hospital;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String[] passwords = { "123456", "zhang123", "li123", "wang123", "liu123" };

		for (String rawPass : passwords) {
			System.out.println(rawPass + " => " + encoder.encode(rawPass));
		}
	}
}
