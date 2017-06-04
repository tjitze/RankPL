package com.tr.rp.exceptions;

import com.tr.rp.core.Expression;
import com.tr.rp.expressions.Variable;

public class RPLUndefinedException extends RPLException {

	public RPLUndefinedException(Expression e) {
		setExpression(e);
	}
	
	public RPLUndefinedException(String var) {
		setExpression(new Variable(var));
	}
	
	public String getDescription() {
		return "Undefined value.";
	}
	
}
