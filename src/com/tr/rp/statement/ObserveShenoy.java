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
 * Implements Shenoy conditioning (also called evidence-oriented conditioning).
 * This is equivalent to:
 * 	if rank(b) <= x then
 *		observe b [x-rank(b)+rank(-b)] observe -b
 *	else
 *		observe -b [rank(b)-x] observe b
 */
public class ObserveShenoy implements DStatement {

	private BoolExpression b;
	private DStatement statement;
	private int rank;

	public ObserveShenoy(BoolExpression b, int rank) {
		this(b, new IntLiteral(rank));
	}
	
	public ObserveShenoy(BoolExpression b, NumExpression x) {
		this.b = b;
		NumExpression rb = new RankExpression(b);
		NumExpression rnb = new RankExpression(b.negate());
		BoolExpression cond = new LessOrEq(rb, x);
		NumExpression r1 = new Minus(new Plus(x, rnb), rb);
		NumExpression r2 = new Minus(rb, x);
		DStatement c1 = new Choose(
				new Observe(b),
				new Observe(b.negate()),
				r1);
		DStatement c2 = new Choose(
				new Observe(b.negate()),
				new Observe(b),
				r2);
		statement = new ProgramBuilder()
				.add(new IfElse(cond, c1, c2))
				.build();
	}

	@Override
	public RankedIterator getIterator(RankedIterator in) {
		return statement.getIterator(in);
	}

	public String toString() {
		return "observeShenoy("+rank+") " + b;
	}
}
