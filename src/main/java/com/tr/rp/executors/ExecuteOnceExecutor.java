package com.tr.rp.executors;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

/**
 * Executor that calls a given function upon the first close/push call.
 */
public final class ExecuteOnceExecutor implements Executor {

	private final Callable callable;
	private boolean called = false;
	
	public ExecuteOnceExecutor(Callable callable) {
		this.callable = callable;
	}
	
	@Override
	public void close() throws RPLException {
		if (!called) {
			called = true;
			callable.call();
		}
	}

	@Override
	public void push(State s) throws RPLException {
		if (!called) {
			called = true;
			callable.call();
		}
	}
	
	public interface Callable {
		public void call() throws RPLException;
	}

}
