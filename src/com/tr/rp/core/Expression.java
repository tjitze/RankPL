package com.tr.rp.core;

import com.tr.rp.expressions.num.NumExpression;

/**
 * Abstract class for expressions.
 */
public abstract class Expression<T extends Expression> {

	/**
	 * @return True iff this expression contains a rank expression
	 * that must be rewritten before it can be evaluated.
	 */
	public abstract boolean hasRankExpression();

	/**
	 * Transform this expression by rewriting all (sub) rank expressions,
	 * as far as possible, with the provided variable store and rank, 
	 * assuming that this method is called for each variable store, in
	 * ranked (low-to-high) order.
	 * 
	 * Returns a new object if at least one rank expression was 
	 * rewritten, or itself if not.
	 * 
	 * @param v Variable store to use for rank expression rewriting.
	 * @param rank Rank to use for rank expression rewriting.
	 * @return A new object iff something changed.
	 */
	public abstract T transformRankExpressions(VarStore v, int rank);
	
	/**
	 * Transform this expression by rewriting all (sub) rank expressions.
	 * They will all be rewritten with the given rank.
	 * 
	 * @param rank Rank to use for rewriting rank expressions.
	 * @return New object iff something changed.
	 */
	public final T transformRankExpressions(int rank) {
		return this.transformRankExpressions(null, rank);
	}
}
