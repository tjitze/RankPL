package com.tr.rp.statement;

import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.BufferingIterator;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

public class Observe extends DStatement {

	private Expression exp;
	private DStatement exceptionSource;
	
	public Observe(Expression exp) {
		this.exp = exp;
		this.exceptionSource = this;
	}

	public Observe(Expression exp, DStatement exceptionSource) {
		this.exp = exp;
		this.exceptionSource = exceptionSource;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		Expression exp2 = null;
		try {
			// If exp is contradiction/tautology we
			// can immediately return result.
			if (exp.hasDefiniteValue()) {
				if (exp.getDefiniteBoolValue()) {
					return in;
				} else {
					return new AbsurdIterator<VarStore>();
				}
			}
	
			// Replace rank expressions in exp
			RankTransformIterator rt = 
					new RankTransformIterator(in, this, this.exp);
			exp2 = rt.getExpression(0);
			
			// Check contradiction/tautology again.
			if (exp.hasDefiniteValue()) {
				if (exp.getDefiniteBoolValue()) {
					return rt;
				} else {
					return new AbsurdIterator<VarStore>();
				}
			}
			
			// Find first varstore satisfying condition
			// (if there is no varstore then hasnext will be false)
			final BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(rt);
			boolean hasNext = bi.next();
			while (hasNext && !exp2.getBoolValue(bi.getItem())) { 
				hasNext = bi.next();
			}
			// Remember rank of this varstore
			final int conditioningOffset = hasNext? bi.getRank(): Integer.MAX_VALUE;
			// Move back one item so that we can reuse the buffering iterator
			if (hasNext) bi.reset(bi.getIndex() - 1);
			
			final Expression exp2f = exp2;
			return new RankedIterator<VarStore>() {
				
				@Override
				public boolean next() throws RPLException {
					try {
						// Find next varstore satisfying condition
						boolean hasNext = bi.next();
						while (hasNext && !exp2f.getBoolValue(bi.getItem())) { 
							hasNext = bi.next();
						}
						return hasNext;
					} catch (RPLException e) {
						if (e.getExpression() == exp2f) {
							e.setStatement(exceptionSource);
						}
						throw e;
					}
				}
	
				@Override
				public VarStore getItem() throws RPLException {
					try {
						return bi.getItem();
					} catch (RPLException e) {
						e.setStatement(Observe.this);
						throw e;
					}
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
					return "ObserveIterator(exp=" + exp2f + ", buffer=" + bi + ")";
				}
			};
		} catch (RPLException e) {
			if (e.getExpression() == exp2) {
				e.setStatement(exceptionSource);
			}
			throw e;
		}
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
	public LanguageElement replaceVariable(String a, String b) {
		return new Observe((Expression)exp.replaceVariable(a, b));
	}
	
	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new Observe(rewrittenExp.getExpression()), rewrittenExp.getAssignments());
		} else {
			return this;
		}
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}	

}
