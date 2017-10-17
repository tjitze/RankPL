package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class Observe extends AbstractStatement {

	private AbstractExpression exp;
	
	public Observe(AbstractExpression exp) {
		this.exp = exp;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AbstractExpression> transformExp = RankTransformer.create(exp);
		Executor exec = new Executor() {

			private int offset = -1;
			
			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				if (getCheckedValue(transformExp.get(), s.getVarStore())) {
					if (offset == -1) {
						offset = s.getRank();
					}
					out.push(s.shiftDown(offset));
				}
			}
		};
		return transformExp.getExecutor(exec, this);
	}		

	private boolean getCheckedValue(AbstractExpression exp2, VarStore v) throws RPLException {
		try {
			return exp2.getValue(v, Type.BOOL);
		} catch (RPLException e) {
			handleConditionException(e);
			return false;
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

	/**
     * Override to handle exceptions resulting from conditione valuation
	 */
	public void handleConditionException(RPLException e) throws RPLException {
		e.setStatement(this);
		throw e;
	}

}
