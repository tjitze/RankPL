package com.tr.rp.denotational.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.denotational.core.VarStore;

/**
 * An IteratorSplitter takes an iterator as input
 * and produces two copies (getA(), getB()).
 * Each copy is backed by a FIFO buffer to 
 * remember elements in case the consumption 
 * of one of the two copies overtakes the other.
 */
public class IteratorSplitter<V> {

	private final RankedIterator<V> in;
	private final LinkedList<V> vsA = new LinkedList<V>();
	private final LinkedList<V> vsB = new LinkedList<V>();
	private final LinkedList<Integer> rsA = new LinkedList<Integer>();
	private final LinkedList<Integer> rsB = new LinkedList<Integer>();

	private V va;
	private int ra;
	private V vb;
	private int rb;
	
	public IteratorSplitter(RankedIterator<V> in) {
		this.in = in;
	}
	
	public RankedIterator<V> getA() {
		return new RankedIterator<V>() {

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
			public V getItem() {
				return va;
			}

			@Override
			public int getRank() {
				return ra;
			}
			
		};
	}

	public RankedIterator<V> getB() {
		return new RankedIterator<V>() {

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
			public V getItem() {
				return vb;
			}

			@Override
			public int getRank() {
				return rb;
			}
			
		};	
	}

}
