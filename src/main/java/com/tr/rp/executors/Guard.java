package com.tr.rp.executors;

import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

public final class Guard implements Executor {

	private final Executor out;
	private boolean closed = false;
	private int rank = 0;
	
	public Guard(Executor out) {
		this.out = out;
	}
	
	@Override
	public void close() throws RPLException {
		if (closed) {
			throw new IllegalStateException("Close called on closed executor");
		}
		closed = true;
		out.close();
	}

	@Override
	public void push(State s) throws RPLException {
		if (closed) {
			throw new IllegalStateException("Push called on closed executor");
		}
		if (s.getRank() < rank) {
			throw new IllegalStateException("Illegal rank order (got " + s.getRank() + " but should be at least " + rank + ")");
		}
		rank = s.getRank();
		out.push(s);
	}

}
