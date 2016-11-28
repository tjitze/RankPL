package com.tr.rp.denotational.statement;
//package com.tr.defaultprogramming.denotational.statement;
//
//import com.tr.defaultprogramming.denotational.core.DExpression;
//import com.tr.defaultprogramming.denotational.core.DStatement;
//import com.tr.defaultprogramming.denotational.core.ProgramBuilder;
//import com.tr.defaultprogramming.denotational.core.Ranking;
//import com.tr.defaultprogramming.denotational.core.rankediterators.RankedIterator;
//import com.tr.defaultprogramming.denotational.expressions.IntLiteral;
//import com.tr.defaultprogramming.denotational.expressions.LessOrEq;
//import com.tr.defaultprogramming.denotational.expressions.Minus;
//import com.tr.defaultprogramming.denotational.expressions.Plus;
//import com.tr.defaultprogramming.denotational.expressions.RankExpression;
//import com.tr.defaultprogramming.exec.VarStore;
//
//public class ObserveE implements DStatement {
//
//	private BoolExp b;
//	private DStatement in;
//	private int rank;
//	
//	public ObserveE(BoolExp b, int rank) {
//		this.b = b;
//		DExpression x = new IntLiteral(rank);
//		DExpression rb = new RankExpression(b);
//		DExpression rnb = new RankExpression(b.negate());
//		BoolExp cond = new LessOrEq(rb, x);
//		DExpression r1 = new Plus(new Minus(x, rb), rnb);
//		DExpression r2 = new Minus(rb, x);
//		DStatement c1 = new Choose(
//				new Observe(b),
//				new Observe(b.negate()),
//				r1);
//		DStatement c2 = new Choose(
//				new Observe(b.negate()),
//				new Observe(b),
//				r2);
//		in = new ProgramBuilder()
//				.add(new IfElse(cond, c1, c2))
//				.build();
//	}
//
//	/**
//	 * Observe e conditions on e
//	 * 	and returns blocked ranking if undefined.
//	 * 		
//	 */
//	@Override
//	public Ranking apply(Ranking input) {
//		return in.apply(input);
//	}
//
//	@Override
//	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//}
