package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.expressions.Variable;

public class RPLMissingKeyException extends RPLException {

	private final Object missingKey;
	
	public RPLMissingKeyException(AbstractExpression e, Object missingKey) {
		setExpression(e);
		this.missingKey = missingKey;
	}
	
	public String getDescription() {
		return "Map does not contain key " + missingKey;
	}
	
}
