package com.tr.rp.expressions.bool;

import java.util.Set;
import java.util.function.Predicate;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

public class Pred extends BoolExpression {

	private final Predicate<VarStore> pred;
	
	public Pred(Predicate<VarStore> pred) {
		this.pred = pred;
	}
	
	@Override
	public boolean isTrue(VarStore e) {
		return pred.test(e);
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	@Override
	public boolean isContradiction() {
		return false;
	}

	@Override
	public boolean isTautology() {
		return false;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}

	public boolean equals(Object o) {
		return o instanceof Pred && ((Pred)o).pred.equals(pred);
	}

	@Override
	public boolean containsVariable(String var) {
		throw new UnsupportedOperationException("Cannot check for variable occurrence in Pred objects");
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		throw new UnsupportedOperationException("Cannot replace variables in Pred objects");
	}

	@Override
	public void getVariables(Set<String> list) { }

}
