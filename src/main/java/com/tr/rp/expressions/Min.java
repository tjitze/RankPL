package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

/**
 * Minimum of sequence of integers.
 */
public class Min extends Expression {

	private final Expression[] es;
	
	public Min(Expression ... es) {
		this.es = es;
	}

	@Override
	public void getVariables(Set<String> list) {
		for (Expression e: es) e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newEs = new Expression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (Expression)es[i].replaceVariable(a, b);
		}
		return new Min(newEs);
	}

	@Override
	public boolean hasRankExpression() {
		for (int i = 0; i < es.length; i++) {
			if (es[i].hasRankExpression()) return true;
		}
		return false;
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newEs = new Expression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (Expression)es[i].transformRankExpressions(v, rank);
		}
		return new Min(newEs);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		for (int i = 0; i < es.length; i++) {
			AbstractFunctionCall fc = es[i].getEmbeddedFunctionCall();
			if (fc != null) {
				return fc;
			}
		}
		return null;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		Expression[] newEs = new Expression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (Expression)es[i].replaceEmbeddedFunctionCall(fc, var);
		}
		return new Min(newEs);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < es.length; i++) {
			min = Integer.min(min, es[i].getIntValue(e));
		}
		return min;
	}

	@Override
	public boolean hasDefiniteValue() {
		for (int i = 0; i < es.length; i++) {
			if (es[i].hasDefiniteValue()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < es.length; i++) {
			min = Integer.min(min, es[i].getDefiniteIntValue());
		}
		return min;
	}

	public String toString() {
		return "min(" + 
				Arrays.stream(es).map(e -> e.toString()).collect(Collectors.joining(", ")) + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof Min) && Arrays.deepEquals(((Min)o).es, es);
	}

}
