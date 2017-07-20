package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Minimum of sequence of integers.
 */
public class Min extends AbstractExpression {

	private final AbstractExpression[] es;
	
	public Min(AbstractExpression ... es) {
		this.es = es;
	}

	@Override
	public void getVariables(Set<String> list) {
		for (AbstractExpression e: es) e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		AbstractExpression[] newEs = new AbstractExpression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (AbstractExpression)es[i].replaceVariable(a, b);
		}
		return new Min(newEs);
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		for (int i = 0; i < es.length; i++) {
			if (es[i].needsRankExpressionTransformation()) return true;
		}
		return false;
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		AbstractExpression[] newEs = new AbstractExpression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (AbstractExpression)es[i].doRankExpressionTransformation(v, rank);
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
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression[] newEs = new AbstractExpression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (AbstractExpression)es[i].replaceEmbeddedFunctionCall(fc, var);
		}
		return new Min(newEs);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < es.length; i++) {
			min = Integer.min(min, es[i].getValue(e, Type.INT));
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
			min = Integer.min(min, es[i].getDefiniteValue(Type.INT));
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
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(es);
	}

}
