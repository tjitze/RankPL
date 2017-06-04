package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

public class FunctionCallForm extends AbstractStatement {

	private final List<Pair<String, AbstractFunctionCall>> assignments;
	private final AbstractStatement statement;

	@SafeVarargs
	public FunctionCallForm(AbstractStatement statement, List<Pair<String, AbstractFunctionCall>> ... assignments) {
		if (assignments.length == 1) {
			this.assignments = assignments[0];
		} else {
			this.assignments = new ArrayList<Pair<String, AbstractFunctionCall>>();
			for (List<Pair<String, AbstractFunctionCall>> a: assignments) {
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
		for (Pair<String, AbstractFunctionCall> assignment: assignments) {
			chainedIterator = assignment.b.getIterator(c, assignment.b.getArguments(), assignment.a, chainedIterator);
		}
		// Execute statement
		chainedIterator = statement.getIterator(chainedIterator, c);
		return chainedIterator;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		List<Pair<String, AbstractFunctionCall>> rewrittenAssingments = new ArrayList<Pair<String, AbstractFunctionCall>>();
		for (Pair<String, AbstractFunctionCall> assignment: assignments) {
			rewrittenAssingments.add(new Pair<String, AbstractFunctionCall>(assignment.a, (AbstractFunctionCall)assignment.b.replaceVariable(a, b)));
		}
		return new FunctionCallForm((AbstractStatement)statement.replaceVariable(a, b), rewrittenAssingments);
	}

	@Override
	public void getVariables(Set<String> list) {
		assignments.forEach(p -> p.b.getVariables(list));
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
		private List<Pair<String, AbstractFunctionCall>> assignments = new ArrayList<Pair<String, AbstractFunctionCall>>();
		private AbstractExpression expression;
		private boolean isRewritten = false;
		
		public ExtractedExpression(AbstractExpression expression) {
			this.expression = expression;
		};
		
		public AbstractExpression getExpression() {
			return expression;
		}

		public List<Pair<String, AbstractFunctionCall>> getAssignments() {
			return assignments;
		}

		private void rewrite(String var, AbstractFunctionCall fc) {
			expression = expression.replaceEmbeddedFunctionCall(fc, var);
			assignments.add(new Pair<String, AbstractFunctionCall>(var, fc));
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
			s = s.replace(assignments.get(i).a, assignments.get(i).b.toString());
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

	public boolean semanticEquals(FunctionCallForm fcf) {
		fcf = (FunctionCallForm)fcf.replaceVariable("", "");
		if (assignments.size() != fcf.assignments.size()) {
			return false;
		}
		AbstractStatement s = fcf.statement;
		outer: for (int i = 0; i < assignments.size(); i++) {
			Pair<String, AbstractFunctionCall> a = assignments.get(i);
			for (Pair<String, AbstractFunctionCall> b: fcf.assignments) {
				if (a.b.equals(b.b)) {
					s = (AbstractStatement)s.replaceVariable(b.a, a.a);
					for (int j = i + 1; j < assignments.size(); j++) {
						Pair<String, AbstractFunctionCall> c = fcf.assignments.get(j);
						fcf.assignments.set(j, new Pair<String, AbstractFunctionCall>(c.a, (AbstractFunctionCall)c.b.replaceVariable(b.a, a.a)));
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


}
