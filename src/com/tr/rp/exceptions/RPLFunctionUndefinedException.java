package com.tr.rp.exceptions;

public class RPLFunctionUndefinedException extends RPLException {

	private final String functionName;
	
	public RPLFunctionUndefinedException(String functionName) {
		this.functionName = functionName;
	}
	
	public String getDescription() {
		return "Function " + functionName + " undefined.";
	}
	
}
