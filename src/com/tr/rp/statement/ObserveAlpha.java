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
 * Implements Alpha conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveAlpha implements DStatement {

	private BoolExpression b;
	private DStatement statement;
	private int rank;
	
	public ObserveAlpha(BoolExpression b, int x) {
		this(b, new IntLiteral(x));
	}
	public ObserveAlpha(BoolExpression b, NumExpression x) {
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
		return "observeShenoy("+rank+") " + b;
	}
}
