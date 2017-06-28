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

	private final BufferingIterator<VarStore> in1;
	private final BufferingIterator<VarStore> in2;
	
	private boolean getB = false;
	
	private int rankIncrease;
	
	private boolean in1init = false;
	private boolean in1empty = false;
		
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
		this.in1 = new BufferingIterator<VarStore>(in1);
		this.in2 = new BufferingIterator<VarStore>(in2);
		this.rankIncrease = rankIncrease;
	}

	@Override
	public boolean next() throws RPLException {
		boolean next1 = in1.next();
		if (!in1init && !next1) {
			in1empty = true;
		}
		in1init = true;
		if (!next1) {
			getB = true;
			return in2.next();
		}
		// At this point in1 is ready
		
		// shortcut: if rank of in1 does not exceed rank increase, we don't need to check in2.
		if (in1.getRank() <= rankIncrease) {
			getB = false;
			return true;
		}
		
		boolean next2 = in2.next();
		if (!next2) {
			getB = false;
			return true;
		}
		
		// At this point both in1 and in2 are ready 
		
		// Decide which to use
		if (in2.getRank() + rankIncrease < in1.getRank()) {
			getB = true;
			in1.moveBack();
		} else {
			getB = false;
			in2.moveBack();
		}
		return true;
	}

	@Override
	public VarStore getItem() throws RPLException {
		return getB? in2.getItem(): in1.getItem();
	}

	@Override
	public int getRank() {
		return getB? in2.getRank() + (in1empty? 0: rankIncrease): in1.getRank();
	}

}
