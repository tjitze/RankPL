package com.tr.rp.ast.statements;

import java.util.Set;
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
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class While extends AbstractStatement implements IfElseErrorHandler {

	/** Optional statement to execute before evaluating while condition */
	private AbstractStatement preStatement;

	/** While condition */
	private AbstractExpression whileCondition;
	
	/** While statement body */
	private AbstractStatement body;

	private static final int SKIP_COUNT = 5;
	
	public While(AbstractExpression whileCondition, AbstractStatement body) {
		this.whileCondition = whileCondition;
		this.body = body;
		this.preStatement = null;
	}
	
	private While(AbstractExpression whileCondition, AbstractStatement body, AbstractStatement preStatement) {
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
		if (preStatement == null) {
			return ifElse.getIterator(in, c);
		} else {
			return new Composition(preStatement, ifElse).getIterator(in, c);
		}
	}
	
	public boolean equals(Object o) {
		return o instanceof While &&
				((While)o).whileCondition.equals(whileCondition) &&
				((While)o).body.equals(body);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new While((AbstractExpression)whileCondition.replaceVariable(a, b),
				(AbstractStatement)this.body.replaceVariable(a, b));
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
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		if (preStatement != null) {
			throw new UnsupportedOperationException();
		}
		AbstractStatement sr = body.rewriteEmbeddedFunctionCalls();
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

	@Override
	public int hashCode() {
		return Objects.hash(preStatement, whileCondition, body);
	}

	@Override
	public void ifElseConditionError(RPLException e) throws RPLException {
		e.setStatement(this);
		e.setExpression(whileCondition);
		throw e;
	}

	@Override
	public void ifElseThenError(RPLException e) throws RPLException {
		throw e;
	}

	@Override
	public void ifElseElseError(RPLException e) throws RPLException {
		throw e;
	}	

}
