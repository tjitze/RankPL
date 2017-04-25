package com.tr.rp.core.rankediterators;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public class RankTransformIterator<T extends Expression<T>> extends BufferingIterator<VarStore> {

	private final T[] es;
	
	public RankTransformIterator(RankedIterator<VarStore> in, T ... expressions) {
		super(in);
		this.es = expressions;
		transform();
		reset();
		stopBuffering();
	}

	public T getExpression(int i) {
		return es[i];
	}
	
	public T[] getExpressions() {
		return es;
	}
	
	private final void transform() {
		while (hasRankExpression(es) && next()) {
			for (int i = 0; i < es.length; i++) {
				es[i] = es[i].transformRankExpressions(getVarStore(), getRank());
			}
		}
		for (int i = 0; i < es.length; i++) {
			es[i] = es[i].transformRankExpressions(Integer.MAX_VALUE);
		}
	}
	
	private final boolean hasRankExpression(T[] e) {
		for (int i = 0; i < es.length; i++) {
			if (e[i].hasRankExpression()) return true;
		}
		return false;
	}
	

}
