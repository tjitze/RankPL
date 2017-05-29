package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;

/**
 * Returns true iff given variable is set.
 */
public class IsSet extends Expression {

	private final Expression exp;

	public IsSet(Expression exp) {
		this.exp = exp;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return this.exp.containsVariable(var);
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new IsSet((Expression)exp.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return exp.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new IsSet((Expression)exp.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return exp.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new IsSet((Expression)exp.replaceEmbeddedFunctionCall(fc, var));
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
