package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.LessOrEq;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Minus;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.RankExpression;

/**
 * Implements J-conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveJ implements DStatement {

	private BoolExpression b;
	private DStatement statement;
	
	public ObserveJ(BoolExpression b, int x) {
		this(b, new IntLiteral(x));
	}
	public ObserveJ(BoolExpression b, NumExpression x) {
		//		observe b [rank] observe -b
		this.b = b;
		statement = new Choose(
				new Observe(b),
				new Observe(b.negate()),
				x);
	}

	@Override
	public RankedIterator getIterator(RankedIterator in) {
		return statement.getIterator(in);
	}

	public String toString() {
		return "observeShenoy("+b+") " + b;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveJ &&
				((ObserveJ)o).b.equals(b) &&
				((ObserveJ)o).statement.equals(statement);
	}	
}
