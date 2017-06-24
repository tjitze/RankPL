package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractStatement;

public class RPLAssertionException extends RPLException {

	private final String description;
	
	public RPLAssertionException(String description, AbstractStatement s) {
		this.description = description;
		setStatement(s);
	}

	@Override
	public String getDescription() {
		return description;
	}

}
