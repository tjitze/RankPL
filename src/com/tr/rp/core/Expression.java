package com.tr.rp.core;

import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.num.FunctionCall;

/**
 * Abstract class for expressions.
 */
public abstract class Expression<T extends Expression<T>> implements LanguageElement {

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
	
	/**
	 * An embedded function call is a FunctionCall expression that is
	 * used as a subexpression. These have to be rewritten before 
	 * execution (see FunctionCallForm). This method should return
	 * the first encountered embedded function call in this expression,
	 * or null if there is none.
	 * 
	 * @return First embedded function call in this expression.
	 */
	public abstract FunctionCall getEmbeddedFunctionCall();
	
	/**
	 * Replace the given embedded FunctionCall object with the given 
	 * variable and return the resulting expression.
	 * 
	 * @param fc FunctionCall object to replace
	 * @param var Variable to replace function call with
	 * @return Result
	 */
	public abstract T replaceEmbeddedFunctionCall(FunctionCall fc, String var);

}
