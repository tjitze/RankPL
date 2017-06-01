package com.tr.rp.expressions;

import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

/**
 * The boolean NOT operator.
 */
public class Negative extends Expression {

	private final Expression e;
	
	public Negative(Expression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Negative((Expression)e.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Negative(e.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Negative((Expression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore v) throws RPLException {
		return - e.getIntValue(v);
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return - e.getDefiniteIntValue();
	}

	public String toString() {
		return "-" + e;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Negative) && ((Negative)o).e.equals(e);
	}
}
