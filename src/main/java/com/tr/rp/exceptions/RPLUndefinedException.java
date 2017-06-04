package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.expressions.Variable;

public class RPLUndefinedException extends RPLException {

	public RPLUndefinedException(AbstractExpression e) {
		setExpression(e);
	}
	
	public RPLUndefinedException(String var) {
		setExpression(new Variable(var));
	}
	
	public String getDescription() {
		return "Undefined value.";
	}
	
}
