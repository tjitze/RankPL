package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;

/**
 * Implements J-conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveJ implements DStatement {

	private BoolExpression b;
	private NumExpression rank;
	
	public ObserveJ(BoolExpression b, int rank) {
		this(b, new IntLiteral(rank));
	}
	public ObserveJ(BoolExpression b, NumExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) {
		return new Choose(new Observe(b), new Observe(b.negate()), rank).getIterator(in);
	}

	public String toString() {
		return "observeShenoy("+b+") " + b;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveJ &&
				((ObserveJ)o).b.equals(b);
	}
	
	@Override
	public boolean containsVariable(String var) {
		return b.containsVariable(var) || rank.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveJ((BoolExpression)this.b.replaceVariable(a, b), (NumExpression)rank.replaceVariable(a, b));
	}
}
