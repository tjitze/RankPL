package com.tr.rp.exceptions;

import com.tr.rp.core.Function;

public class RPLMissingReturnValueException extends RPLException {

	private final Function f;
	
	public RPLMissingReturnValueException(Function f) {
		this.f = f;
	}
	
	public String getDescription() {
		return "Function " + f.getName() + " is missing return statement.";
	}
	
}
