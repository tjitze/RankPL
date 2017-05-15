package com.tr.rp.core.rankediterators;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

public class RankTransformIterator extends BufferingIterator<VarStore> {

	private final Expression[] es;
	
	public RankTransformIterator(RankedIterator<VarStore> in, Expression ... expressions) throws RPLException {
		super(in);
		this.es = expressions;
		transform();
		reset();
		stopBuffering();
	}

	public Expression getExpression(int i) {
		return es[i];
	}
	
	public Expression[] getExpressions() {
		return es;
	}
	
	private final void transform() throws RPLException {
		while (hasRankExpression(es) && next()) {
			for (int i = 0; i < es.length; i++) {
				es[i] = es[i].transformRankExpressions(getItem(), getRank());
			}
		}
		for (int i = 0; i < es.length; i++) {
			es[i] = es[i].transformRankExpressions(Integer.MAX_VALUE);
		}
	}
	
	private final boolean hasRankExpression(Expression[] e) {
		for (int i = 0; i < es.length; i++) {
			if (e[i].hasRankExpression()) return true;
		}
		return false;
	}
	

}
