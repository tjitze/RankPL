package com.tr.rp.denotational.statement;
//package com.tr.defaultprogramming.denotational.statement;
//
//import com.tr.defaultprogramming.denotational.core.DStatement;
//import com.tr.defaultprogramming.denotational.core.Ranking;
//
//public class While implements DStatement {
//
//	private BoolExp exp;
//	private DStatement a;
//	private int maxDepth = Integer.MAX_VALUE;
//	
//	public While(BoolExp exp, DStatement a) {
//		this.exp = exp;
//		this.a = a;
//	}
//	
//	@Override
//	public Ranking apply(Ranking input) {
//		if (input.isUndefined()) return input;
//
//		Ranking r = input;
//		
//		// TODO: check for fixed point
//		int depth = 0;
//		while (r.getRank(exp) != Integer.MAX_VALUE) {
//			if (depth > maxDepth) {
//				return new Ranking().setUndefined(true);
//			}
//			r = a.apply(r);
//			if (r.isUndefined()) return r;
//			depth++;
//		}
//		
//		return r;
//	}
//	
//	public While setMaxDepth(int maxDepth) {
//		this.maxDepth = maxDepth;
//		return this;
//	}
//}
