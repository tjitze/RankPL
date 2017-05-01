package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.BufferingIterator;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;

public class Observe implements DStatement {

	private BoolExpression exp;
	
	public Observe(BoolExpression exp) {
		this.exp = exp;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) {
		
		// If exp is contradiction/tautology we
		// can immediately return result.
		if (exp.isTautology()) {
			return in;
		}
		if (exp.isContradiction()) {
			return new AbsurdIterator<VarStore>();
		}

		// Replace rank expressions in exp
		RankTransformIterator<BoolExpression> rt = 
				new RankTransformIterator<BoolExpression>(in, this.exp);
		BoolExpression exp2 = rt.getExpression(0);
		
		// Check contradiction/tautology again.
		if (exp2.isTautology()) {
			return rt;
		}
		if (exp2.isContradiction()) {
			return new AbsurdIterator<VarStore>();
		}		
		// Find first varstore satisfying condition
		// (if there is no varstore then hasnext will be false)
		final BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(rt);
		boolean hasNext = bi.next();
		while (hasNext && !exp2.isTrue(bi.getItem())) { 
			hasNext = bi.next();
		}
		// Remember rank of this varstore
		final int conditioningOffset = hasNext? bi.getRank(): Integer.MAX_VALUE;
		// Move back one item so that we can reuse the buffering iterator
		if (hasNext) bi.reset(bi.getIndex() - 1);
		return new RankedIterator<VarStore>() {
			
			@Override
			public boolean next() {
				// Find next varstore satisfying condition
				boolean hasNext = bi.next();
				while (hasNext && !exp2.isTrue(bi.getItem())) { 
					hasNext = bi.next();
				}
				return hasNext;
			}

			@Override
			public VarStore getItem() {
				return bi.getItem();
			}

			@Override
			public int getRank() {
				// Subtract offset so that ranks start with zero
				return Rank.sub(bi.getRank(),  conditioningOffset);
			}
			
			@Override
			public int getConditioningOffset() {
				return conditioningOffset;
			}
			
			public String toString() {
				return "ObserveIterator(exp=" + exp2 + ", buffer=" + bi + ")";
			}
		};
	}

	public String toString() {
		String expString = exp.toString();
		if (expString.startsWith("(") && expString.endsWith(")")) {
			expString = expString.substring(1, expString.length()-1);
		}
		return "observe " + expString;
	}
	
	public boolean equals(Object o) {
		return o instanceof Observe && ((Observe)o).exp.equals(exp);
	}
	
	@Override
	public boolean containsVariable(String var) {
		return exp.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Observe((BoolExpression)exp.replaceVariable(a, b));
	}
	
	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}
}
