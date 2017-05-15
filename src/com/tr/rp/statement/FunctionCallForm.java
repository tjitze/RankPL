package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.exceptions.RPLMissingReturnValueException;
import com.tr.rp.exceptions.RPLWrongNumberOfArgumentsException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Variable;
import com.tr.rp.tools.Pair;

public class FunctionCallForm extends DStatement {

	private final List<Pair<String, FunctionCall>> assignments;
	private final DStatement statement;

	public FunctionCallForm(DStatement statement, List<Pair<String, FunctionCall>> assignments) {
		this.assignments = assignments;
		this.statement = statement;
	}

	public FunctionCallForm(DStatement statement, String var, FunctionCall fc) {
		assignments = new ArrayList<Pair<String, FunctionCall>>();
		assignments.add(new Pair<String, FunctionCall>(var, fc));
		this.statement = statement;
		checkStatement();
	}

	public FunctionCallForm(DStatement statement, String var1, FunctionCall fc1, String var2, FunctionCall fc2) {
		assignments = new ArrayList<Pair<String, FunctionCall>>();
		assignments.add(new Pair<String, FunctionCall>(var1, fc1));
		assignments.add(new Pair<String, FunctionCall>(var2, fc2));
		this.statement = statement;
		checkStatement();
	}

	public FunctionCallForm(DStatement statement, String var1, FunctionCall fc1, String var2, FunctionCall fc2, String var3, FunctionCall fc3) {
		assignments = new ArrayList<Pair<String, FunctionCall>>();
		assignments.add(new Pair<String, FunctionCall>(var1, fc1));
		assignments.add(new Pair<String, FunctionCall>(var2, fc2));
		assignments.add(new Pair<String, FunctionCall>(var3, fc3));
		this.statement = statement;
		checkStatement();
	}

	private void checkStatement() {
		if (statement instanceof FunctionCallForm) {
			throw new RuntimeException("Directly nested function call form should not happen");
		}
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) throws RPLException {
		RankedIterator<VarStore> chainedIterator = parent;
		// For each function assignment ...
		for (Pair<String, FunctionCall> assignment: assignments) {
			// Create closure with parameters assigned to arguments
			final RankedIterator<VarStore> pre = chainedIterator;
			chainedIterator = new RankedIterator<VarStore>() {
				@Override
				public boolean next() throws RPLException {
					return pre.next();
				}
	
				@Override
				public VarStore getItem() throws RPLException {
					String[] parameters = assignment.b.getFunction().getParameters();
					Expression[] arguments = assignment.b.getArguments();
					if (parameters.length != arguments.length) {
						throw new RPLWrongNumberOfArgumentsException(assignment.b.getFunction().getName(), parameters.length, arguments.length);
					}
					return pre.getItem().createClosure(assignment.b.getFunction().getParameters(), assignment.b.getArguments());
				}
	
				@Override
				public int getRank() {
					return pre.getRank();
				}
			};
			// Execute body in closure
			chainedIterator = assignment.b.getFunction().getBody().getIterator(chainedIterator);
			// Get parent closure with variable assigned to return value
			final RankedIterator<VarStore> post = chainedIterator;
			chainedIterator = new RankedIterator<VarStore>() {
				@Override
				public boolean next() throws RPLException {
					return post.next();
				}
	
				@Override
				public VarStore getItem() throws RPLException {
					VarStore v = post.getItem();
					if (!v.containsVar("$return")) {
						throw new RPLMissingReturnValueException(assignment.b.getFunction());
					}
					return v.getParentOfClosure(assignment.a, new Variable("$return"));
				}
	
				@Override
				public int getRank() {
					return post.getRank();
				}
			};
		}
		// Execute statement
		chainedIterator = statement.getIterator(chainedIterator);
		return chainedIterator;
	}

	@Override
	public boolean containsVariable(String var) {
		for (Pair<String, FunctionCall> assignment: assignments) {
			if (assignment.b.containsVariable(var)) {
				return true;
			}
		}
		return statement.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		List<Pair<String, FunctionCall>> rewrittenAssingments = new ArrayList<Pair<String, FunctionCall>>();
		for (Pair<String, FunctionCall> assignment: assignments) {
			rewrittenAssingments.add(new Pair<String, FunctionCall>(assignment.a, (FunctionCall)assignment.b.replaceVariable(a, b)));
		}
		return new FunctionCallForm((DStatement)statement.replaceVariable(a, b), rewrittenAssingments);
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
	public static ExtractedExpression extractFunctionCalls(Expression exp) {
		ExtractedExpression ee = new ExtractedExpression(exp);
		FunctionCall fc = exp.getEmbeddedFunctionCall();
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
		private List<Pair<String, FunctionCall>> assignments = new ArrayList<Pair<String, FunctionCall>>();
		private Expression expression;
		private boolean isRewritten = false;
		
		public ExtractedExpression(Expression expression) {
			this.expression = expression;
		};
		
		public Expression getExpression() {
			return expression;
		}

		public List<Pair<String, FunctionCall>> getAssignments() {
			return assignments;
		}

		private void rewrite(String var, FunctionCall fc) {
			expression = expression.replaceEmbeddedFunctionCall(fc, var);
			assignments.add(new Pair<String, FunctionCall>(var, fc));
			isRewritten = true;
		}
		
		public boolean isRewritten() {
			return isRewritten;
		}
}
	
	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
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
		DStatement s = fcf.statement;
		outer: for (int i = 0; i < assignments.size(); i++) {
			Pair<String, FunctionCall> a = assignments.get(i);
			for (Pair<String, FunctionCall> b: fcf.assignments) {
				if (a.b.equals(b.b)) {
					s = (DStatement)s.replaceVariable(b.a, a.a);
					for (int j = i + 1; j < assignments.size(); j++) {
						Pair<String, FunctionCall> c = fcf.assignments.get(j);
						fcf.assignments.set(j, new Pair<String, FunctionCall>(c.a, (FunctionCall)c.b.replaceVariable(b.a, a.a)));
					}
					continue outer;
				}
			}
			return false;
		}
		return statement.equals(s);
	}

}
