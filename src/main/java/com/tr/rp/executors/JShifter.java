package com.tr.rp.executors;

import java.util.LinkedList;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class JShifter implements Executor {

	private final Supplier<AbstractExpression> condition;

	private final Executor out;
	private boolean closed = false;
	
	private LinkedList<State> in1Queue = new LinkedList<State>();
	private LinkedList<State> in2Queue = new LinkedList<State>();
	
	private int shift;
	
	private int offset1 = -1;
	private int offset2 = -1;
	
	private EvaluationErrorHandler errorHandler;
	
	private int minPotentialNextRank = -1;
	
	public JShifter(Executor out, Supplier<AbstractExpression> condition, int shift) {
		this.out = new Executor() {

			private int offset = -1;
			
			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				if (offset == -1) {
					offset = s.getRank();
				}
				out.push(s.shiftDown(offset));
			}
			
		};
		this.shift = shift;
		this.condition = condition;
	}
	
	public JShifter(Executor out, AbstractExpression condition, int shift) {
		this(out, new Supplier<AbstractExpression>() {

			@Override
			public AbstractExpression get() {
				return condition;
			}
			
		}, shift);
	}
	
	@Override
	public void close() throws RPLException {
		closed = true;
		minPotentialNextRank = Rank.MAX;
		flush();
	}
	
	@Override
	public void push(State s) throws RPLException {
		if (closed) {
			throw new IllegalStateException();
		}
		minPotentialNextRank = s.getRank();
		if (getCheckedValue(s.getVarStore())) {
			if (offset1 == -1) {
				offset1 = s.getRank();
			}
			in1Queue.addLast(s.shiftDown(offset1));
		} else {
			if (offset2 == -1) {
				offset2 = s.getRank();
			}
			in2Queue.addLast(s.shiftDown(offset2).shiftUp(shift));
		}
		flush();
 	}

	private boolean getCheckedValue(VarStore varStore) throws RPLException {
		try {
			return condition.get().getValue(varStore, Type.BOOL);
		} catch (RPLException e) {
			if (errorHandler != null) {
				errorHandler.handleEvaluationError(e);
			}
			throw e;
		}
	}

	private void flush() throws RPLException {
		State s = getNextItem();
		while (s != null) {
			out.push(s);
			s = getNextItem();
		}
		if (closed) {
			out.close();
		}
	}
	
	private State getNextItem() {
			
		// Can we return item from queue 1?
		boolean in1Filled = !in1Queue.isEmpty();
		boolean in2Filled = !in2Queue.isEmpty();

		if (in1Filled) {
			int in1Rank = in1Queue.getFirst().getRank();
			
			// Does rank not exceed shift? Then return item from queue 1.
			if (in1Rank <= shift) {
				return in1Queue.removeFirst();
			}
			
			// Is queue 2 filled, but first item is ranked lower? Then return item from queue 1.
			if (in2Filled && in2Queue.getFirst().getRank() >= in1Rank) {
				return in1Queue.removeFirst();
			}
			
			// Is queue 2 empty, but input is closed? Then return item from queue 1.
			if (!in2Filled && closed) {
				return in1Queue.removeFirst();
			}
			
			// Is queue 2 empty, but any item that might be added there will be ranked higher? 
			// Then return item from queue 1.
			if (offset2 >= 0) {
				int minPotentialNextRankInQueue2 = Rank.sub(Rank.add(minPotentialNextRank, shift), offset2);
				if (!in2Filled && minPotentialNextRankInQueue2 >= in1Rank) {
					return in1Queue.removeFirst();
				}
			}
		}
		
		if (in2Filled) {
			int in2Rank = in2Queue.getFirst().getRank();

			// Is queue 1 filled, but first item is ranked lower? Then return item from queue 2.
			if (in1Filled && in1Queue.getFirst().getRank() > in2Rank) {
				return in2Queue.removeFirst();
			}
			
			// Is queue 1 empty, but input is closed? Then return item from queue 2.
			if (!in1Filled && closed) {
				return in2Queue.removeFirst();
			}

			// Is queue 1 empty, but any item that might be added there will be ranked higher? 
			// Then return item from queue 2.
			int minPotentialNextRankInQueue1 = offset1 == -1? 0: minPotentialNextRank - offset1;
			if (!in1Filled && minPotentialNextRankInQueue1 >= in2Rank) {
				return in2Queue.removeFirst();
			}
		}
		
		return null;
	}

	public void setErrorHandler(EvaluationErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

}
