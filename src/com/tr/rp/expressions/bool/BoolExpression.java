package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

/**
 * Base class for RankPL boolean expressions.
 */
public abstract class BoolExpression extends Expression<BoolExpression> {

	/**
	 * Evaluate this boolean expression w.r.t. given variable store.
	 * 
	 * @param e A variable store
	 * @return Value that this expression evaluates to w.r.t. variable store
	 */
	public abstract boolean isTrue(VarStore e);

	/**
	 * @return Negation of this boolean expression.
	 */
	public BoolExpression negate() {
		return new Not(this);
	}

	/**
	 * Returns the result of substituting all sub-expressions of this
	 * expression that are rank expressions with their actual value
	 * with respect to the given variable store.
	 */
	public abstract BoolExpression transformRankExpressions(VarStore v, int rank);
	
	/**
     * Returns true only if this expression is a contradiction. An
     * expression is a contradiction if it evaluates to false with
     * respect to every possible valuation. 
	 */
	public abstract boolean isContradiction();
	
	/**
     * Returns true only if this expression is a tautology. An
     * expression is a tautology if it evaluates to true with
     * respect to every possible valuation. 
	 */
	public abstract boolean isTautology();
}
