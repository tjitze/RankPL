package com.tr.rp.executors;

import java.util.LinkedList;
import java.util.PriorityQueue;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRankException;
import com.tr.rp.varstore.types.Type;

public final class DynamicMerger {

	private final Executor out;
	private final Executor in1;
	private final Executor in2;
	private boolean in1Closed = false;
	private boolean in2Closed = false;
	
	private final PriorityQueue<State> inQueue = new PriorityQueue<State>((x, y) -> x.getRank() - y.getRank());
	
	private int outputOffset = -1;
	
	private int safeOutRank = 0;
	
	private final AbstractStatement exceptionSource;
	
	public DynamicMerger(Executor out, AbstractExpression shift, AbstractStatement exceptionSource) {
		this.exceptionSource = exceptionSource;
		this.out = new Executor() {

			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				if (outputOffset == -1) {
					outputOffset = s.getRank();
				}
				out.push(s.shiftDown(outputOffset));
			}
			
		};
		this.in1 = new Executor() {

			@Override
			public void close() throws RPLException {
				in1Closed = true;
				safeOutRank = Rank.MAX;
				flush();
			}

			@Override
			public void push(State s) throws RPLException {
				if (in1Closed) {
					throw new IllegalStateException();
				}
				inQueue.add(s);
				safeOutRank = Math.max(safeOutRank, s.getRank());
				flush();
			}
			
		};
		this.in2 = new Executor() {

			@Override
			public void close() throws RPLException {
				in2Closed = true;
				flush();
			}

			@Override
			public void push(State s) throws RPLException {
				if (in2Closed) {
					throw new IllegalStateException();
				}
				int shiftValue = shift.getValue(s.getVarStore(), Type.INT);
				if (shiftValue < 0) {
					throw new RPLIllegalRankException(shiftValue, shift, exceptionSource);
				}
				inQueue.add(s.shiftUp(shiftValue));
				flush();
			}
			
		};
	}
	
	private void flush() throws RPLException {
		while (!inQueue.isEmpty() && inQueue.peek().getRank() <= safeOutRank) {
			out.push(inQueue.remove());
		}
		if (in1Closed && in2Closed && inQueue.isEmpty()) {
			out.close();
		}
	}
	
	public Executor getIn1() {
		return in1;
	}

	public Executor getIn2() {
		return in2;
	}
	
}
