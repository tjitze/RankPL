package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Returns true iff given variable is set.
 */
public class IsOfType extends AbstractExpression {

	private final AbstractExpression exp;
	private final Type<?> type;
	
	public IsOfType(AbstractExpression exp, Type<?> type) {
		this.exp = exp;
		this.type = type;
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
		return new IsOfType((AbstractExpression)exp.transformRankExpressions(v, rank), type);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return exp.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new IsOfType((AbstractExpression)exp.replaceEmbeddedFunctionCall(fc, var), type);
	}

	@Override
	public Object getValue(VarStore vs) throws RPLException {
		return type.test(exp.getValue(vs));
	}

	@Override
	public boolean hasDefiniteValue() {
		return exp.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return type.test(exp.getDefiniteValue());
	}

	public String toString() {
		return "is"+ type.getName() + "(" + exp + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof IsOfType) && ((IsOfType)o).exp.equals(exp);
	}
	
	@Override
	public int hashCode() {
		return exp.hashCode();
	}

}
