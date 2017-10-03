package com.tr.rp.executors;

import java.util.function.BooleanSupplier;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLInterruptedException;

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
