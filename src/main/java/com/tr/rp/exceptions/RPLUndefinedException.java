package com.tr.rp.exceptions;

import com.tr.rp.core.Expression;
import com.tr.rp.expressions.Variable;

public class RPLUndefinedException extends RPLException {

	private final Expression e;
	
	public RPLUndefinedException(Expression e) {
		this.e = e;
	}
	
	public RPLUndefinedException(String var) {
		this.e = new Variable(var);
	}
	
	public String getDescription() {
		return "Expression " + e + " has an undefined value.";
	}
	
}
