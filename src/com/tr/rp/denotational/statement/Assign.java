package com.tr.rp.denotational.statement;

import com.tr.rp.denotational.core.DExpression;
import com.tr.rp.denotational.core.DStatement;
import com.tr.rp.denotational.core.VarStore;
import com.tr.rp.denotational.core.rankediterators.RankedIterator;
import com.tr.rp.denotational.expressions.num.IntLiteral;
import com.tr.rp.denotational.expressions.num.Var;

public class Assign implements DStatement {

	private DExpression exp;
	private String var;
	
	public Assign(String var, DExpression exp) {
		this.var = var;
		this.exp = exp;
	}
	
	public Assign(String var, int value) {
		this(var, new IntLiteral(value));
	}

	public Assign(String var1, String var2) {
		this(var1, new Var(var2));
	}

	int rank = 0;

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> parent) {
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() {
				return parent.next();
			}

			@Override
			public VarStore getItem() {
				return parent.getItem().create(var, exp.getVal(parent.getItem()));
			}

			@Override
			public int getRank() {
				return parent.getRank();
			}
		};
	}
	
}
