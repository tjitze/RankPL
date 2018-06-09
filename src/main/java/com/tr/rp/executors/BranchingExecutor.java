package com.tr.rp.executors;

import java.util.LinkedList;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.ExecutorProvider;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class BranchingExecutor implements Executor {

	private final Splitter splitter;
	private int inRank = 0;
	private int shift1 = Rank.MAX;
	private int shift2 = Rank.MAX;
	private Supplier<AbstractExpression> exp;

	
	public BranchingExecutor(Supplier<AbstractExpression> exp, ExecutorProvider s1, ExecutorProvider s2, Executor out,
			ExecutionContext c) {
		this.exp = exp;
		InternalMerger m = new InternalMerger(new Deduplicator(out));
		Filter.Predicate pos = new Filter.Predicate() {
			@Override
			public boolean test(State s) throws RPLException {
				return testBranchCondition(s);
			}
		};
		Filter.Predicate neg = new Filter.Predicate() {
			@Override
			public boolean test(State s) throws RPLException {
				return !pos.test(s);
			}
		};
		Executor exec1 = new Filter(s1.getExecutor(m.getIn1(), c), pos) {
			public void handleConditionException(RPLException e) throws RPLException {
				BranchingExecutor.this.handleConditionException(e);
			}
		};
		Executor exec2 = new Filter(s2.getExecutor(m.getIn2(), c), neg) {
			public void handleConditionException(RPLException e) throws RPLException {
				BranchingExecutor.this.handleConditionException(e);
			}
		};
		splitter = new Splitter(exec1, exec2);
	}

	@Override
	public void close() throws RPLException {
		splitter.close();
	}

	@Override
	public void push(State s) throws RPLException {
		inRank = s.getRank();
		if (testBranchCondition(s)) {
			if (shift1 == Rank.MAX) {
				shift1 = s.getRank();
			}
		} else {
			if (shift2 == Rank.MAX) {
				shift2 = s.getRank();
			}
		}
		splitter.push(s);
	}

	private boolean testBranchCondition(State s) throws RPLException {
		try {
			return extraBranchCondition(s) && exp.get().getValue(s.getVarStore(), Type.BOOL);
		} catch (RPLException e) {
			handleConditionException(e);
			return false;
		}
	}

	/**
	 * Override to handle exceptions resulting from evaluation of condition
	 */
	public void handleConditionException(RPLException e) throws RPLException {
		throw e;
	}
	
	/**
	 * Override to implement extra check for condition
	 */
	public boolean extraBranchCondition(State s) {
		return true;
	}
	
	private class InternalMerger {

		private final Executor out;
		private final Executor in1;
		private final Executor in2;
		private boolean in1Closed = false;
		private boolean in2Closed = false;

		private LinkedList<State> in1Queue = new LinkedList<State>();
		private LinkedList<State> in2Queue = new LinkedList<State>();

		private int minPotentialNextRank1 = 0;
		private int minPotentialNextRank2 = 0;

		private int outputOffset = -1;

		private int outRank = 0;

		public InternalMerger(Executor out) {
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
					minPotentialNextRank1 = Rank.MAX;
					flush();
				}

				@Override
				public void push(State s) throws RPLException {
					if (in1Closed) {
						throw new IllegalStateException();
					}
					minPotentialNextRank1 = Rank.add(s.getRank(), shift1);
					in1Queue.addLast(s.shiftUp(shift1));
					flush();
				}

			};
			this.in2 = new Executor() {

				@Override
				public void close() throws RPLException {
					in2Closed = true;
					minPotentialNextRank2 = Rank.MAX;
					flush();
				}

				@Override
				public void push(State s) throws RPLException {
					if (in2Closed) {
						throw new IllegalStateException();
					}
					minPotentialNextRank2 = Rank.add(s.getRank(), shift2);
					in2Queue.addLast(s.shiftUp(shift2));
					flush();
				}

			};
		}

		private void flush() throws RPLException {
			State s = getNextItem();
			while (s != null) {
				if (s.getRank() < outRank) { // debug
					throw new IllegalStateException("Illegal rank order");
				}
				outRank = s.getRank();
				out.push(s);
				s = getNextItem();
			}
			if (in1Closed && in2Closed) {
				out.close();
				if (!in1Queue.isEmpty() || !in2Queue.isEmpty()) {
					//System.out.println("queue 1: " + in1Queue); // debug
					//System.out.println("queue 2: " + in2Queue);
					throw new IllegalStateException();
				}
			}
		}

		private State getNextItem() {

			// Can we return item from queue 1?
			boolean in1Filled = !in1Queue.isEmpty();
			boolean in2Filled = !in2Queue.isEmpty();
			
			if (in1Filled && shift1 < Rank.MAX) {
				int in1rank = in1Queue.getFirst().getRank();
				
				// still waiting for shift2, and rank higher than input? then wait
				if (!in2Closed && shift2 == Rank.MAX && in1rank > inRank) {
					return null;
				}
				
				// rank lower than shift2 value? then return
				if (in1rank < shift2) {
					return in1Queue.removeFirst();
				}
				
				// queue 2 filled but in1rank is lower? then return
				if (!in2Queue.isEmpty() && in1rank <= in2Queue.getFirst().getRank()) {
					return in1Queue.removeFirst();
				}
				
				// queue 2 empty but next rank in queue 2 higher than in1rank? then return
				if (in2Queue.isEmpty() && in1rank < minPotentialNextRank2) {
					return in1Queue.removeFirst();
				}
			}

			if (in2Filled && shift2 < Rank.MAX) {
				int in2rank = in2Queue.getFirst().getRank();
				
				// still waiting for shift1, and rank higher than input? then wait
				if (!in1Closed && shift1 == Rank.MAX && in2rank > inRank) {
					return null;
				}
				
				// rank lower than shift1 value? then return
				if (in2rank < shift1) {
					return in2Queue.removeFirst();
				}
				
				// queue 1 filled but in2rank is lower? then return
				if (!in1Queue.isEmpty() && in2rank < in1Queue.getFirst().getRank()) {
					return in2Queue.removeFirst();
				}
				
				// queue 1 empty but next rank in queue 1 higher than in1rank? then return
				if (in1Queue.isEmpty() && in2rank < minPotentialNextRank1) {
					return in2Queue.removeFirst();
				}

			}

			return null;
		}

		public Executor getIn1() {
			return in1;
		}

		public Executor getIn2() {
			return in2;
		}

	}
}
