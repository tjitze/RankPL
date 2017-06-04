package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.varstore.VarStore;

/**
 * Returns true iff given variable is set.
 */
public class IsSet extends AbstractExpression {

	private final AbstractExpression exp;

	public IsSet(AbstractExpression exp) {
		this.exp = exp;
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new IsSet((AbstractExpression)exp.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return exp.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new IsSet((AbstractExpression)exp.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return exp.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new IsSet((AbstractExpression)exp.replaceEmbeddedFunctionCall(fc, var));
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
		return "isset(" + exp + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof IsSet) && ((IsSet)o).exp.equals(exp);
	}

}
