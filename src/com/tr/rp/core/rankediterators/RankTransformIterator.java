package com.tr.rp.core.rankediterators;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.NumExpression;

public class RankTransformIterator<T extends Expression<T>> extends BufferingIterator {

	private T exp;
	
	public RankTransformIterator(RankedIterator<VarStore> in, T exp) {
		super(in);
		this.exp = transform(exp);
		reset();
		stopBuffering();
	}

	public T getExpression() {
		return exp;
	}
	
	private T transform(T e) {
		while (e.hasRankExpression() && next()) {
			e = e.transformRankExpressions(getItem(), getRank());
		}
		e.transformRankExpressions(Integer.MAX_VALUE);
		return e;
	}
	

}
