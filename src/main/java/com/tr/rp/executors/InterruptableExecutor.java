package com.tr.rp.executors;

import java.util.function.BooleanSupplier;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLInterruptedException;

/**
 * This executor can be used to implement asynchronous termination of 
 * execution. The input is passed on to another executor unchanged, but 
 * after every push event, an interrupt condition is checked. If the 
 * condition is true, an RPLInterruptedException is thrown.
 */
public final class InterruptableExecutor implements Executor {

	private final Executor out;
	private final BooleanSupplier interruptCondition;
	
	public InterruptableExecutor(Executor out, BooleanSupplier interruptCondition) {
		this.out = out;
		this.interruptCondition = interruptCondition;
	}
	
	@Override
	public void close() throws RPLException {
		out.close();
	}

	@Override
	public void push(State s) throws RPLException {
		if (interruptCondition.getAsBoolean()) {
			throw new RPLInterruptedException();
		}
		out.push(s);;
	}

}
