package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;

public class RPLMiscException extends RPLException {

	private String description;
	
	public RPLMiscException(String description) {
		this.description = description;
	}
	
	public RPLMiscException(String description, AbstractStatement s) {
		this(description);
		setStatement(s);
	}

	public RPLMiscException(String description, AbstractExpression e) {
		this(description);
		setExpression(e);
	}

	public String getDescription() {
		return description;
	}
	
}
