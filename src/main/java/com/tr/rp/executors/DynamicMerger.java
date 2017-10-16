package com.tr.rp.executors;

import java.util.PriorityQueue;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.statements.IfElse;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRankException;
import com.tr.rp.varstore.types.Type;

/**
 * DynamicMerger is constructed with a given output executor and produces two 
 * input executors. States pushed into the two input executors are merged
 * (in such a way that rank order is respected) and pushed into the output 
 * executor. 
 * 
 * In addition, DynamicMerger allows the ranks of the states pushed into
 * the second iterator the be shifted up by a value determined by a given
 * expression. This is the basic functionality required by the ranked 
 * choice statement.
 * 
 * If the shift parameter is known to be a constant value, the Merger class
 * should be used instead of DynamicMerger, as it is more efficient.
 */

public class DynamicMerger {

	private final Executor out;
	private final Executor in1;
	private final Executor in2;
	private boolean in1Closed = false;
	private boolean in2Closed = false;
	
	private final PriorityQueue<State> inQueue = new PriorityQueue<State>((x, y) -> x.getRank() - y.getRank());
	
	private int outputOffset = -1;
	
	private int safeOutRank = 0;
		
	public DynamicMerger(Executor out, Supplier<AbstractExpression> shift) {
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
				int shiftValue;
				try {
					shiftValue = shift.get().getValue(s.getVarStore(), Type.INT);
				} catch (RPLException e) {
					handleRankExpressionException(e);
					return;
				}
				if (shiftValue < 0) {
					handleRankExpressionException(new RPLIllegalRankException(shiftValue, shift.get()));
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
	
	/**
	 * Override to handle exception resulting from rank expression evaluation
	 */
	public void handleRankExpressionException(RPLException e) throws RPLException {
		throw e;
	}

	
}
