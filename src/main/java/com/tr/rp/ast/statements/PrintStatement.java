package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class PrintStatement extends AbstractStatement {
	
	private final AbstractExpression exp;
	
	public PrintStatement(AbstractExpression exp) {
		this.exp = exp;
	}
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, exp);
		final AbstractExpression exp = rt.getExpression(0);
		final RankedIterator<VarStore> in2 = new DuplicateRemovingIterator<VarStore>(rt);
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				boolean next = in2.next();
				// Print here so that it's only done once, and in the right order
				if (next) {
					System.out.println(exp.getValue(in2.getItem()));
				}
				return next;
			}

			@Override
			public VarStore getItem() throws RPLException {
				return in2.getItem();
			}

			@Override
			public int getRank() {
				return in2.getRank();
			}
			
		};
	}
	
	
	public String toString() {
		return "print("+exp+")";
	}
	
	public boolean equals(Object o) {
		return o instanceof PrintStatement && ((PrintStatement)o).exp.equals(exp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(exp);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new PrintStatement((AbstractExpression)exp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new PrintStatement((AbstractExpression)rewrittenExp.getExpression()), rewrittenExp.getAssignments());
		} else {
			return this;
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}	

}
