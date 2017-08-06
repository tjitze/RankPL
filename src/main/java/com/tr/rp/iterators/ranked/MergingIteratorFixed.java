package com.tr.rp.iterators.ranked;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.Rank;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.varstore.VarStore;

public class ChooseMergingIteratorFixed implements RankedIterator<VarStore> {

	private final RankedIterator<VarStore> in1;
	private final RankedIterator<VarStore> in2;
		
	private int rankIncrease;

	private RankedItem<VarStore> next = null;
	
	private boolean in1init = false;
	private boolean in2init = false;
	private boolean in1done = false;
	private boolean in2done = false;

	private int normalizationOffset = -1;
	
	/**
	 * Construct a choose merging iterator 
	 * 
	 * @param in1 First iterator
	 * @param in2 Second iterator
	 * @param rankIncrease Expression indicating rank increase for second iterator
	 * @param exceptionSource Statement to use as exception source for exceptions coning from rank increase expression
	 * @throws RPLException
	 */
	public ChooseMergingIteratorFixed(RankedIterator<VarStore> in1, 
			RankedIterator<VarStore> in2, int rankIncrease) throws RPLException {
		this.in1 = in1 ;
		this.in2 = in2;
		this.rankIncrease = rankIncrease;
	}

	@Override
	public boolean next() throws RPLException {
		next = getNextItem();
		if (normalizationOffset == -1) {
			normalizationOffset = (next == null)? 0: next.rank;
		}
		return next != null;
	}

	@Override
	public VarStore getItem() throws RPLException {
		return next != null? next.item: null;
	}

	@Override
	public int getRank() {
		return next != null? next.rank - normalizationOffset: -1;
	}

	// Current items still need to be returned
	
	public RankedItem<VarStore> getNextItem() throws RPLException {
		if (in1done && !in2done) {
			if (in2init) {
				RankedItem<VarStore> ret = new RankedItem<VarStore>(in2.getItem(), in2.getRank() + rankIncrease);
				in2done = !in2.next();
				return ret;
			} else {
				in2init = true;				
				if (in2.next()) {
					RankedItem<VarStore> ret = new RankedItem<VarStore>(in2.getItem(), in2.getRank() + rankIncrease);
					in2done = !in2.next();
					return ret;
				} else {
					in2done = true;
					return null;
				}
			}
		}
		if (!in1done && in2done) {
			if (in1init) {
				RankedItem<VarStore> ret = new RankedItem<VarStore>(in1.getItem(), in1.getRank());
				in1done = !in1.next();
				return ret;
			} else {
				in1init = true;				
				if (in1.next()) {
					RankedItem<VarStore> ret = new RankedItem<VarStore>(in1.getItem(), in1.getRank());
					in1done = !in1.next();
					return ret;
				} else {
					in1done = true;
					return null;
				}
			}
		}
		if (in2done && in1done) {
			return null;
		}
		
		// Initialize A if necessary
		if (!in1init) {
			in1init = true;
			if (!in1.next()) {
				in1done = true;
				return getNextItem();
			}
		}

		// Initialize B if necessary
		if (!in2init && rankIncrease < in1.getRank()) {
			in2init = true;
			if (!in2.next()) {
				in2done = true;
				return getNextItem();
			}
		}
		
		// Return A unless B is lower ranked
		if (in2init && in2.getRank() + rankIncrease < in1.getRank()) {
			RankedItem<VarStore> ret = new RankedItem<VarStore>(in2.getItem(), in2.getRank() + rankIncrease);
			in2done = !in2.next();
			return ret;
		} else {
			RankedItem<VarStore> ret = new RankedItem<VarStore>(in1.getItem(), in1.getRank());
			in1done = !in1.next();
			return ret;
		}
	}
}
