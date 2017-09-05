package com.tr.rp.iterators.ranked;

import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class BranchingIterator implements RankedIterator<VarStore> {
	
	private final BranchingIteratorErrorHandler errorHandler;

	private final SingleBufferingIterator<VarStore> in;
	private CheckingIterator c = null;
	private SingleBufferingIterator<VarStore> p1 = null;
	
	private final AbstractExpression e;
	private final AbstractStatement s1;
	private final AbstractStatement s2;
	
	private RankedItem<VarStore> next;
	
	private ExecutionContext ec;
	
	private int state = 0;

	private boolean pos = false;

	private boolean done = false;

	private boolean normalized = false;

	private MergingIterator mi;
	private RankedIterator<VarStore> restP2;
	
	public BranchingIterator(RankedIterator<VarStore> in, AbstractExpression e, AbstractStatement s1, AbstractStatement s2, ExecutionContext ec, BranchingIteratorErrorHandler errorHandler) {
		Objects.nonNull(in);
		Objects.nonNull(e);
		Objects.nonNull(s1);
		Objects.nonNull(s2);
		Objects.nonNull(ec);
		Objects.nonNull(errorHandler);
		this.in = new SingleBufferingIterator<VarStore>(in);
		this.e = e;
		this.s1 = s1;
		this.s2 = s2;
		this.ec = ec;
		this.errorHandler = errorHandler;
	}

	@Override
	public boolean next() throws RPLException {
		next = getNextItem();
		return next != null;
	}

	@Override
	public VarStore getItem() throws RPLException {
		return next != null? next.item: null;
	}

	@Override
	public int getRank() {
		return next != null? next.rank: -1;
	}
	
	private RankedItem<VarStore> getNextItem() throws RPLException {
		if (done) {
			return null;
		}
		if (state == 0) {
			state = 1;
			// Determine 1st item
			if (in.next()) {
				try {
					pos = e.getValue(in.getItem(), Type.BOOL);
				} catch (RPLException exception) {
					errorHandler.handlExpError(exception);
					throw exception;
				}
				in.moveBack();
			}
			c = new CheckingIterator(in, pos? e: new Not(e));
			p1 = new SingleBufferingIterator<VarStore>((pos? s1: s2).getIterator(c, ec));
		}

		if (state == 1) {
			if (p1.next()) {
				SingleBufferingIterator<VarStore> oppIt = c.getOppIterator();
				if (oppIt != null) {
					state = 3;
					int rank = oppIt.getRank();
					oppIt.moveBack();
					RankedIterator<VarStore> s = new ShiftingIterator(oppIt, -rank);
					s = new FilteringIterator(s, pos? new Not(e): e);
					s = (pos? s2: s1).getIterator(s, ec);
					s = new ShiftingIterator(s, rank);
					if (s.next()) {
						mi = new MergingIterator(p1, s);
						normalized = true;
						return new RankedItem<VarStore>(mi.getItem(), mi.getRank());
					} else {
						state = 2;
						normalized = true;
						return new RankedItem<VarStore>(p1.getItem(), p1.getRank());
					}
				} else {
					normalized = true;
					return new RankedItem<VarStore>(p1.getItem(), p1.getRank());
				}
			} else {
				SingleBufferingIterator<VarStore> oppIt = c.getOppIteratorFinal();
				if (oppIt != null) {
					state = 4;
					int rank = oppIt.getRank();
					oppIt.moveBack();
					RankedIterator<VarStore> s = new ShiftingIterator(oppIt, -rank);
					s = new FilteringIterator(s, pos? new Not(e): e);
					s = (pos? s2: s1).getIterator(s, ec);
					if (normalized) {
						s = new ShiftingIterator(s, rank);
					}
					restP2 = s;
				} else {
					done = true;
					return null;
				}
			}
		}
		
		// p2 done before p1
		if (state == 2) {
			if (p1.next()) {
				normalized = true;
				return new RankedItem<VarStore>(p1.getItem(), p1.getRank());
			} else {
				return null;
			}
		}
		
		// merging
		if (state == 3) {
			if (mi.next()) {
				normalized = true;
				return new RankedItem<VarStore>(mi.getItem(), mi.getRank());
			} else {
				return null;
			}
		}
		
		// p1 done before p2
		if (state == 4) {
			if (restP2.next()) {
				normalized = true;
				return new RankedItem<VarStore>(restP2.getItem(), restP2.getRank());
			} else {
				return null;
			}
		}
		
		throw new IllegalStateException();
	}

	private class CheckingIterator implements RankedIterator<VarStore> {

		private RankedIterator<VarStore> inA;
		private SingleBufferingIterator<VarStore> inB;
		private AbstractExpression exp;
		
		/**
		 * Construct buffering iterator.
		 * 
		 * @param in Iterator to use as input.
		 */
		public CheckingIterator(RankedIterator<VarStore> in, AbstractExpression exp) {
			IteratorSplitter<VarStore> s = new IteratorSplitter<VarStore>(in);
			inA = s.getA();
			inB = new SingleBufferingIterator<VarStore>(s.getB());
			this.exp = exp;
		}

		@Override
		public boolean next() throws RPLException {
			// Find item satisfying exp
			boolean hasNext = inA.next();
			while (hasNext && !getCheckedValue(inA.getItem())) {
				hasNext = inA.next();
			}
			return hasNext;
		}

		private boolean getCheckedValue(VarStore v) throws RPLException {
				try {
					return exp.getValue(v, Type.BOOL);
				} catch (RPLException exception) {
					errorHandler.handlExpError(exception);
					throw exception;
				}
		}

		@Override
		public VarStore getItem() throws RPLException {
			return inA.getItem();
		}

		@Override
		public int getRank() {
			return inA.getRank();
		}
		
		public SingleBufferingIterator<VarStore> getOppIterator() throws RPLException {
			while (inB.next()) {
				if (inA.getRank() < inB.getRank()) {
					inB.moveBack();
					return null;
				} else {
					if (!getCheckedValue(inB.getItem())) {
						return inB;
					}
				}
			}
			return null;
		}
		
		public SingleBufferingIterator<VarStore> getOppIteratorFinal() throws RPLException {
			while (inB.next()) {
				if (!getCheckedValue(inB.getItem())) {
					return inB;
				}
			}
			return null;
		}
		
	}


	private class FilteringIterator implements RankedIterator<VarStore> {

		private RankedIterator<VarStore> in;
		private AbstractExpression e;
		
		/**
		 * Construct buffering iterator.
		 * 
		 * @param in Iterator to use as input.
		 */
		public FilteringIterator(RankedIterator<VarStore> in, AbstractExpression e) {
			this.in = in;
			this.e = e;
		}

		@Override
		public boolean next() throws RPLException {
			boolean hasNext = in.next();
			while (hasNext) {
				boolean v = getCheckedValue(in.getItem());
				if (!v) {
					hasNext = in.next();
					continue;
				} else {
					break;
				}
			}
			return hasNext;
		}

		private boolean getCheckedValue(VarStore v) throws RPLException {
				try {
					return e.getValue(v, Type.BOOL);
				} catch (RPLException exception) {
					errorHandler.handlExpError(exception);
					throw exception;
				}
		}

		@Override
		public VarStore getItem() throws RPLException {
			return in.getItem();
		}

		@Override
		public int getRank() {
			return in.getRank();
		}
		
	}

	private class ShiftingIterator implements RankedIterator<VarStore> {
		
		private RankedIterator<VarStore> in;
		private int shift;
		
		/**
		 * Construct buffering iterator.
		 * 
		 * @param in Iterator to use as input.
		 */
		public ShiftingIterator(RankedIterator<VarStore> in, int shift) {
			this.in = in;
			this.shift = shift;
		}

		@Override
		public boolean next() throws RPLException {
			return in.next();
		}

		@Override
		public VarStore getItem() throws RPLException {
			return in.getItem();
		}

		@Override
		public int getRank() {
			return in.getRank() + shift;
		}
	}
}
