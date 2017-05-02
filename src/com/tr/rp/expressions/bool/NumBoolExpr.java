package com.tr.rp.expressions.bool;

import java.util.Set;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.FunctionCall;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;

public class NumBoolExpr extends BoolExpression {

	public final NumExpression e;
	
	public NumBoolExpr(NumExpression e) {
		this.e = e;
	}

	@Override
	public boolean isTrue(VarStore env) {
		return (new Not(new Equals(e, new IntLiteral(0)))).isTrue(env);
	}


	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		return new NumBoolExpr(e.transformRankExpressions(v, rank));
	}
	
	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}
	
	public String toString() {
		return e.toString();
	}


	@Override
	public boolean isContradiction() {
		return (new Not(new Equals(e, new IntLiteral(0)))).isContradiction();
	}

	@Override
	public boolean isTautology() {
		return (new Not(new Equals(e, new IntLiteral(0)))).isTautology();
	}

	public boolean equals(Object o) {
		return o instanceof NumBoolExpr && ((NumBoolExpr)o).e.equals(e);
	}

	@Override
	public boolean containsVariable(String var) {
		return e.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new NumBoolExpr((NumExpression)e.replaceVariable(a, b));
	}
	
	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public BoolExpression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		return new NumBoolExpr(e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}
}
