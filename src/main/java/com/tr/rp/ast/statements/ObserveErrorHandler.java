package com.tr.rp.ast.statements;

import com.tr.rp.exceptions.RPLException;

public interface ObserveErrorHandler {

	public void observeConditionError(RPLException e) throws RPLException;

}
