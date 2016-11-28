package com.tr.rp.core.rankediterators;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;

/**
 * Iterator that takes two iterators as input and produces
 * a merge of the two that respects the rank ordering. 
 * Accepts an optional offset for the two inputs, to adjust 
 * the rank of one of the two input iterators.
 */
public class MergingIterator<V> implements RankedIterator<V> {

	private final RankedIterator<V> in1;
	private final RankedIterator<V> in2;
	private final int offset1;
	private final int offset2;

	// Staged elements
	private V v1 = null;
	private V v2 = null;

	// Staged ranks
	private int r1 = -1;
	private int r2 = -1;

	public MergingIterator(RankedIterator<V> in1, RankedIterator<V> in2, int offset1, int offset2) {
		this.in1 = in1;
		this.in2 = in2;
		this.offset1 = offset1;
		this.offset2 = offset2;
	}
	
	public MergingIterator(RankedIterator<V> in1, RankedIterator<V> in2) {
		this(in1, in2, 0, 0);
	}
	
	@Override
	public boolean next() {
		if (v1 == null && v2 == null) {
			boolean in1ok = in1.next();
			v1 = in1ok? in1.getItem(): null;
			r1 = in1.getRank() + offset1;
			boolean in2ok = in2.next();
			v2 = in2ok? in2.getItem(): null;
			r2 = in2.getRank() + offset2;
			return in1ok || in2ok;
		}
		if (v1 == null) {
			boolean in2ok = in2.next();
			v2 = in2ok? in2.getItem(): null;
			r2 = in2.getRank() + offset2;
			return in2ok;
		} else if (v2 == null) {
			boolean in1ok = in1.next();
			v1 = in1ok? in1.getItem(): null;
			r1 = in1.getRank() + offset1;;
			return in1ok;
		} 
		if (r1 <= r2) {
			boolean in1ok = in1.next();
			v1 = in1ok? in1.getItem(): null;
			r1 = in1.getRank() + offset1;
			return in1ok || v2 != null;
		} else {
			boolean in2ok = in2.next();
			v2 = in2ok? in2.getItem(): null;
			r2 = in2.getRank() + offset2;
			return in2ok || v1 != null;
		} 
	}

	@Override
	public V getItem() {
		if (v1 == null) {
			return v2;
		}
		if (v2 == null) {
			return v1;
		}
		if (r1 <= r2) {
			return v1;
		} else {
			return v2;
		}
	}

	@Override
	public int getRank() {
		if (v1 == null) {
			return r2;
		}
		if (v2 == null) {
			return r1;
		}
		if (r1 <= r2) {
			return r1;
		} else {
			return r2;
		}
	}

}
