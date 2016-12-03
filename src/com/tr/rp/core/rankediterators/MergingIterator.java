package com.tr.rp.core.rankediterators;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.tr.rp.core.Rank;
import com.tr.rp.core.RankedVarStore;
import com.tr.rp.core.VarStore;

/**
 * Iterator that takes two iterators as input and produces
 * a merge of the two that respects the rank ordering. 
 * Accepts an optional offset for the two inputs, to adjust 
 * the rank of one of the two input iterators.
 */
public class MergingIterator<V> implements RankedIterator {

	private final RankedIterator in1;
	private final RankedIterator in2;
	private final int offset1;
	private final int offset2;

	private final PriorityQueue<RankedVarStore> pq = new PriorityQueue<RankedVarStore>(
			new Comparator<RankedVarStore>() {
				@Override
				public int compare(RankedVarStore o1, RankedVarStore o2) {
					return Rank.sub(o1.rank, o2.rank);
				}
			});
	private boolean in1next = false;
	private boolean in2next = false;

	public MergingIterator(RankedIterator in1, RankedIterator in2, int offset1, int offset2) {
		this.in1 = in1;
		this.in2 = in2;
		in1next = in1.next();
		in2next = in2.next();
		// Correct offset in case one input fails
		if (in1next) {
			this.offset2 = offset2;
		} else {
			this.offset2 = 0;
		}
		if (in2next) {
			this.offset1 = offset1;
		} else {
			this.offset1 = 0;
		}
	}
	
	public MergingIterator(RankedIterator in1, RankedIterator in2) {
		this(in1, in2, 0, 0);
	}
	
	@Override
	public boolean next() {
		if (!pq.isEmpty()) pq.remove();
		if (pq.isEmpty()) {
			if (in1next) {
				pq.add(new RankedVarStore(in1.getItem(), Rank.add(in1.getRank(), offset1)));
				in1next = in1.next();
			}
			if (in2next) {
				pq.add(new RankedVarStore(in2.getItem(), Rank.add(in2.getRank(), offset2)));
				in2next = in2.next();
			}
		} else {
			// Fill pq
			int currentRank = pq.peek().rank;
			while (in1next && Rank.add(in1.getRank(), offset1) + offset1 < currentRank) {
				pq.add(new RankedVarStore(in1.getItem(),Rank.add(in1.getRank(), offset1)));
				in1next = in1.next();
			}
			while (in2next && Rank.add(in2.getRank(), offset2) < currentRank) {
				pq.add(new RankedVarStore(in2.getItem(),Rank.add(in2.getRank(), offset2)));
				in2next = in2.next();
			}
		}
		return !pq.isEmpty();
	}

	@Override
	public VarStore getItem() {
		return pq.peek().v;
	}

	@Override
	public int getRank() {
		return pq.peek().rank;
	}

}
