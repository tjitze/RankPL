package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.AbsurdIterator;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.iterators.ranked.MergingIteratorFixed;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.IteratorSplitter;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.Rank;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class IfElse extends AbstractStatement implements IfElseErrorHandler, ObserveErrorHandler {

	private AbstractExpression exp;
	private AbstractStatement a, b;
	private IfElseErrorHandler errorHandler;
	
	public IfElse(AbstractExpression exp, AbstractStatement a, AbstractStatement b) {
		this.exp = exp;
		this.a = a;
		this.b = b;
		this.errorHandler = this;
	}

	public IfElse(AbstractExpression exp, AbstractStatement a, AbstractStatement b, IfElseErrorHandler errorHandler) {
		this.exp = exp;
		this.a = a;
		this.b = b;
		this.errorHandler = errorHandler;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent, ExecutionContext c) throws RPLException {

		// If exp is contradiction/tautology we
		// can immediately pass the a/b iterator.
		try {
			if (exp.hasDefiniteValue()) {
				if (exp.getDefiniteValue(Type.BOOL)) {
					return a.getIterator(parent, c);
				} else {
					return b.getIterator(parent, c);
				}
			}
		} catch (RPLException e) {
			errorHandler.ifElseConditionError(e);
		}

		// Replace rank expressions in exp
		RankTransformIterator i = 
				new RankTransformIterator(parent, this, this.exp);
		AbstractExpression exp2 = i.getExpression(0);
		
		// Check contradiction/tautology again.
		try {
			if (exp2.hasDefiniteValue()) {
				if (exp2.getDefiniteValue(Type.BOOL)) {
					return a.getIterator(parent, c);
				} else {
					return b.getIterator(parent, c);
				}
			}
		} catch (RPLException e) {
			errorHandler.ifElseConditionError(e);
		}
		
		// Split input
		IteratorSplitter<VarStore> split = new IteratorSplitter<VarStore>(i);

		// Apply condition 
		IteratorWithOffSet<VarStore> ia1 = getConditioningIterator(exp2, split.getA(), c);
		IteratorWithOffSet<VarStore> ia2 = getConditioningIterator(new Not(exp2), split.getB(), c);
		
		// Remember offsets (prior ranks of the conditions)
		int offset1 = ia1.getConditioningOffset();
		int offset2 = ia2.getConditioningOffset();
		
		// Following happens if input iterator is empty
		if (offset1 == Rank.MAX && offset2 == Rank.MAX) {
			return new AbsurdIterator<VarStore>();
		}
		
		// Execute statements
		RankedIterator<VarStore> ib1 = a.getIterator(ia1, c);
		RankedIterator<VarStore> ib2 = b.getIterator(ia2, c);

		// Merge result
		if (offset1 > 0) {
			return new MergingIteratorFixed(ib2, ib1, offset1);
		} else {
			return new MergingIteratorFixed(ib1, ib2, offset2);
		}

	}

	public void ifElseConditionError(RPLException e) throws RPLException {
		e.setStatement(this);
		e.setExpression(exp);
		throw e;
	}
	
	public void ifElseThenError(RPLException e) throws RPLException {
		throw e;
	};
	
	public void ifElseElseError(RPLException e) throws RPLException {
		throw e;
	};
	
	@Override
	public void observeConditionError(RPLException e) throws RPLException {
		errorHandler.ifElseConditionError(e);
	}	

	public String toString() {
		String expString = exp.toString();
		if (!(expString.startsWith("(") && expString.endsWith(")"))) {
			expString = "(" + expString + ")";
		}
		return "if " + expString + " then " + a + " else " + b;
	}
	
	public boolean equals(Object o) {
		return o instanceof IfElse &&
				((IfElse)o).a.equals(a) &&
				((IfElse)o).b.equals(b) &&
				((IfElse)o).exp.equals(exp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b, exp);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new IfElse((AbstractExpression)exp.replaceVariable(a, b),
				(AbstractStatement)this.a.replaceVariable(a, b),
				(AbstractStatement)this.b.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		a.getVariables(list);
		b.getVariables(list);
		exp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		AbstractStatement ar = a.rewriteEmbeddedFunctionCalls();
		AbstractStatement br = b.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new IfElse(rewrittenExp.getExpression(), ar, br), rewrittenExp.getAssignments());
		} else {
			return new IfElse(exp, ar, br);
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		a.getAssignedVariables(variables);
		b.getAssignedVariables(variables);
	}

	private IteratorWithOffSet<VarStore> getConditioningIterator(final AbstractExpression exp2f, final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		final BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(in);
		boolean hasNext = bi.next();
		while (hasNext && !getCheckedValue(exp2f, bi)) { 
			hasNext = bi.next();
		}
		final int conditioningOffset = hasNext? bi.getRank(): Rank.MAX;
		if (hasNext) bi.reset(bi.getIndex() - 1);
		bi.stopBuffering();
		return new IteratorWithOffSet<VarStore>() {
			
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
				return Rank.sub(bi.getRank(),  conditioningOffset);
			}
			
			@Override
			public int getConditioningOffset() {
				return conditioningOffset;
			}
		};
	}

	private boolean getCheckedValue(AbstractExpression exp2, BufferingIterator<VarStore> bi) throws RPLException {
		try {
			return exp2.getValue(bi.getItem(), Type.BOOL);
		} catch (RPLException e) {
			observeConditionError(e);
			throw e;
		}
	}

	private static interface IteratorWithOffSet<T> extends RankedIterator<T> {
		public int getConditioningOffset();
	}

}
