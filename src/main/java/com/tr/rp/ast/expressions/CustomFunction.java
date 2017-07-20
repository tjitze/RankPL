package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

/**
 * Custom function. Represents an expression defined by a given
 * function object. This allows RankPL code to interact with Java
 * code.
 *
 * @param <T>
 */
public class CustomFunction<T> extends AbstractExpression {

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
	public boolean needsRankExpressionTransformation() {
		return false;
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return this;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return null;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
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

	@Override
	public boolean equals(Object o) {
		return (o instanceof CustomFunction<?>) &&
				((CustomFunction<?>)o).fn.equals(fn);
	}

	@Override
	public int hashCode() {
		return fn.hashCode();
	}

}
