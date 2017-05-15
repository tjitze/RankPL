package com.tr.rp.expressions;

import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

public class Conditional extends Expression {

	private final Expression condition;
	private final Expression e1;
	private final Expression e2;
	
	public Conditional(Expression condition, Expression e1, Expression e2) {
		this.condition = condition;
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return condition.containsVariable(var) ||
				e1.containsVariable(var) ||
				e2.containsVariable(var);
	}

	@Override
	public void getVariables(Set<String> list) {
		condition.getVariables(list);
		e1.getVariables(list);
		e2.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Conditional((Expression)condition.replaceVariable(a, b),
				(Expression)e1.replaceVariable(a, b),
				(Expression)e2.replaceVariable(a, b));
				
	}

	@Override
	public boolean hasRankExpression() {
		return condition.hasRankExpression() ||
				e1.hasRankExpression() ||
				e2.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Conditional(condition.transformRankExpressions(v, rank),
				e1.transformRankExpressions(v, rank),
				e2.transformRankExpressions(v, rank));
	}

	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		FunctionCall fc = condition.getEmbeddedFunctionCall();
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
	public Expression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
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

}
