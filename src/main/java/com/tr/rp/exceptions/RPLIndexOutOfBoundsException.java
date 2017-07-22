package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;

public class RPLIndexOutOfBoundsException extends RPLException {

	private final int index;
	private final int size;
	private boolean hasIndexAndSize;
	
	public RPLIndexOutOfBoundsException(int index, int size, AbstractExpression e) {
		this.index = index;
		this.size = size;
		this.hasIndexAndSize = true;
		setExpression(e);
	}
	
	public RPLIndexOutOfBoundsException(int index, int size, AbstractStatement s) {
		this.index = index;
		this.size = size;
		this.hasIndexAndSize = true;
		setStatement(s);
	}

	public RPLIndexOutOfBoundsException() {
		this.index = -1;
		this.size = -1;
		this.hasIndexAndSize = false;
	}
	
	public String getDescription() {
		return "Array or string index out of bounds" +
				(hasIndexAndSize? (" (referencing index " + index + " of array or string of length " + size + ")"): "") + ".";
	}
}
