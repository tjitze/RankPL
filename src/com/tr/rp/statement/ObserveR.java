package com.tr.rp.statement;
//package com.tr.defaultprogramming.denotational.statement;
//
//import com.tr.defaultprogramming.denotational.core.DStatement;
//import com.tr.defaultprogramming.denotational.core.ProgramBuilder;
//import com.tr.defaultprogramming.denotational.core.Ranking;
//
//public class ObserveR implements DStatement {
//
//	private BoolExp exp;
//	private DStatement in;
//	private int rank;
//	
//	public ObserveR(BoolExp exp, int rank) {
//		this.exp = exp;
//		in = new ProgramBuilder()
//				.add(new Choose(
//						new Observe(exp),
//						new Observe(exp.negate()),
//						rank))
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
//	
//}
