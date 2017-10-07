package com.tr.rp.exceptions;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;

public abstract class RPLException extends Exception {

	private AbstractExpression expression;

	private AbstractStatement statement;
	
	private static final int MAX_STATEMENT_STRING_LENGTH = 50;
	
	/**
	 * Set statement from which this exception originates.
	 */
	public void setStatement(AbstractStatement statement) {
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
	
	public void printDescription() {
		System.out.println("Exception: " + getDescription());
		String info = "";
		if (getExpression() != null) {
			info += "In expression " + getExpression();
		}
		if (getStatement() != null) {
			String statementString = getStatement().toString();
			if (statementString.length() > MAX_STATEMENT_STRING_LENGTH) {
				statementString = statementString.substring(0, MAX_STATEMENT_STRING_LENGTH) + "...";
			}
			info += (info.equals("") ? "In" : ", in") + " statement " + getStatement();
			if (getStatement().getLineNumber() != -1) {
				info += ", on line " + getStatement().getLineNumber();
			}
		}
		if (info.length() > 0) info += ".";
		System.out.println(info);
	}

}
