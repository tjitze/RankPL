package com.tr.rp.core.rankediterators;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.tr.rp.core.Rank;
import com.tr.rp.core.RankedVarStore;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.NumExpression;

public class ChooseMergingIterator implements RankedIterator {

	private final RankedIterator in1;
	private final RankedIterator in2;

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
	
	public ChooseMergingIterator(RankedIterator in1, 
			RankedIterator in2, NumExpression e) {
		this.in1 = in1;
		this.in2 = in2;
		in1next = in1.next();
		in2next = in2.next();
		this.e = e;

		// Check
		if (e.hasRankExpression()) {
			throw new IllegalArgumentException("Expression contains rank expressions");
		}
	}
	
	@Override
	public boolean next() {
		if (!pq.isEmpty()) pq.remove();
		if (pq.isEmpty()) {
			if (in1next) {
				pq.add(new RankedVarStore(in1.getVarStore(), in1.getRank()));
				in1next = in1.next();
			}
			if (in2next) {
				pq.add(new RankedVarStore(in2.getVarStore(), Rank.add(in2.getRank(), e.getVal(in2.getVarStore()))));
				in2next = in2.next();
			}
		} else {
			// Make sure we don't skip any item
			int currentRank = pq.peek().rank;
			while (in1next && in1.getRank() < currentRank) {
				pq.add(new RankedVarStore(in1.getVarStore(),in1.getRank()));
				in1next = in1.next();
			}
			// Here we may be adding items ranked higher than current rank,
			// but that's ok. We just need to ensure that all items that are
			// possibly ranked n are in pq.
			while (in2next && in2.getRank() < currentRank) {
				pq.add(new RankedVarStore(in2.getVarStore(),Rank.add(in2.getRank(), e.getVal(in2.getVarStore()))));
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
	public VarStore getVarStore() {
		return pq.peek().varStore;
	}

	@Override
	public int getRank() {
		return pq.peek().rank - normalizationOffset;
	}

}
