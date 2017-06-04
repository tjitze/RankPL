package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.statements.Composition;

public abstract class RPLException extends Exception {

	private AbstractExpression expression;

	private AbstractStatement statement;

	/**
	 * Set statement from which this exception originates.
	 */
	public void setStatement(AbstractStatement statement) {
		if (statement.toString().equals("if (isset($return)) then skip else return a")) {
			throw new RuntimeException();
		}
		this.statement = statement;
	}
	
	/**
	 * Set expression from which this exception originates.
	 */
	public void setExpression(AbstractExpression expression) {
		this.expression = expression;
	}
	
	/**
	 * @return Statement from which this exception originates.
	 */
	public AbstractStatement getStatement() {
		return statement;
	}

	/**
	 * @return Expression from which this exception originates;
	 */
	public AbstractExpression getExpression() {
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
