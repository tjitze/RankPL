package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

public class Assign implements DStatement {

	private NumExpression exp;
	private String var;
	
	public Assign(String var, NumExpression exp) {
		this.var = var;
		this.exp = exp;
	}
	
	public Assign(String var, int value) {
		this(var, new IntLiteral(value));
	}

	public Assign(String var1, String var2) {
		this(var1, new Var(var2));
	}

	@Override
	public RankedIterator getIterator(final RankedIterator in) {
		RankTransformIterator<NumExpression> rt = 
				new RankTransformIterator<NumExpression>(in, this.exp);
		NumExpression exp2 = rt.getExpression(0);
		return new RankedIterator() {

			@Override
			public boolean next() {
				return rt.next();
			}

			@Override
			public VarStore getVarStore() {
				if (rt.getVarStore() == null) return null;
				return rt.getVarStore().create(getVar(rt.getVarStore()), exp2.getVal(rt.getVarStore()));
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
	}
	
	public String toString() {
		return var + " := " + exp;
	}
	
	protected String getVar(VarStore v) {
		return var;
	}
	
	public boolean equals(Object o) {
		return o instanceof Assign &&
				((Assign)o).var.equals(var) &&
				((Assign)o).exp.equals(exp);
	}

	@Override
	public boolean containsVariable(String var) {
		return this.var.equals(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		if (var.equals(a)) {
			return new Assign(b, exp);
		} else {
			return this;
		}
	}
}
