package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;

public class RPLWrongAssignmentTargetSize extends RPLException {

	private final int expected;
	private final int found;
	
	public RPLWrongAssignmentTargetSize(int expected, int found, AbstractExpression expression) {
		this.expected = expected;
		this.found = found;
		this.setExpression(expression);
	}
	
	public String getDescription() {
		return "Attempt to assign a " + found + "-sized array of values to a " + expected + "-sized array of variables.";
	}
	
}
