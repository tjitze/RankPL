package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.Rank;
import com.tr.rp.varstore.VarStore;

/**
 * A literal value (Integer, String, Boolean, or PersistentList).
 */
public class Literal<T> extends AbstractExpression {

	public static final Literal<Integer> ZERO = new Literal<Integer>(0);
	public static final Literal<Integer> MAX = new Literal<Integer>(Rank.MAX);
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
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
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
