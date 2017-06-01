package com.tr.rp.expressions;

import java.util.Set;
import java.util.function.Function;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

/**
 * Custom function. Represents an expression defined by a given
 * function object. This allows RankPL code to interact with Java
 * code.
 *
 * @param <T>
 */
public class CustomFunction<T> extends Expression {

	private final Function<VarStore, T> fn;
	
	public static <U> CustomFunction<U> create(Function<VarStore, U> fn) {
		return new CustomFunction<U>(fn);
	}
	
	private CustomFunction(Function<VarStore, T> fn) {
		this.fn = fn;
	}

	@Override
	public void getVariables(Set<String> list) {
		// nop
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return this;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return null;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return this;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return fn.apply(e);
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return null;
	}

}
