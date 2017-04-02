package com.tr.rp.expressions.num;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

public class Var extends NumExpression {

	public final String variable;
	
	public Var(String variable) {
		this.variable = variable;
	}

	@Override
	public int getVal(VarStore e) {
		if (e == null) throw new NullPointerException();
		return e.getValue(variable);
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
		return variable;
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public int getDefiniteValue() {
		return 0;
	}

	public boolean equals(Object o) {
		return o instanceof Var && ((Var)o).variable.equals(variable);
	}

	@Override
	public boolean containsVariable(String var) {
		return var.equals(variable);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		if (a.equals(variable)) {
			return new Var(b);
		} else {
			return this;
		}
	}
}
