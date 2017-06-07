package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

public class Conditional extends AbstractExpression {

	private final AbstractExpression condition;
	private final AbstractExpression e1;
	private final AbstractExpression e2;
	
	public Conditional(AbstractExpression condition, AbstractExpression e1, AbstractExpression e2) {
		this.condition = condition;
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	public void getVariables(Set<String> list) {
		condition.getVariables(list);
		e1.getVariables(list);
		e2.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Conditional((AbstractExpression)condition.replaceVariable(a, b),
				(AbstractExpression)e1.replaceVariable(a, b),
				(AbstractExpression)e2.replaceVariable(a, b));
				
	}

	@Override
	public boolean hasRankExpression() {
		return condition.hasRankExpression() ||
				e1.hasRankExpression() ||
				e2.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Conditional(condition.transformRankExpressions(v, rank),
				e1.transformRankExpressions(v, rank),
				e2.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = condition.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		fc = e1.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		return e2.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Conditional(condition.replaceEmbeddedFunctionCall(fc, var),
				e1.replaceEmbeddedFunctionCall(fc, var),
				e2.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore v) throws RPLException {
		return condition.getBoolValue(v)?
				e1.getValue(v): e2.getValue(v);
	}

	@Override
	public boolean hasDefiniteValue() {
		return condition.hasDefiniteValue() && 
				e1.hasDefiniteValue() &&
				e2.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return condition.getDefiniteBoolValue()?
				e1.getDefiniteValue(): e2.getDefiniteValue();
	}
	
	public String toString() {
		return "(" + condition + "? " + e1 + ": " + e2 + ")";
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Conditional) &&
				((Conditional)o).condition.equals(condition) &&
				((Conditional)o).e1.equals(e1) &&
				((Conditional)o).e2.equals(e2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(condition, e1, e2);
	}

}
