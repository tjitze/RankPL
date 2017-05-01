package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
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
 * Implements L-conditioning.
 * This is equivalent to:
 * 	if rank(b) <= x then
 *		observe b [x-rank(b)+rank(-b)] observe -b
 *	else
 *		observe -b [rank(b)-x] observe b
 */
public class ObserveL implements DStatement {

	private BoolExpression b;
	private NumExpression rank;

	public ObserveL(BoolExpression b, int rank) {
		this(b, new IntLiteral(rank));
	}
	
	public ObserveL(BoolExpression b, NumExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) {
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
		DStatement statement = new ProgramBuilder()
				.add(new IfElse(cond, c1, c2))
				.build();
		return statement.getIterator(rt);
	}

	public String toString() {
		String bString = b.toString();
		if (bString.startsWith("(") && bString.endsWith(")")) {
			bString = bString.substring(1, bString.length()-1);
		}
		String rankString = rank.toString();
		if (!(rankString.startsWith("(") && rankString.endsWith(")"))) {
			rankString = "(" + rankString + ")";
		}
		return "observe-l " + rankString + " " + bString;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveL &&
				((ObserveL)o).b.equals(b) &&
				((ObserveL)o).rank == rank;
	}	

	@Override
	public boolean containsVariable(String var) {
		return b.containsVariable(var) || rank.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveL((BoolExpression)this.b.replaceVariable(a, b), (NumExpression)rank.replaceVariable(a, b));
	}
	
	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
		rank.getVariables(list);
	}
}
