package com.tr.rp.statement;

import java.util.Arrays;
import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.FunctionCall;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.tools.Pair;

/**
 * The assign statement takes as input a variable, an (optional) list
 * of array indices, and an expression. 
 * 
 * If the list of array indices is empty, the effect of this statement
 * is that the given variable is assigned the value of the given 
 * expression. If the list of array indices is nonempty, the effect is
 * that the array associated with the variable name (indexed by the 
 * array indices) is assigned the value of the given expression.
 */
public class Assign implements DStatement {

	private final NumExpression exp;
	private final NumExpression[] index;
	private final String var;
	
	public Assign(String var, NumExpression[] index, NumExpression exp) {
		this.var = var;
		this.exp = exp;
		this.index = index;
	}

	public Assign(String var, NumExpression[] index, int value) {
		this(var, index, new IntLiteral(value));
	}

	public Assign(String var, int value) {
		this(var, new NumExpression[0], new IntLiteral(value));
	}

	public Assign(String var1, String var2) {
		this(var1, new NumExpression[0], new Var(var2));
	}

	public Assign(String var, NumExpression e) {
		this(var, new NumExpression[0], e);
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) {
		RankTransformIterator<NumExpression> rt = 
				new RankTransformIterator<NumExpression>(in, this.exp);
		NumExpression exp2 = rt.getExpression(0);
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() {
				return rt.next();
			}

			@Override
			public VarStore getItem() {
				if (rt.getItem() == null) return null;
				String internalName = Var.getInternalName(var, index, rt.getItem());
				return rt.getItem().create(internalName, exp2.getVal(rt.getItem()));
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
	}
	
	public String toString() {
		String expString = exp.toString();
		if (expString.startsWith("(") && expString.endsWith(")")) {
			expString = expString.substring(1, expString.length()-1);
		}
		return var + " := " + expString;
	}
	
	public boolean equals(Object o) {
		return o instanceof Assign &&
				((Assign)o).var.equals(var) &&
				((Assign)o).exp.equals(exp);
	}

	@Override
	public boolean containsVariable(String var) {
		for (int i = 0; i < index.length; i++) {
			if (index[i].containsVariable(var)) return true;
		}
		return exp.containsVariable(var) || var.equals(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		NumExpression[] newIndex = new NumExpression[index.length];
		for (int i = 0; i < index.length; i++) {
			newIndex[i] = (NumExpression)index[i].replaceVariable(a, b);
		}
		return new Assign(var.equals(a)? b: var, newIndex, (NumExpression)exp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		list.add(var);
		Arrays.stream(index).forEach(e -> e.getVariables(list));
		exp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		Pair<List<Pair<String, FunctionCall>>, NumExpression> rewrittenExp
			= FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.a.isEmpty()) {
			return this;
		}
		return new FunctionCallForm(new Assign(var, rewrittenExp.b), rewrittenExp.a);
	}	
}
