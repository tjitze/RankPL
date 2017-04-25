package com.tr.rp.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;

/**
 * An IteratorSplitter takes a ranked iterator as input
 * and produces two copies (getA(), getB()). Each copy 
 * is backed by a FIFO buffer to remember elements in 
 * case the consumption of one of the two copies overtakes 
 * the other.
 */
public class IteratorSplitter<T> {

	private final RankedIterator<T> in;
	private final LinkedList<T> vsA = new LinkedList<T>();
	private final LinkedList<T> vsB = new LinkedList<T>();
	private final LinkedList<Integer> rsA = new LinkedList<Integer>();
	private final LinkedList<Integer> rsB = new LinkedList<Integer>();

	private T va = null;
	private int ra = -1;
	private T vb = null;
	private int rb = -1;
	
	public IteratorSplitter(RankedIterator<T> in) {
		this.in = in;
	}
	
	/**
	 * @return The first copy of the iterator provided at construction.
	 */
	public RankedIterator<T> getA() {
		return new RankedIterator<T>() {

			@Override
			public boolean next() {
				if (!rsA.isEmpty()) {
					va = vsA.removeFirst();
					ra = rsA.removeFirst();
					return true;
				} else if (in.next()) {
					va = in.getItem();
					ra = in.getRank();
					vsB.addLast(va);
					rsB.addLast(ra);
					return true;
				} else {
					return false;
				}
			}

			@Override
			public T getItem() {
				return va;
			}

			@Override
			public int getRank() {
				return ra;
			}
			
		};
	}

	/**
	 * @return The second copy of the iterator provided at construction.
	 */
	public RankedIterator<T> getB() {
		return new RankedIterator<T>() {

			@Override
			public boolean next() {
				if (!rsB.isEmpty()) {
					vb = vsB.removeFirst();
					rb = rsB.removeFirst();
					return true;
				} else if (in.next()) {
					vb = in.getItem();
					rb = in.getRank();
					vsA.addLast(vb);
					rsA.addLast(rb);
					return true;
				} else {
					return false;
				}
			}

			@Override
			public T getItem() {
				return vb;
			}

			@Override
			public int getRank() {
				return rb;
			}
			
		};	
	}
	
	public String toString() {
		return "IteratorSplitter(in="+in+")";
	}

}
