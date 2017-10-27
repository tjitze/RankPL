package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * The boolean NOT operator.
 */
public class Negative extends AbstractExpression {

	private final AbstractExpression e;

	public Negative(AbstractExpression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression x = new Negative(e.transformRankExpressions(v, rank));
		x.setLineNumber(getLineNumber());
		return x;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression x = new Negative((AbstractExpression) e.replaceEmbeddedFunctionCall(fc, var));
		x.setLineNumber(getLineNumber());
		return x;
	}

	@Override
	public Object getValue(VarStore v) throws RPLException {
		return -e.getValue(v, Type.INT);
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return -e.getDefiniteValue(Type.INT);
	}

	public String toString() {
		return "-" + e;
	}

	public boolean equals(Object o) {
		return (o instanceof Negative) && ((Negative) o).e.equals(e);
	}

	@Override
	public int hashCode() {
		return e.hashCode();
	}

}
