package com.tr.rp.exec;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

public abstract class MultiMergeExecutor implements Executor {

	private final Executor out;
	
	private int inRank = 0;
	
	private PriorityQueue<State> queue = new PriorityQueue<State>((x, y) -> x.getRank() - y.getRank());
	private boolean closed = false;
	
	public MultiMergeExecutor(Executor out) {
		this.out = out;
	}

	@Override
	public void close() throws RPLException {
		closed = true;
		inRank = Rank.MAX;
		flush();
		out.close();
	}

	@Override
	public void push(State s) throws RPLException {
		if (closed) {
			throw new IllegalStateException();
		}
		inRank = s.getRank();
		AtomicReference<Boolean> closed = new AtomicReference<Boolean>(false);
		transform(s.getVarStore(), new Executor() {

			@Override
			public void close() throws RPLException {
				closed.set(true);
				flush();
			}

			@Override
			public void push(State t) throws RPLException {
				queue.add(t.shiftUp(s.getRank()));
				flush();
			}
			
		});
		if (!closed.get()) {
			throw new IllegalStateException();
		}
	}

	private void flush() throws RPLException {
		while (!queue.isEmpty() && queue.peek().getRank() <= inRank) {
			out.push(queue.remove());
		}
	}
	
	/**
	 * is called for all input var stores
	 * result of function call with that var store is pushed to out
	 */
	public abstract void transform(VarStore in, Executor out) throws RPLException;

	
}
