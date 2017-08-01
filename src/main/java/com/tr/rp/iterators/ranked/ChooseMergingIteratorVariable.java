package com.tr.rp.iterators.ranked;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRankException;
import com.tr.rp.ranks.Rank;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class ChooseMergingIteratorVariable implements RankedIterator<VarStore> {

	private final RankedIterator<VarStore> in1;
	private final RankedIterator<VarStore> in2;
	private final ChooseMergingIteratorErrorHandler errorHandler;
	
	public final PriorityQueue<RankedItem<VarStore>> pq = new PriorityQueue<RankedItem<VarStore>>(
			new Comparator<RankedItem<VarStore>>() {
				@Override
				public int compare(RankedItem<VarStore> o1, RankedItem<VarStore> o2) {
					return Rank.sub(o1.rank, o2.rank);
				}
			});
	private boolean in1next = false;
	private boolean in2next = false;
	
	private AbstractExpression rankIncrease;
	
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
	public ChooseMergingIteratorVariable(RankedIterator<VarStore> in1, 
			RankedIterator<VarStore> in2, AbstractExpression rankIncrease,
			ChooseMergingIteratorErrorHandler errorHandler) throws RPLException {
		this.errorHandler = errorHandler;
		this.in1 = in1;
		this.in2 = in2;
		in1next = in1.next();
		in2next = in2.next();
		this.rankIncrease = rankIncrease;
		
		// Check
		if (rankIncrease.hasRankExpression()) {
			throw new IllegalArgumentException("Expression contains rank expressions");
		}
	}

	public ChooseMergingIteratorVariable(RankedIterator<VarStore> in1, RankedIterator<VarStore> in2, AbstractExpression rankIncrease) throws RPLException {
		this(in1, in2, rankIncrease, new ChooseMergingIteratorErrorHandler() {
			@Override
			public void handleRankExpressionError(RPLException e) throws RPLException {
				throw e;
			}

			@Override
			public void illegalRank(int ri) throws RPLException {
				throw new RPLIllegalRankException(ri, rankIncrease, null);
			}
		});
	}

	@Override
	public boolean next() throws RPLException {
		if (!pq.isEmpty()) pq.remove();
		if (pq.isEmpty()) {
			if (in1next) {
				pq.add(new RankedItem<VarStore>(in1.getItem(), in1.getRank()));
				in1next = in1.next();
			}
			if (in2next) {
				int ri = 0;
				VarStore vs = in2.getItem();
				try {
					ri = rankIncrease.getValue(vs, Type.INT);
					if (ri < 0) {
						errorHandler.illegalRank(ri);
					}
				} catch (RPLException e) {
					errorHandler.handleRankExpressionError(e);
				}
				pq.add(new RankedItem<VarStore>(in2.getItem(), Rank.add(in2.getRank(), ri)));
				in2next = in2.next();
			}
		} else {
			// Make sure we don't skip any item
			int currentRank = pq.peek().rank;
			while (in1next && in1.getRank() < currentRank) {
				pq.add(new RankedItem<VarStore>(in1.getItem(),in1.getRank()));
				in1next = in1.next();
			}
			// Here we may be adding items ranked higher than current rank,
			// but that's OK. We just need to ensure that all items that are
			// possibly ranked as low as the current rank are in pq.
			while (in2next && in2.getRank() < currentRank) {
				int ri = 0;
				VarStore vs = in2.getItem();
				try {
					ri = rankIncrease.getValue(vs, Type.INT);
					if (ri < 0) {
						errorHandler.illegalRank(ri);
					}
				} catch (RPLException e) {
					errorHandler.handleRankExpressionError(e);
				}
				pq.add(new RankedItem<VarStore>(in2.getItem(),Rank.add(in2.getRank(), ri)));
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
	public VarStore getItem() throws RPLException {
		return pq.isEmpty()? null: pq.peek().item;
	}

	@Override
	public int getRank() {
		return pq.isEmpty()? 0: pq.peek().rank - normalizationOffset;
	}
	

}
