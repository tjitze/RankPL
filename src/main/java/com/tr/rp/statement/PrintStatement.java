package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.DuplicateRemovingIterator;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;

public class PrintStatement extends DStatement {
	
	private final Expression exp;
	
	public PrintStatement(Expression exp) {
		this.exp = exp;
	}
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		final RankedIterator<VarStore> in2 = new DuplicateRemovingIterator<VarStore>(in);
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				boolean next = in2.next();
				// Print here so that it's only done once, and in the right order
				if (next) {
					System.out.println(exp.getStringValue(in2.getItem()));
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
	public LanguageElement replaceVariable(String a, String b) {
		return new PrintStatement((Expression)exp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new PrintStatement((Expression)rewrittenExp.getExpression()), rewrittenExp.getAssignments());
		} else {
			return this;
		}
	}	
}
