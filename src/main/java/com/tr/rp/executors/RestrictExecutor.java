package com.tr.rp.executors;

import java.util.function.Consumer;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

/**
 * An executor that is constructed with an output executor, and passes on only 
 * states with a rank equal to or less than a given maxRank value. Any "cutoff 
 * event" (i.e., a state not being passed on because its rank exceeds maxRank) is
 * registered with a given cutOffListener.
 */
public final class RestrictExecutor implements Executor {

	private final int maxRank;
	private final Executor out;
	private final Consumer<Integer> cutOffListener;
	private boolean closed = false;
	
	public RestrictExecutor(int maxRank, Executor out, Consumer<Integer> cutOffListener) {
		this.maxRank = maxRank;
		this.out = out;
		this.cutOffListener = cutOffListener;
	}
	
	@Override
	public void close() throws RPLException {
		if (!closed) {
			out.close();
			closed = true;
		}
	}
	
	@Override
	public void push(State s) throws RPLException {
		if (closed) {
			return;
		}
		if (s.getRank() >= maxRank) {
			cutOffListener.accept(s.getRank());
			out.close();
			closed = true;
		} else {
			out.push(s);
		}
	}

}
