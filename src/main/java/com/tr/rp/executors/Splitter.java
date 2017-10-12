package com.tr.rp.executors;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

/**
 * An executor that is constructed with two output executors. All
 * push/close calls called on this executor or reproduced on the
 * two input executors.
 */
public final class Splitter implements Executor {

	private final Executor out1;
	private final Executor out2;
	
	public Splitter(Executor out1, Executor out2) {
		this.out1 = out1;
		this.out2 = out2;
	}

	@Override
	public void close() throws RPLException{
		out1.close();
		out2.close();
	}

	@Override
	public void push(State s) throws RPLException{
		out1.push(s);
		out2.push(s);
	}

}
