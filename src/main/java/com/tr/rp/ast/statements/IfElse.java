package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.BranchingExecutor;
import com.tr.rp.exec.Deduplicator;
import com.tr.rp.exec.EvaluationErrorHandler;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.RankTransformer;

public class IfElse extends AbstractStatement implements IfElseErrorHandler, ObserveErrorHandler, EvaluationErrorHandler {

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
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AbstractExpression> transformExp = RankTransformer.create(exp);
		Executor e = new BranchingExecutor(transformExp, a, b, new Deduplicator(out), c);
		transformExp.setOutput(e, this);
		return transformExp;
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

	@Override
	public void handleEvaluationError(RPLException e) throws RPLException {
		e.setStatement(this);
		throw e;
	}


}
