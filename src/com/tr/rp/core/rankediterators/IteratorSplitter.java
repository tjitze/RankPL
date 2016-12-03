package com.tr.rp.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;

/**
 * An IteratorSplitter takes an iterator as input
 * and produces two copies (getA(), getB()).
 * Each copy is backed by a FIFO buffer to 
 * remember elements in case the consumption 
 * of one of the two copies overtakes the other.
 */
public class IteratorSplitter {

	private final RankedIterator in;
	private final LinkedList<VarStore> vsA = new LinkedList<VarStore>();
	private final LinkedList<VarStore> vsB = new LinkedList<VarStore>();
	private final LinkedList<Integer> rsA = new LinkedList<Integer>();
	private final LinkedList<Integer> rsB = new LinkedList<Integer>();

	private VarStore va = null;
	private int ra = -1;
	private VarStore vb = null;
	private int rb = -1;
	
	public IteratorSplitter(RankedIterator in) {
		this.in = in;
	}
	
	public RankedIterator getA() {
		return new RankedIterator() {

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
			public VarStore getItem() {
				return va;
			}

			@Override
			public int getRank() {
				return ra;
			}
			
		};
	}

	public RankedIterator getB() {
		return new RankedIterator() {

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
			public VarStore getItem() {
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
