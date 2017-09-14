package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.RankTransformer;
import com.tr.rp.exec.State;

public class PrintStatement extends AbstractStatement {
	
	private final AbstractExpression exp;
	
	public PrintStatement(AbstractExpression exp) {
		this.exp = exp;
	}
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AbstractExpression> transformExp = RankTransformer.create(exp);
		Executor exec = new Executor() {
			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				System.out.println(transformExp.get().getValue(s.getVarStore()));
				out.push(s);
			}
		};
		transformExp.setOutput(exec, this);
		return transformExp;
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
