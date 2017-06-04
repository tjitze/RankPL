package com.tr.rp.exceptions;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.statement.Composition;

public abstract class RPLException extends Exception {

	private Expression expression;

	private DStatement statement;

	/**
	 * Set statement from which this exception originates.
	 */
	public void setStatement(DStatement statement) {
		if (statement.toString().equals("if (isset($return)) then skip else return a")) {
			throw new RuntimeException();
		}
		this.statement = statement;
	}
	
	/**
	 * Set expression from which this exception originates.
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	/**
	 * @return Statement from which this exception originates.
	 */
	public DStatement getStatement() {
		return statement;
	}

	/**
	 * @return Expression from which this exception originates;
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @return Description of exception without reference to statement
	 */
	public abstract String getDescription();
		
	public String toString() {
		return getDescription();
	}

}
