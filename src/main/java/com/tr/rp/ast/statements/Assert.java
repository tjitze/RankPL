package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLAssertionException;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.types.Type;

/**
 * The "assert b" statement is intended for testing purposes. Aborts if b is false. 
 */
public class Assert extends AbstractStatement {

	private AbstractExpression expression;
	
	public Assert(AbstractExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AbstractExpression> transformExp = RankTransformer.create(expression);
		Executor exec = new Executor() {

			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				boolean b;
				try {
					b = transformExp.get().getValue(s.getVarStore(), Type.BOOL);
				} catch (RPLException e) {
					e.setStatement(Assert.this);
					throw e;
				}
				if (!b) {
					throw new RPLAssertionException("Failed assertion: " + transformExp.get(), Assert.this);
				}
				out.push(s);
			}
			
		};
		return transformExp.getExecutor(exec, this);
	}

	@Override
	public void getVariables(Set<String> list) {
		expression.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Assert((AbstractExpression)expression.replaceVariable(a, b));
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExpression = FunctionCallForm.extractFunctionCalls(expression);
		if (rewrittenExpression.isRewritten()) {
			Assert ass = new Assert(rewrittenExpression.getExpression());
			ass.setLineNumber(getLineNumber());
			return new FunctionCallForm(ass, rewrittenExpression.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}
	
	public String toString() {
		return "assert("+expression+")";
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Assert) &&
				((Assert)o).expression.equals(expression);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(expression);
	}	

}
