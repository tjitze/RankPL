package com.tr.rp.expressions.num;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.VarStore;

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
