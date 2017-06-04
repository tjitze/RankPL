package com.tr.rp.core.rankediterators;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

public class RankTransformIterator extends BufferingIterator<VarStore> {

	private final Expression[] es;
	
	private DStatement exceptionSource = null;
	
	public RankTransformIterator(RankedIterator<VarStore> in, DStatement exceptionSource, Expression ... expressions) throws RPLException {
		super(in);
		this.exceptionSource = exceptionSource;
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
			VarStore v = getItem();
			for (int i = 0; i < es.length; i++) {
				try {
					es[i] = es[i].transformRankExpressions(v, getRank());
				} catch (RPLException e) {
					if (exceptionSource != null) {
						e.setStatement(exceptionSource);
					}
				}
			}
		}
		for (int i = 0; i < es.length; i++) {
			try {
				es[i] = es[i].transformRankExpressions(Integer.MAX_VALUE);
			} catch (RPLException e) {
				if (exceptionSource != null) {
					e.setStatement(exceptionSource);
				}
			}
		}
	}
	
	private final boolean hasRankExpression(Expression[] e) {
		for (int i = 0; i < es.length; i++) {
			if (e[i].hasRankExpression()) return true;
		}
		return false;
	}
	

}
