package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;
import com.tr.rp.denotational.core.VarStore;

public class Var extends DExpression {

	public final String variable;
	
	public Var(String variable) {
		this.variable = variable;
	}

	@Override
	public int getVal(VarStore e) {
		return e.getValue(variable);
	}
	
}
