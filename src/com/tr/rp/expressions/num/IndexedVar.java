package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;

public class IndexedVar extends NumExpression {

	public final String variable;
	public final NumExpression index;
	
	public IndexedVar(String variable, NumExpression index) {
		this.variable = variable;
		this.index = index;
	}

	public IndexedVar(String variable, String indexVariable) {
		this.variable = variable;
		this.index = new Var(indexVariable);
	}

	@Override
	public int getVal(VarStore e) {
		if (e == null) throw new NullPointerException();
		return e.getElement(variable, index.getVal(e));
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}
	
	public String toString() {
		return "_"+variable;
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public int getDefiniteValue() {
		return 0;
	}

	
}
