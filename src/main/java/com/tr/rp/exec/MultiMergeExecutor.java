package com.tr.rp.exec;

import java.util.PriorityQueue;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

public abstract class MultiMergeExecutor implements Executor {

	private final Executor out;
	private final PriorityQueue<State> outQueue = new PriorityQueue<State>((x, y) -> x.getRank() - y.getRank());

	private State ps;

	public MultiMergeExecutor(Executor out) {
		this.out = out;
	}

	@Override
	public void close() throws RPLException {
		if (ps != null) {
			execute(ps, Rank.MAX);
		} else {
			flush(Rank.MAX);
		}
		out.close();
	}

	@Override
	public void push(State s) throws RPLException {
		if (ps != null) {
			execute(ps, s.getRank());
		}
		ps = s;
	}

	private void execute(State ps, int safeOutRank) throws RPLException {
		int thisRank = ps.getRank();
		Executor exec = new Executor() {
			@Override
			public void close() throws RPLException {
				flush(safeOutRank);
			}

			@Override
			public void push(State ss) throws RPLException {
				State ks = ss.shiftUp(thisRank);
				outQueue.add(ks);
				flush(Math.min(ks.getRank(), safeOutRank));
			}
		};
		transform(ps.getVarStore(), exec);
	}
	
	int outRank = 0;
	
	private void flush(int until) throws RPLException {
		while (!outQueue.isEmpty() && outQueue.peek().getRank() <= until) {
			State s = outQueue.remove();
			if (s.getRank() < outRank) {
				throw new IllegalStateException();
			}
			outRank = s.getRank();
			out.push(s);
		}
	}
	
	/**
	 * is called for all input var stores
	 * result of function call with that var store is pushed to out
	 */
	public abstract void transform(VarStore in, Executor out) throws RPLException;

	
}
