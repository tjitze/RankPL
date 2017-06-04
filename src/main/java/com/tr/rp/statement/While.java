package com.tr.rp.statement;

import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.BufferingIterator;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

public class While extends DStatement {

	/** Optional statement to execute before evaluating while condition */
	private DStatement preStatement;

	/** While condition */
	private Expression whileCondition;
	
	/** While statement body */
	private DStatement body;

	private static final int SKIP_COUNT = 5;
	
	public While(Expression whileCondition, DStatement body) {
		this.whileCondition = whileCondition;
		this.body = body;
		this.preStatement = null;
	}
	
	private While(Expression whileCondition, DStatement body, DStatement preStatement) {
		this.whileCondition = whileCondition;
		this.body = body;
		this.preStatement = preStatement;
	}
			
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		while (true) {

			// Do one iteration
			in = generateIteration(in, c);
			
			// Check if exp is still satisfied
			BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(in);
			boolean expSatisfied = false;
			boolean hasNext = bi.next();
			if (!hasNext) { // Undefined
				return new AbsurdIterator<VarStore>(); 
			}
			while (hasNext) {
				if (whileCondition.getBoolValue(bi.getItem())) {
					expSatisfied = true;
					break;
				}
				hasNext = bi.next();
			}
			
			bi.reset();
			bi.stopBuffering();

			// If exp is not satisfied, we are done
			if (!expSatisfied) {
				return bi;
			}
			
			// Try another iteration
			in = bi;
		}
		
	}
	
	private RankedIterator<VarStore> generateIteration(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		IfElse ifElse = new IfElse(whileCondition, body, new Skip(), this);
		try {
			if (preStatement == null) {
				return ifElse.getIterator(in, c);
			} else {
				return new Composition(preStatement, ifElse).getIterator(in, c);
			}
		} catch (RPLException e) {
			if (e.getStatement() == ifElse || e.getStatement() == preStatement) {
				e.setStatement(this);
			}
			throw e;
		}
	}
	
	public boolean equals(Object o) {
		return o instanceof While &&
				((While)o).whileCondition.equals(whileCondition) &&
				((While)o).body.equals(body);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new While((Expression)whileCondition.replaceVariable(a, b),
				(DStatement)this.body.replaceVariable(a, b));
	}
	
	public String toString() {
		String expString = whileCondition.toString();
		if (preStatement != null && preStatement instanceof FunctionCallForm) {
			
		}
		if (!(expString.startsWith("(") && expString.endsWith(")"))) {
			expString = "(" + expString + ")";
		}
		String ss = body.toString();
		if (preStatement != null && preStatement instanceof FunctionCallForm) {
			expString = ((FunctionCallForm)preStatement).transformStatement(expString);
			ss = ((FunctionCallForm)preStatement).transformStatement(ss);
		}
		return "while " + expString + " do " + ss;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		body.getVariables(list);
		whileCondition.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		if (preStatement != null) {
			throw new UnsupportedOperationException();
		}
		DStatement sr = body.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(whileCondition);
		if (rewrittenExp.isRewritten()) {
			return new While(rewrittenExp.getExpression(), sr, new FunctionCallForm(new Skip(), rewrittenExp.getAssignments()));
		} else {
			return new While(whileCondition, sr);
		}
	}	

	@Override
	public void getAssignedVariables(Set<String> variables) {
		body.getAssignedVariables(variables);
	}	

}
