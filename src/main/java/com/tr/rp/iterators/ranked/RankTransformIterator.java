package com.tr.rp.iterators.ranked;

import java.util.Arrays;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.Rank;
import com.tr.rp.varstore.VarStore;

public class RankTransformIterator extends BufferingIterator<VarStore> {

	private final AbstractExpression[] es;
	
	private AbstractStatement exceptionSource = null;
	
	public RankTransformIterator(RankedIterator<VarStore> in, AbstractStatement exceptionSource, AbstractExpression ... expressions) throws RPLException {
		super(in);
		this.exceptionSource = exceptionSource;
		this.es = Arrays.copyOf(expressions, expressions.length);
		transform();
		reset();
		stopBuffering();
	}
	
	public AbstractExpression getExpression(int i) {
		return es[i];
	}
	
	public AbstractExpression[] getExpressions() {
		return es;
	}
	
	private final void transform() throws RPLException {
		while (rankTransformationNeeded(es) && next()) {
			VarStore v = getItem();
			for (int i = 0; i < es.length; i++) {
				try {
					es[i] = es[i].doRankExpressionTransformation(v, getRank());
				} catch (RPLException e) {
					if (exceptionSource != null) {
						e.setStatement(exceptionSource);
					}
				}
			}
		}
		for (int i = 0; i < es.length; i++) {
			try {
				es[i] = es[i].transformRankExpressions(Rank.MAX);
			} catch (RPLException e) {
				if (exceptionSource != null) {
					e.setStatement(exceptionSource);
				}
			}
		}
	}
	
	private final boolean rankTransformationNeeded(AbstractExpression[] e) {
		for (int i = 0; i < es.length; i++) {
			if (e[i].needsRankExpressionTransformation()) return true;
		}
		return false;
	}
	

}
