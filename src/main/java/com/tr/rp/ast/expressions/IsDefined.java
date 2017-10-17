package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

/**
 * Returns true iff given variable is set.
 */
public class IsDefined extends AbstractExpression {

	private final AbstractExpression exp;

	public IsDefined(AbstractExpression exp) {
		this.exp = exp;
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return exp.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new IsDefined((AbstractExpression)exp.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return exp.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new IsDefined((AbstractExpression)exp.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		if (exp instanceof Variable) {
			return ((Variable)exp).isDefined(e);
		}
		if (exp instanceof IndexElementExpression) {
			return ((IndexElementExpression)exp).isDefined(e);
		}
		return true;
	}

	@Override
	public boolean hasDefiniteValue() {
		return exp.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return exp.getDefiniteValue() != null;
	}

	public String toString() {
		return "isdefined(" + exp + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof IsDefined) && ((IsDefined)o).exp.equals(exp);
	}
	
	@Override
	public int hashCode() {
		return exp.hashCode();
	}

}
