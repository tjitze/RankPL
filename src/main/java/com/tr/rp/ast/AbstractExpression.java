package com.tr.rp.ast;

import java.util.Objects;

import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Abstract class for expressions.
 */
public abstract class AbstractExpression implements LanguageElement {

	private int lineNumber = -1;

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
	 * @throws RPLException Run time RPL exception
	 */
	public abstract AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException;
	
	/**
	 * Transform this expression by rewriting all (sub) rank expressions.
	 * They will all be rewritten with the given rank.
	 * 
	 * @param rank Rank to use for rewriting rank expressions.
	 * @return New object iff something changed.
	 * @throws RPLException Run time RPL exception
	 */
	public final AbstractExpression transformRankExpressions(int rank) throws RPLException {
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
	public abstract AbstractFunctionCall getEmbeddedFunctionCall();
	
	/**
	 * Replace the given embedded FunctionCall object with the given 
	 * variable and return the resulting expression.
	 * 
	 * @param fc FunctionCall object to replace
	 * @param var Variable to replace function call with
	 * @return Result
	 */
	public abstract AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var);

	public abstract Object getValue(VarStore e) throws RPLException;
	
	public abstract boolean hasDefiniteValue();
	
	public abstract Object getDefiniteValue() throws RPLException;

	public <T> T getValue(VarStore e, Type<T> type) throws RPLException {
		Object o = getValue(e);
		if (o == null) {
			throw new RPLUndefinedException(this);
		} else if (type.test(o)) {
			return type.cast(o);
		} else {
			throw new RPLTypeError(type.getName(), o, this);
		}
	}
	
	public final int getIntValue(VarStore e) throws RPLException {
		return (int)getValue(e);
	}
	
	
	public <T> T getDefiniteValue(Type<T> type) throws RPLException {
		Objects.requireNonNull(type);
		Object o = getDefiniteValue();
		if (o == null) {
			throw new RPLUndefinedException(this);
		} else if (type.test(o)) {
			return type.cast(o);
		} else {
			throw new RPLTypeError(type.getName(), o, this);
		}
	}

	public abstract boolean equals(Object o);

	public abstract int hashCode();

	/**
	 * @return Line number in source file (-1 if not set).
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	
	/**
	 * Set the line number of this expression in the source file. Will be used
	 * for error reporting.
	 * 
	 * @param lineNumber Line number of this expression
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}
