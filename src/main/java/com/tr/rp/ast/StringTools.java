package com.tr.rp.ast;

public class StringTools {

	public static String stripPars(String input) {
		while (input.startsWith("(") && input.endsWith(")")) {
			input = input.substring(1, input.length() - 1);
		}
		return input;
	}
}
