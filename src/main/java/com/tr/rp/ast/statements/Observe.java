package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.AbsurdIterator;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.Rank;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

public class Observe extends AbstractStatement {

	private AbstractExpression exp;
	private AbstractStatement exceptionSource;
	
	public Observe(AbstractExpression exp) {
		this.exp = exp;
		this.exceptionSource = this;
	}

	public Observe(AbstractExpression exp, AbstractStatement exceptionSource) {
		this.exp = exp;
		this.exceptionSource = exceptionSource;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		AbstractExpression exp2 = null;
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
			final int conditioningOffset = hasNext? bi.getRank(): Rank.MAX;
			// Move back one item so that we can reuse the buffering iterator
			if (hasNext) bi.reset(bi.getIndex() - 1);
			
			final AbstractExpression exp2f = exp2;
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
		return new Observe((AbstractExpression)exp.replaceVariable(a, b));
	}
	
	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
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
