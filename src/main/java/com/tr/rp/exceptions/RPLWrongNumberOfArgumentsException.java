package com.tr.rp.exceptions;

public class RPLWrongNumberOfArgumentsException extends RPLException {

	private final String functionName;
	private final int paramsLength;
	private final int argsLength;
	
	public RPLWrongNumberOfArgumentsException(String functionName, int paramsLength, int argsLength) {
		this.functionName = functionName;
		this.paramsLength = paramsLength;
		this.argsLength = argsLength;
	}

	public String getDescription() {
		return "Wrong number of arguments supplied to function " + functionName + ". Number of parameters: " + paramsLength + ". Number of supplied arguments: " + argsLength + ".";
	}
	
}
