package com.tr.rp.exceptions;

import com.tr.rp.ast.statements.Assert;

public class RPLAssertionException extends RPLException {

	private final String description;
	
	public RPLAssertionException(String description, Assert s) {
		this.description = description;
		setStatement(s);
	}

	@Override
	public String getDescription() {
		return description;
	}

}
