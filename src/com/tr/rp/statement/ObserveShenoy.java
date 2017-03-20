package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.rankediterators.RankTransformIterator;
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
	private NumExpression rank;

	public ObserveShenoy(BoolExpression b, int rank) {
		this(b, new IntLiteral(rank));
	}
	
	public ObserveShenoy(BoolExpression b, NumExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator getIterator(RankedIterator in) {
		NumExpression rb = new RankExpression(b);
		NumExpression rnb = new RankExpression(b.negate());
		// Do rank transformation here
		NumExpression temp = new Plus(rb, rnb);
		RankTransformIterator<NumExpression> rt = 
				new RankTransformIterator<NumExpression>(in, temp);
		rb = ((Plus)temp).getE1();
		rnb = ((Plus)temp).getE2();
		BoolExpression cond = new LessOrEq(rb, rank);
		NumExpression r1 = new Minus(new Plus(rank, rnb), rb);
		NumExpression r2 = new Minus(rb, rank);
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
		return statement.getIterator(rt);
	}

	public String toString() {
		return "observeShenoy("+rank+") " + b;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveShenoy &&
				((ObserveShenoy)o).b.equals(b) &&
				((ObserveShenoy)o).statement.equals(statement) &&
				((ObserveShenoy)o).rank == rank;
	}	

}
