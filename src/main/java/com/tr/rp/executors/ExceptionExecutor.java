package com.tr.rp.executors;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

/**
 * Executor that throws a given exception upon the first close/push call.
 */
public final class ExceptionExecutor implements Executor {

	private final RPLException exception;
	
	public ExceptionExecutor(RPLException exception) {
		this.exception = exception;
	}
	
	@Override
	public void close() throws RPLException {
		throw exception;
	}

	@Override
	public void push(State s) throws RPLException {
		throw exception;
	}

}
