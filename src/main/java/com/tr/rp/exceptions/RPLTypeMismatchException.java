package com.tr.rp.exceptions;

import com.tr.rp.core.Expression;

public class RPLTypeMismatchException extends RPLException {

	private final Object o1, o2;
	private final Expression e;
	
	public RPLTypeMismatchException(Object o1, Object o2, Expression e) {
		this.o1 = o1;
		this.o2 = o2;
		this.e = e;
	}
	
	public String getDescription() {
		return "Type mismatch (" + o1.getClass().getSimpleName() + " and " + o2.getClass().getSimpleName() + ") in expression " + e;
	}
	
}
