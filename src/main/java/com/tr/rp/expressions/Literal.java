package com.tr.rp.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

/**
 * A literal value (Integer, String, Boolean, or PersistentList).
 */
public class Literal<T> extends Expression {

	public static final Literal<Integer> ZERO = new Literal<Integer>(0);
	public static final Literal<Integer> MAX = new Literal<Integer>(Integer.MAX_VALUE);
	public static final Literal<Boolean> TRUE = new Literal<Boolean>(true);
	public static final Literal<Boolean> FALSE = new Literal<Boolean>(false);
	
	private final T value;
	
	public Literal(T value) {
		this.value = value;
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
		return value;
	}

	@Override
	public boolean hasDefiniteValue() {
		return true;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return value;
	}
	
	public String toString() {
		if (value instanceof String) {
			return "\""+value.toString()+"\"";
		} else {
			return value.toString();
		}
	}
	
	public boolean equals(Object o) {
		return ((o instanceof Literal<?>) && Objects.equals(((Literal<?>)o).value, value));
	}

	public T getLiteralValue() {
		return value;
	}
}
