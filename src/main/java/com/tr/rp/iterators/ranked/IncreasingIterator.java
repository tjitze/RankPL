package com.tr.rp.iterators.ranked;

import java.util.LinkedList;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Iterator that increases the rank of items that satisfy a given condition.
 * 
 * TODO: add check for precondition
 */
public class IncreasingIterator implements RankedIterator<VarStore> {

	private final SingleBufferingIterator<VarStore> buffered;
	private final LinkedList<RankedItem<VarStore>> queue = new LinkedList<RankedItem<VarStore>>();
	private final AbstractExpression e;
	private final int inc;

	/**
	 * Construct iterator that reproduces items provided by the in iterator, except that
	 * the rank of items satisfying the expression e are increased by the value of inc.
	 * This assumes that not all zero-ranked items satisfy e.
	 */
	public IncreasingIterator(RankedIterator<VarStore> in, AbstractExpression e, int inc) {
		buffered = new SingleBufferingIterator<VarStore>(in);
		this.e = e;
		this.inc = inc;
	}

	RankedItem<VarStore> next = null;

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
		while (buffered.next()) {
			VarStore v = buffered.getItem();
			int r = buffered.getRank();
			if (!e.getValue(v, Type.BOOL)) {
				if (!queue.isEmpty() && queue.peek().rank < r) {
					buffered.moveBack();
					return queue.removeFirst();
				}
				return new RankedItem<VarStore>(v, r);
			} else {
				queue.addLast(new RankedItem<VarStore>(v, r + inc));
			}
		}
		if (queue.isEmpty()) {
			return null;
		} else {
			return queue.removeFirst();
		}
	}

}
