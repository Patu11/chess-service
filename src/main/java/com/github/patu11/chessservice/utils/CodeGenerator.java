package com.github.patu11.chessservice.utils;

public class CodeGenerator {

	public static String generateCode() {
		String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String LOWER = "abcdefghijklmnopqrstuvwxyz";
		String NUMBERS = "0123456789";
		String ALPHA = UPPER + LOWER + NUMBERS;
		int LENGTH = 6;

		StringBuilder code = new StringBuilder();

		for (int i = 0; i < LENGTH; i++) {
			code.append(ALPHA.charAt((int) Math.floor(Math.random() * ALPHA.length())));
		}
		return code.toString();
	}
}
