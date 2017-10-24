package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.Guard;
import com.tr.rp.varstore.FreeVarNameProvider;

public class FunctionCallForm extends AbstractStatement {

	private final List<Assignment> assignments;
	private final AbstractStatement statement;

	@SafeVarargs
	public FunctionCallForm(AbstractStatement statement, List<Assignment> ... assignments) {
		if (assignments.length == 1) {
			this.assignments = assignments[0];
		} else {
			this.assignments = new ArrayList<Assignment>();
			for (List<Assignment> a: assignments) {
				this.assignments.addAll(a);
			}
		}
		this.statement = statement;
		setLineNumber(statement.getLineNumber());
		checkStatement();
	}

	private void checkStatement() {
		if (statement instanceof FunctionCallForm) {
			throw new RuntimeException("Directly nested function call form should not happen");
		}
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		out = Guard.checkIfEnabled(out);
		out = statement.getExecutor(out, c);
		for (int i = assignments.size() - 1; i >= 0; i--) {
			Assignment assignment = assignments.get(i);
			out = assignment.functionCall.getExecutor(c, assignment.var, out, this);
		}
		final Executor out2 = out;
		return new Executor() {

			@Override
			public void close() throws RPLException {
				try {
					out2.close();
				} catch (RPLException e) {
					if (e.getStatement() == statement) {
						e.setStatement(FunctionCallForm.this);
					}
					// TODO: fix this (we set it to null because exp may contain subst variables)
					if (e.getExpression().toString().contains("$")) {
						e.setExpression(null);
					}
					throw e;
				}
			}

			@Override
			public void push(State s) throws RPLException {
				try {
					out2.push(s);
				} catch (RPLException e) {
					if (e.getStatement() == statement) {
						e.setStatement(FunctionCallForm.this);
					}
					// TODO: fix this (we set it to null because exp may contain subst variables)
					if (e.getExpression().toString().contains("$")) {
						e.setExpression(null);
					}
					throw e;
				}
			}
			
		};
	}

	@Override
	public void getVariables(Set<String> list) {
		assignments.forEach(p -> p.functionCall.getVariables(list));
		statement.getVariables(list);
	}
	
	/**
	 * Utility function to transform an expression that contains function calls into an 
	 * expression where each function call is replaced by a unique placeholder variable.
	 * 
	 * @param exp
	 * @return
	 */
	public static ExtractedExpression extractFunctionCalls(AbstractExpression exp) {
		ExtractedExpression ee = new ExtractedExpression(exp);
		AbstractFunctionCall fc = exp.getEmbeddedFunctionCall();
		while (fc != null) {
			String var = FreeVarNameProvider.getFreeVariable("subst");
			ee.rewrite(var, fc);
			fc = ee.getExpression().getEmbeddedFunctionCall();
		}
		return ee;
	}

	/**
	 * An ExtractedExpression captures a rewritten expression, in which all function calls
	 * are replaced with unique placeholder variables, and a sequence of assignments (i.e.,
	 * the placeholder variables with the associated function calls.)
	 */
	public static final class ExtractedExpression {
		private List<Assignment> assignments = new ArrayList<Assignment>();
		private AbstractExpression expression;
		private boolean isRewritten = false;
		
		public ExtractedExpression(AbstractExpression expression) {
			this.expression = expression;
		};
		
		public AbstractExpression getExpression() {
			return expression;
		}

		public List<Assignment> getAssignments() {
			return assignments;
		}

		private void rewrite(String var, AbstractFunctionCall fc) {
			expression = expression.replaceEmbeddedFunctionCall(fc, var);
			assignments.add(new Assignment(var, fc));
			isRewritten = true;
		}
		
		public boolean isRewritten() {
			return isRewritten;
		}
	}
	
	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}
	
	public String transformStatement(String s) {
		for (int i = assignments.size()-1; i >= 0; i--) {
			s = s.replace(assignments.get(i).var, assignments.get(i).functionCall.toString());
		}
		return s;
	}
	
	public String toString() {
		return transformStatement(statement.toString());
	}

	public boolean equals(Object o) {
		return o instanceof FunctionCallForm &&
				((FunctionCallForm)o).assignments.equals(assignments) &&
				((FunctionCallForm)o).statement.equals(statement);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assignments, statement);
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		statement.getAssignedVariables(variables);
	}	

	public static class Assignment {
		public final String var;
		public final AbstractFunctionCall functionCall;
		public Assignment(String var, AbstractFunctionCall functionCall) {
			this.var = var;
			this.functionCall = functionCall;
		}
	}
}
