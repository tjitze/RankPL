package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.AbsurdIterator;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.Rank;
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
		final AbstractExpression exp2 = rt.getExpression(0);
		
		// Check contradiction/tautology again.
		if (exp2.hasDefiniteValue()) {
			if (exp2.getDefiniteBoolValue()) {
				return rt;
			} else {
				return new AbsurdIterator<VarStore>();
			}
		}
		
		return new RankedIterator<VarStore>() {
			
			private int conditioningOffset = Rank.MAX;
			
			@Override
			public boolean next() throws RPLException {
				// Find next varstore satisfying condition
				boolean hasNext = rt.next();
				while (hasNext && !getCheckedValue(exp2, rt)) { 
					hasNext = rt.next();
				}
				// Store conditioning offset
				if (hasNext && conditioningOffset == Rank.MAX) {
					conditioningOffset = rt.getRank();
				}
				return hasNext;
			}

			@Override
			public VarStore getItem() throws RPLException {
				return rt.getItem();
			}

			@Override
			public int getRank() {
				// Subtract offset so that ranks start with zero
				return Rank.sub(rt.getRank(), conditioningOffset);
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
