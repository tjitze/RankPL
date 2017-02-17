package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

/**
 * Array assignment. Sets value of element of an array, where
 * the index is determined by a numeric expression.
 */
public class IndexedAssign implements DStatement {

	private NumExpression[] indexedValues;
	private String var;
	
	public IndexedAssign(String var, NumExpression ... indexedValues) {
		this.var = var;
		this.indexedValues = indexedValues;
		if (indexedValues.length % 2 != 0) throw new IllegalArgumentException("Even number of index/value elements required");
	}
	
	@Override
	public RankedIterator getIterator(final RankedIterator in) {
		// Transform rank expressions
		RankTransformIterator<NumExpression> rt = 
				new RankTransformIterator<NumExpression>(in, indexedValues);
		indexedValues = rt.getExpressions();

		return new RankedIterator() {

			@Override
			public boolean next() {
				return rt.next();
			}

			@Override
			public VarStore getVarStore() {
				if (rt.getVarStore() == null) return null;
				return rt.getVarStore().create(var, indexedValues);
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
	}
	
	public String toString() {
		return var + " := <indexed " + indexedValues + ">";
	}

}
