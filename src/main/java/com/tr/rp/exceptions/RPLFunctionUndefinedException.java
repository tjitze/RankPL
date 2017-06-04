package com.tr.rp.exceptions;

import com.tr.rp.expressions.FunctionCall;

public class RPLFunctionUndefinedException extends RPLException {

	private final String functionName;
	
	public RPLFunctionUndefinedException(String functionName) {
		this.functionName = functionName;
	}
	
	public RPLFunctionUndefinedException(String functionName, FunctionCall fc) {
		this.functionName = functionName;
		setExpression(fc);
	}
	
	public String getDescription() {
		return "Function " + functionName + " undefined.";
	}
	
}
