package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;

public class RPLIllegalRankException extends RPLException {

	private final int rank;
	
	public RPLIllegalRankException(int rank, AbstractExpression expression, AbstractStatement statement) {
		this.rank = rank;
		this.setExpression(expression);
		this.setStatement(statement);
	}
	
	public RPLIllegalRankException(AbstractStatement statement) {
		this.rank = 0;
		this.setStatement(statement);
	}

	public String getDescription() {
		return "Illegal rank (" + rank + ")";
	}
	
}
