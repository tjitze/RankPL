package com.tr.rp.expressions.bool;

import java.util.Set;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.FunctionCall;

public class Not extends BoolExpression {

	public final BoolExpression e;
	
	public Not(BoolExpression e) {
		this.e = e;
	}

	@Override
	public boolean isTrue(VarStore env) {
		return !e.isTrue(env);
	}
	
	public BoolExpression negate() {
		return e;
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		BoolExpression te = e.transformRankExpressions(v, rank);
		if (e != te) {
			return new Not(te);
		} else {
			return this;
		}
	}
	
	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}
	
	public String toString() {
		return "Not(" + e + ")";
	}


	@Override
	public boolean isContradiction() {
		return e.isTautology();
	}

	@Override
	public boolean isTautology() {
		return e.isContradiction();
	}

	public boolean equals(Object o) {
		return o instanceof Not && ((Not)o).e.equals(e);
	}

	@Override
	public boolean containsVariable(String var) {
		return e.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Not((BoolExpression)e.replaceVariable(a, b));
	}
	
	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public BoolExpression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		return new Not((BoolExpression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}
}
