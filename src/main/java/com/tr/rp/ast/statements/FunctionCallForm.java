package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

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
		checkStatement();
	}

	private void checkStatement() {
		if (statement instanceof FunctionCallForm) {
			throw new RuntimeException("Directly nested function call form should not happen");
		}
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent, ExecutionContext c) throws RPLException {
		RankedIterator<VarStore> chainedIterator = parent;
		// For each function assignment ...
		for (Assignment assignment: assignments) {
			chainedIterator = assignment.functionCall.getIterator(c, assignment.functionCall.getArguments(), assignment.var, chainedIterator);
		}
		// Execute statement
		chainedIterator = statement.getIterator(chainedIterator, c);
		return chainedIterator;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		List<Assignment> rewrittenAssingments = new ArrayList<Assignment>();
		for (Assignment assignment: assignments) {
			rewrittenAssingments.add(new Assignment(assignment.var, (AbstractFunctionCall)assignment.functionCall.replaceVariable(a, b)));
		}
		return new FunctionCallForm((AbstractStatement)statement.replaceVariable(a, b), rewrittenAssingments);
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
			String var = VarStore.getFreeVariable(fc.getFunctionName());
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

	public boolean semanticEquals(FunctionCallForm fcf) {
		fcf = (FunctionCallForm)fcf.replaceVariable("", "");
		if (assignments.size() != fcf.assignments.size()) {
			return false;
		}
		AbstractStatement s = fcf.statement;
		outer: for (int i = 0; i < assignments.size(); i++) {
			Assignment a = assignments.get(i);
			for (Assignment b: fcf.assignments) {
				if (a.functionCall.equals(b.functionCall)) {
					s = (AbstractStatement)s.replaceVariable(b.var, a.var);
					for (int j = i + 1; j < assignments.size(); j++) {
						Assignment c = fcf.assignments.get(j);
						fcf.assignments.set(j, new Assignment(c.var, (AbstractFunctionCall)c.functionCall.replaceVariable(b.var, a.var)));
					}
					continue outer;
				}
			}
			return false;
		}
		return statement.equals(s);
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
