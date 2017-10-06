package com.tr.rp.executors;

import java.util.LinkedList;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public final class LShifter implements Executor {

	private final Supplier<AbstractExpression> condition;

	private final Executor out;
	private boolean closed = false;
	
	private final LinkedList<State> in1Queue = new LinkedList<State>();
	private final LinkedList<State> in2Queue = new LinkedList<State>();
	
	private int shift;
	
	private int offset1 = -1;
	private int offset2 = -1;
	
	private EvaluationErrorHandler errorHandler;
	
	private int minPotentialNextRank = -1;
	
	private int outRank = 0;
	
	public LShifter(Executor out, Supplier<AbstractExpression> condition, int shift) {
		this.out = Guard.checkIfEnabled(out);
		this.shift = shift;
		if (shift < 0) {
			throw new IllegalArgumentException();
		}
		this.condition = condition;
	}
	
	public LShifter(Executor out, AbstractExpression condition, int shift) {
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

		// Set offset in case rank of true item exceeds shift
		// TODO: disable this case to achieve destructive conditioning (block if condition never satisfied)
		if (offset1 == -1 && s.getRank() >= shift) {
			offset1 = shift;
			offset2 = 0;
			// no need to adjust ranks in queue 2
		}
		
		if (getCheckedValue(s.getVarStore())) {
			// Set offset in case shift is larger than rank of true item
			if (offset1 == -1) {
				offset1 = s.getRank();
				offset2 = shift - offset1;
				// adjust ranks in queue 2
				int n = in2Queue.size();
				for (int i = 0; i < n; i++) {
					in2Queue.addLast(in2Queue.removeFirst().shiftUp(offset2));
				}
			}
			in1Queue.addLast(s.shiftDown(offset1));
		} else {
			if (offset2 >= 0) {
				in2Queue.addLast(s.shiftUp(offset2));
			} else {
				in2Queue.addLast(s);
			}
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
			if (s.getRank() < outRank) {
				throw new IllegalStateException("Illegal rank order");
			}
			outRank = s.getRank();
			s = getNextItem();
		}
		if (closed) {
			if (!in1Queue.isEmpty() || !in2Queue.isEmpty()) {
				throw new IllegalStateException();
			}
			out.close();
		}
	}
	
	private State getNextItem() {
		
		// Are the offsets initialized?
		if (offset1 == -1 || offset2 == -1) {
			return null;
		}
		
		// Can we return item from queue 1?
		boolean in1Filled = !in1Queue.isEmpty();
		boolean in2Filled = !in2Queue.isEmpty();
		if (in1Filled) {
			int in1Rank = in1Queue.getFirst().getRank();
						
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
			int minPotentialNextRankInQueue2 = Rank.add(minPotentialNextRank, offset2);
			if (!in2Filled && minPotentialNextRankInQueue2 >= in1Rank) {
				return in1Queue.removeFirst();
			}
		}
		
		// Can we return item from queue 2?
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
			int minPotentialNextRankInQueue1 = Rank.sub(minPotentialNextRank, offset1);
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
