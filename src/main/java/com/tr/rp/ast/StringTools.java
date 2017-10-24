package com.tr.rp.ast;

public class StringTools {

	/**
	 * Remove enclosing parentheses if present
	 */
	public static String stripPars(String input) {
		while (input.startsWith("(") && input.endsWith(")")) {
			input = input.substring(1, input.length() - 1);
		}
		return input;
	}
	
	/**
	 * Add enclosing parentheses if absent
	 */
	public static String addPars(String input) {
		if (!input.startsWith("(") && !input.endsWith(")")) {
			input = "(" + input + ")";
		}
		return input;
	}
}

