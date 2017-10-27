package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;

public class RPLIllegalRangeException extends RPLException {

	private final int begin;
	private final int end;
	
	public RPLIllegalRangeException(int begin, int end, AbstractExpression expression) {
		this.begin = begin;
		this.end = end;
		this.setExpression(expression);
	}

	public String getDescription() {
		return "Illegal range: " + begin + " (inclusive) " + end + " (exclusive)";
	}
	
}
