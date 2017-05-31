package com.tr.rp.exceptions;

import com.tr.rp.core.Expression;

public class RPLUndefinedException extends RPLException {

	private final Expression e;
	
	public RPLUndefinedException(Expression e) {
		this.e = e;
	}
	
	public String getDescription() {
		return "Expression " + e + " has an undefined value.";
	}
	
}
