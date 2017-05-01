package com.tr.rp.expressions.num;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

/**
 * Length of array function.
 */
public class Len extends NumExpression {

	private final String variable;
	private final NumExpression[] index;
	
	public Len(String variable, NumExpression ... index) {
		this.variable = variable;
		this.index = index;
	}
	

	public String toString() {
		return "len("+variable+(index.length > 0? Arrays.toString(index): "")+")";
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
		return new Len(variable.equals(a)? b: variable, newIndex);
	}

	@Override
	public int getVal(VarStore e) {
		String var = Var.getInternalName(variable, index, e);
		return getLen(e, var, 0, e.getSize());
	}

	/**
	 * 
	 *     0123456789
	 */
	private int getLen(VarStore e, String var, int start, int size) {
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (!e.containsVar(var + "[" + mid + "]")) hi = mid - 1;
            else if (e.containsVar(var + "[" + mid + "]")) lo = mid + 1;
            else return mid;
        }
        return -1;
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}


	@Override
	public int getDefiniteValue() {
		return 0;
	}


	@Override
	public boolean hasRankExpression() {
		return false;
	}


	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) {
		list.add(variable);
		Arrays.stream(index).forEach(e -> e.getVariables(list));
	}
}
