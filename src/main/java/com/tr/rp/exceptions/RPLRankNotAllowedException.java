package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;

public class RPLRankNotAllowedException extends RPLException {

	public RPLRankNotAllowedException(AbstractExpression expression, AbstractStatement statement) {
		this.setExpression(expression);
		this.setStatement(statement);
	}

	public String getDescription() {
		return "Rank expression not allowed in " + getExpression();
	}
	
}
