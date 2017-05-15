package com.tr.rp.core;

import java.util.List;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.expressions.PersistentList;
import com.tr.rp.expressions.FunctionCall;

/**
 * Abstract class for expressions.
 */
public abstract class Expression implements LanguageElement {

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
	public abstract Expression transformRankExpressions(VarStore v, int rank) throws RPLException;
	
	/**
	 * Transform this expression by rewriting all (sub) rank expressions.
	 * They will all be rewritten with the given rank.
	 * 
	 * @param rank Rank to use for rewriting rank expressions.
	 * @return New object iff something changed.
	 * @throws RPLException Run time RPL exception
	 */
	public final Expression transformRankExpressions(int rank) throws RPLException {
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
	public abstract Expression replaceEmbeddedFunctionCall(FunctionCall fc, String var);

	public abstract Object getValue(VarStore e) throws RPLException;
	
	public abstract boolean hasDefiniteValue();
	
	public abstract Object getDefiniteValue() throws RPLException;
	
	public boolean getBoolValue(VarStore e) throws RPLException {
		Object o = getValue(e);
		if (o instanceof Boolean) {
			return ((Boolean)o);
		} else {
			throw new RPLTypeError("boolean", o);
		}
	}
	
	public int getIntValue(VarStore e) throws RPLException {
		Object o = getValue(e);
		if (o instanceof Integer) {
			return ((Integer)o);
		} else {
			throw new RPLTypeError("integer", o);
		}
	}
	
	public String getStringValue(VarStore e) throws RPLException {
		Object o = getValue(e);
		if (o instanceof String) {
			return ((String)o);
		} else {
			throw new RPLTypeError("string", o);
		}
	}

	public List<?> getListValue(VarStore e) throws RPLException {
		Object o = getValue(e);
		if (o instanceof List) {
			return ((List)o);
		} else {
			throw new RPLTypeError("list", o);
		}
	}

	public boolean getDefiniteBoolValue() throws RPLException {
		Object o = getDefiniteValue();
		if (o instanceof Boolean) {
			return ((Boolean)o);
		} else {
			throw new RPLTypeError("bool", o);
		}
	}
	
	public int getDefiniteIntValue() throws RPLException {
		Object o = getDefiniteValue();
		if (o instanceof Integer) {
			return ((Integer)o);
		} else {
			throw new RPLTypeError("integer", o);
		}
	}
	
	public String getDefiniteStringValue() throws RPLException {
		Object o = getDefiniteValue();
		if (o instanceof String) {
			return ((String)o);
		} else {
			throw new RPLTypeError("string", o);
		}
	}

	public PersistentList getDefiniteListValue() throws RPLException {
		Object o = getDefiniteValue();
		if (o instanceof PersistentList) {
			return ((PersistentList)o);
		} else {
			throw new RPLTypeError("list", o);
		}
	}


}
