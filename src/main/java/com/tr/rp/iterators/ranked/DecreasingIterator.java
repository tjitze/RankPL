package com.tr.rp.iterators.ranked;

import com.google.common.collect.MinMaxPriorityQueue;
import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Iterator that decreases the rank of items that satisfy a given condition.
 * 
 * TODO: add check for precondition
 */
public class DecreasingIterator implements RankedIterator<VarStore> {
	private final RankedIterator<VarStore> in;
	private final MinMaxPriorityQueue<RankedItem<VarStore>> queue = MinMaxPriorityQueue
			.<RankedItem<VarStore>>orderedBy((x, y) -> x.rank - y.rank).create();
	private final int dec;
	private final AbstractExpression e;

	private RankedItem<VarStore> next = null;

	/**
	 * Construct iterator that reproduces items provided by the in iterator, except that
	 * the rank of items satisfying the expression e are decreased by the value of dec.
	 * This assumes the lowest-ranked item satisfying e has a rank of at least the value
	 * of dec. 
	 */
	public DecreasingIterator(RankedIterator<VarStore> in, AbstractExpression e, int dec) {
		this.in = in;
		this.e = e;
		this.dec = dec;
	}

	@Override
	public boolean next() throws RPLException {
		next = getNext();
		return next != null;
	}

	@Override
	public VarStore getItem() throws RPLException {
		return next != null ? next.item : null;
	}

	@Override
	public int getRank() {
		return next != null ? next.rank : -1;
	}

	private RankedItem<VarStore> getNext() throws RPLException {
		if (in.next()) {
			VarStore v = in.getItem();
			int r = in.getRank();
			if (r == 0) {
				return new RankedItem<VarStore>(v, r);
			} else {
				queue.add(new RankedItem<VarStore>(v, e.getValue(v, Type.BOOL) ? r - dec : r));
				// Before returning item ranked x, ensure we queued all items up
				// to rank x + dec
				while (queue.peekLast().rank - queue.peekFirst().rank < dec && in.next()) {
					v = in.getItem();
					r = in.getRank();
					queue.add(new RankedItem<VarStore>(v, e.getValue(v, Type.BOOL) ? r - dec : r));
				}
				RankedItem<VarStore> ret = queue.remove();
				return ret;
			}
		}
		if (queue.isEmpty()) {
			return null;
		} else {
			return queue.remove();
		}
	}

}
