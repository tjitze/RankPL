package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Abs expression. Returns absolute value of its integer argument.
 */
public class Abs extends AbstractExpression {

	private final AbstractExpression e;
	
	public Abs(AbstractExpression e) {
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
		Abs a = new Abs(e.transformRankExpressions(v, rank));
		a.setLineNumber(getLineNumber());
		return a;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		Abs a = new Abs((AbstractExpression)e.replaceEmbeddedFunctionCall(fc, var));
		a.setLineNumber(getLineNumber());
		return a;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return Math.abs(this.e.getValue(e, Type.INT));
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return Math.abs(this.e.getDefiniteValue(Type.INT));
	}

	public String toString() {
		return "abs(" + StringTools.stripPars(e.toString()) + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof Abs) && ((Abs)o).e.equals(e);
	}

	@Override
	public int hashCode() {
		return e.hashCode();
	}

}
