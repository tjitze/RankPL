package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Maximum of sequence of integers.
 */
public class Max extends AbstractExpression {

	private final AbstractExpression[] es;
	
	public Max(AbstractExpression ... es) {
		this.es = es;
	}

	@Override
	public void getVariables(Set<String> list) {
		for (AbstractExpression e: es) e.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		for (int i = 0; i < es.length; i++) {
			if (es[i].hasRankExpression()) return true;
		}
		return false;
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression[] newEs = new AbstractExpression[es.length];
		for (int i = 0; i < newEs.length; i++) {
			newEs[i] = (AbstractExpression)es[i].transformRankExpressions(v, rank);
		}
		Max m = new Max(newEs);
		m.setLineNumber(getLineNumber());
		return m;
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
		Max m = new Max(newEs);
		m.setLineNumber(getLineNumber());
		return m;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < es.length; i++) {
			max = Integer.max(max, es[i].getValue(e, Type.INT));
		}
		return max;
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
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < es.length; i++) {
			max = Integer.max(max, es[i].getDefiniteValue(Type.INT));
		}
		return max;
	}

	public String toString() {
		return "max(" + 
				Arrays.stream(es).map(e -> StringTools.stripPars(e.toString())).collect(Collectors.joining(", ")) + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof Max) && Arrays.deepEquals(((Max)o).es, es);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(es);
	}

}
