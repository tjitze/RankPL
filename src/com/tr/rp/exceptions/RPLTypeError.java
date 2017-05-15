package com.tr.rp.exceptions;

public class RPLTypeError extends RPLException {

	private final String expectedType;
	private final Object found;
	
	public RPLTypeError(String expectedType, Object found) {
		this.expectedType = expectedType;
		this.found = found;
	}
	
	public String getDescription() {
		return "Type error: expected : " + expectedType + ", found " + found;
	}

	public String getExpectedType() {
		return expectedType;
	}
	
	public String getFound() {
		return found.toString();
	}
	
}
