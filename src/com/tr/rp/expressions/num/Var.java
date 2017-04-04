package com.tr.rp.expressions.num;

import java.util.Arrays;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

public class Var extends NumExpression {

	public final String variable;
	public final NumExpression[] index;
	
	public Var(String variable, NumExpression ... index) {
		this.variable = variable;
		this.index = index;
	}

	public static String getInternalName(String variable, NumExpression[] index, VarStore e) {
		String internalName = variable;
		for (int i = 0; i < index.length; i++) {
			internalName += "[" + index[i].getVal(e) + "]";
		}
		return internalName;
	}
	
	@Override
	public int getVal(VarStore e) {
		if (e == null) throw new NullPointerException();
		return e.getValue(getInternalName(variable, index, e));
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		NumExpression[] newIndex = new NumExpression[index.length];
		for (int i = 0; i < index.length; i++) {
			newIndex[i] = index[i].transformRankExpressions(v, rank);
		}
		return new Var(variable, newIndex);
	}

	@Override
	public boolean hasRankExpression() {
		for (int i = 0; i < index.length; i++) {
			if (index[i].hasRankExpression()) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String name = variable;
		for (int i = 0; i < index.length; i++) {
			name += "[" + index[i].toString() + "]";
		}
		return name;
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
		return o instanceof Var && ((Var)o).variable.equals(variable)
				&& Arrays.deepEquals(((Var)o).index, index);
	}

	@Override
	public boolean containsVariable(String var) {
		if (var.equals(variable)) {
			return true;
		}
		for (int i = 0; i < index.length; i++) {
			if (index[i].containsVariable(var)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		NumExpression[] newIndex = new NumExpression[index.length];
		for (int i = 0; i < index.length; i++) {
			newIndex[i] = (NumExpression)index[i].replaceVariable(a, b);
		}
		return new Var(variable.equals(a)? b: variable, newIndex);
	}
}
