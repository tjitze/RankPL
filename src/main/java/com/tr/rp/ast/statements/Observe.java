package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.List;
import java.util.Objects;

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

public class Observe extends AbstractStatement implements ObserveErrorHandler {

	private AbstractExpression exp;
	private ObserveErrorHandler errorHandler;
	
	public Observe(AbstractExpression exp) {
		this.exp = exp;
		this.errorHandler = this;
	}

	public Observe(AbstractExpression exp, ObserveErrorHandler errorHandler) {
		this.exp = exp;
		this.errorHandler = errorHandler;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		AbstractExpression exp2 = null;
		
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
			while (hasNext && !getCheckedValue(exp2, bi)) { 
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
					// Find next varstore satisfying condition
					boolean hasNext = bi.next();
					while (hasNext && !getCheckedValue(exp2f, bi)) { 
						hasNext = bi.next();
					}
					return hasNext;
				}
	
				@Override
				public VarStore getItem() throws RPLException {
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
					return "ObserveIterator(exp=" + exp2f + ", buffer=" + bi + ")";
				}
			};
	}

	private boolean getCheckedValue(AbstractExpression exp2, BufferingIterator<VarStore> bi) throws RPLException {
		try {
			return exp2.getBoolValue(bi.getItem());
		} catch (RPLException e) {
			errorHandler.observeConditionError(e);
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
	public int hashCode() {
		return Objects.hash(exp);
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

	@Override
	public void observeConditionError(RPLException e) throws RPLException {
		e.setStatement(this);
		e.setExpression(exp);
		throw e;
	}	

}
