package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;

public class RPLTypeMismatchException extends RPLException {

	private final Object o1, o2;
	private final AbstractExpression e;
	
	public RPLTypeMismatchException(Object o1, Object o2, AbstractExpression e) {
		this.o1 = o1;
		this.o2 = o2;
		this.e = e;
	}
	
	public String getDescription() {
		return "Type mismatch (" + o1.getClass().getSimpleName() + " and " + o2.getClass().getSimpleName() + ") in expression " + e;
	}
	
}
