package com.tr.rp.executors;

import java.util.LinkedList;

import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;

public class Merger {

	private final Executor out;
	private final Executor in1;
	private final Executor in2;
	private boolean in1Closed = false;
	private boolean in2Closed = false;
	
	private LinkedList<State> in1Queue = new LinkedList<State>();
	private LinkedList<State> in2Queue = new LinkedList<State>();
	
	private int minPotentialNextRank1 = 0;
	private int minPotentialNextRank2 = 0;
	
	private int shift = 0;
	
	private int outputOffset = -1;
	
	private int outRank = 0;
	
	public Merger(Executor out) {
		this(out, 0);
	}
	
	public Merger(Executor out, int shift) {
		this.shift = shift;
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
				minPotentialNextRank1 = s.getRank();
				in1Queue.addLast(s);
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
				minPotentialNextRank2 = s.getRank();
				in2Queue.addLast(s.shiftUp(shift));
				flush();
			}
			
		};
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
		if (in1Closed && in2Closed) {
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
			if (!in2Filled && in2Closed) {
				return in1Queue.removeFirst();
			}
			
			// Is queue 2 empty, but any item that might be added there will be ranked higher? 
			// Then return item from queue 1.
			int minPotentialNextRankInQueue2 = Rank.add(minPotentialNextRank2, shift);
			if (!in2Filled && minPotentialNextRankInQueue2 >= in1Rank) {
				return in1Queue.removeFirst();
			}
		}
		
		if (in2Filled) {
			int in2Rank = in2Queue.getFirst().getRank();

			// Is queue 1 filled, but first item is ranked lower? Then return item from queue 2.
			if (in1Filled && in1Queue.getFirst().getRank() > in2Rank) {
				return in2Queue.removeFirst();
			}
			
			// Is queue 1 empty, but input is closed? Then return item from queue 2.
			if (!in1Filled && in1Closed) {
				return in2Queue.removeFirst();
			}

			// Is queue 1 empty, but any item that might be added there will be ranked higher? 
			// Then return item from queue 2.
			if (!in1Filled && minPotentialNextRank1 >= in2Rank) {
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
