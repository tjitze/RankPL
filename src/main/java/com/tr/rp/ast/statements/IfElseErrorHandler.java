package com.tr.rp.ast.statements;

import com.tr.rp.exceptions.RPLException;

public interface IfElseErrorHandler {

	public void ifElseConditionError(RPLException e) throws RPLException;
	
	public void ifElseThenError(RPLException e) throws RPLException;
	
	public void ifElseElseError(RPLException e) throws RPLException;

}
