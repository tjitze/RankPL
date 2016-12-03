package com.tr.rp.core.rankediterators;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.tr.rp.core.Rank;
import com.tr.rp.core.RankedVarStore;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.NumExpression;

/**
 * Iterator that takes two iterators as input and produces
 * a merge of the two that respects the rank ordering. 
 * Accepts an optional offset for the two inputs, to adjust 
 * the rank of one of the two input iterators.
 */
public class ChooseMergingIterator<V> implements RankedIterator<VarStore> {

	private final RankedIterator<VarStore> in1;
	private final RankedIterator<VarStore> in2;

	private final PriorityQueue<RankedVarStore> pq = new PriorityQueue<RankedVarStore>(
			new Comparator<RankedVarStore>() {
				@Override
				public int compare(RankedVarStore o1, RankedVarStore o2) {
					return Rank.sub(o1.rank, o2.rank);
				}
			});
	private boolean in1next = false;
	private boolean in2next = false;
	
	private NumExpression e;
	
	private int normalizationOffset = -1;
	
	public ChooseMergingIterator(RankedIterator<VarStore> in1, 
			RankedIterator<VarStore> in2, NumExpression e) {
		this.in1 = in1;
		this.in2 = in2;
		in1next = in1.next();
		in2next = in2.next();
		// Correct offset in case one input fails
		this.e = e;
	}
	
	@Override
	public boolean next() {
		if (!pq.isEmpty()) pq.remove();
		if (pq.isEmpty()) {
			if (in1next) {
				pq.add(new RankedVarStore(in1.getItem(), in1.getRank()));
				in1next = in1.next();
			}
			if (in2next) {
				pq.add(new RankedVarStore(in2.getItem(), Rank.add(in2.getRank(), e.getVal(in2.getItem()))));
				in2next = in2.next();
			}
		} else {
			// Make sure we don't skip any item
			int currentRank = pq.peek().rank;
			while (in1next && in1.getRank() < currentRank) {
				pq.add(new RankedVarStore(in1.getItem(),in1.getRank()));
				in1next = in1.next();
			}
			// Here we may be adding items ranked higher than current rank,
			// but that's ok. We just need to ensure that all items that are
			// possibly ranked n are in pq.
			while (in2next && in2.getRank() < currentRank) {
				pq.add(new RankedVarStore(in2.getItem(),Rank.add(in2.getRank(), e.getVal(in2.getItem()))));
				in2next = in2.next();
			}
		}
		// Normalize ranks
		if (!pq.isEmpty() && normalizationOffset == -1) {
			normalizationOffset = pq.peek().rank;
		}
		return !pq.isEmpty();
	}

	@Override
	public VarStore getItem() {
		return pq.peek().v;
	}

	@Override
	public int getRank() {
		return pq.peek().rank - normalizationOffset;
	}

}
