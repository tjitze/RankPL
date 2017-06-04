package com.tr.rp.statement;

import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.IteratorSplitter;
import com.tr.rp.core.rankediterators.MergingIterator;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Not;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

public class IfElse extends DStatement {

	private Expression exp;
	private DStatement a, b;
	private DStatement exceptionSource;
	
	public IfElse(Expression exp, DStatement a, DStatement b) {
		this.exp = exp;
		this.a = a;
		this.b = b;
		this.exceptionSource = this;
	}

	public IfElse(Expression exp, DStatement a, DStatement b, DStatement exceptionSource) {
		this.exp = exp;
		this.a = a;
		this.b = b;
		this.exceptionSource = exceptionSource;
	}

	public IfElse(Expression exp, DStatement a) {
		this(exp, a, new Skip());
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent, ExecutionContext c) throws RPLException {
		Observe obs1;
		Observe obs2;

		// If exp is contradiction/tautology we
		// can immediately pass the a/b iterator.
		if (exp.hasDefiniteValue()) {
			if (exp.getDefiniteBoolValue()) {
				return a.getIterator(parent, c);
			} else {
				return b.getIterator(parent, c);
			}
		}
		
		// Replace rank expressions in exp
		RankTransformIterator i = 
				new RankTransformIterator(parent, this, this.exp);
		Expression exp2 = i.getExpression(0);
		
		// Check contradiction/tautology again.
		if (exp.hasDefiniteValue()) {
			if (exp.getDefiniteBoolValue()) {
				return a.getIterator(parent, c);
			} else {
				return b.getIterator(parent, c);
			}
		}
		
		// Split input
		IteratorSplitter<VarStore> split = new IteratorSplitter<VarStore>(i);

		// Apply condition 
		obs1 = new Observe(exp2);
		obs2 = new Observe(new Not(exp2));
		RankedIterator<VarStore> ia1;
		RankedIterator<VarStore> ia2;
		try {
			ia1 = obs1.getIterator(split.getA(), c);
			ia2 = obs2.getIterator(split.getB(), c);
		} catch (RPLException e) {
			if (e.getStatement() == obs1 || e.getStatement() == obs2) {
				e.setStatement(exceptionSource);
			}
			throw e;
		}
		
		// Remember offsets (prior ranks of the conditions)
		int offset1 = ia1.getConditioningOffset();
		int offset2 = ia2.getConditioningOffset();
		
		// Following happens if input iterator is empty
		if (offset1 == Integer.MAX_VALUE && offset2 == Integer.MAX_VALUE) {
			return new AbsurdIterator<VarStore>();
		}
		
		// Execute statements
		RankedIterator<VarStore> ib1 = a.getIterator(ia1, c);
		RankedIterator<VarStore> ib2 = b.getIterator(ia2, c);

		// Merge result
		RankedIterator<VarStore> rc = new MergingIterator(ib1, ib2, offset1, offset2);
		return rc;

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
	public LanguageElement replaceVariable(String a, String b) {
		return new IfElse((Expression)exp.replaceVariable(a, b),
				(DStatement)this.a.replaceVariable(a, b),
				(DStatement)this.b.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		a.getVariables(list);
		b.getVariables(list);
		exp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		DStatement ar = a.rewriteEmbeddedFunctionCalls();
		DStatement br = b.rewriteEmbeddedFunctionCalls();
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

}
