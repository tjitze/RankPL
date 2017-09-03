package com.tr.rp.varstore;

public class FreeVarNameProvider {
	
	// Global counter to generate unique variable names
	private volatile static long rwCounter = 0;
	
	/**
	 * Returns a unique variable name based on the original variable name.
	 * This uses an internal static counter, whose value is included in the
	 * variable name, to avoid collisions. It is guaranteed that this method
	 * never returns the same name (except possibly in case of counter 
	 * overflow, which ¯should not occur in practice). All names returned
	 * start with "$".
	 * 
	 * @param original Original variable name
	 * @return A unique variable name based on original
	 */
	public static String getFreeVariable(String original) {
		return "$" + (rwCounter++) + "_" + original;
	}
}
