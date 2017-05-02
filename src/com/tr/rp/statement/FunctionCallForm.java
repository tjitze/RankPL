package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.FunctionCall;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.tools.Pair;

public class FunctionCallForm implements DStatement {

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
	
	private List<DStatement> getRewrittenFunctions() {
		return assignments.stream()
			.map(p -> getRewrittenFunction(p.a, p.b))
			.collect(Collectors.toList());
	}
	
	private DStatement getRewrittenFunction(String var, FunctionCall fc) {

		// Get sorted list of variables used in function body
		DStatement rewrittenFunctionBody = fc.getFunction().getBody();
		List<String> rewrittenParameters = new ArrayList<String>();
		for (String param: fc.getFunction().getParameters()) {
			rewrittenParameters.add(param);
		}
		Set<String> varSet = new LinkedHashSet<String>();
		rewrittenFunctionBody.getVariables(varSet);
		rewrittenParameters.forEach(p -> varSet.add(p));
		List<String> variables = new ArrayList<String>();
		variables.addAll(varSet);
		Collections.sort(variables);
	
		// Rewrite parameter list, body, and return expression
		NumExpression returnValue = fc.getFunction().getReturnValue();
		for (String v: variables) {
			String rv = VarStore.getFreeVariable(v);
			rewrittenFunctionBody = (DStatement) rewrittenFunctionBody.replaceVariable(v, rv);
			returnValue = (NumExpression) returnValue.replaceVariable(v, rv);
			for (int i = 0; i < rewrittenParameters.size(); i++) {
				if (rewrittenParameters.get(i).equals(v)) {
					rewrittenParameters.set(i, rv);
				}
			}
		}
		
		// Add setters for parameters to function body
		for (int i = 0; i < rewrittenParameters.size(); i++) {
			rewrittenFunctionBody = new Composition(
					new Assign(rewrittenParameters.get(i), fc.getArguments()[i]),
					rewrittenFunctionBody);
		}
		
		// Add setter for return value to function body
		rewrittenFunctionBody = new Composition(rewrittenFunctionBody, new Assign(var, returnValue));
		return rewrittenFunctionBody;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) {
		List<DStatement> functions = getRewrittenFunctions();
		for (DStatement f: functions) {
			parent = f.getIterator(parent);
		}
		return statement.getIterator(parent);
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
	
	public static <T extends Expression<T>> Pair<List<Pair<String, FunctionCall>>, T> extractFunctionCalls(T exp) {
		List<Pair<String, FunctionCall>> assignments = new ArrayList<Pair<String, FunctionCall>>();
		FunctionCall fc = exp.getEmbeddedFunctionCall();
		while (fc != null) {
			String var = VarStore.getFreeVariable(fc.getFunctionName());
			exp = exp.replaceEmbeddedFunctionCall(fc, var);
			assignments.add(new Pair<String, FunctionCall>(var, fc));
			fc = exp.getEmbeddedFunctionCall();
		}
		return new Pair<List<Pair<String, FunctionCall>>, T>(assignments, exp);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}
	
	public String toString() {
		String s = statement.toString();
		for (int i = assignments.size()-1; i >= 0; i--) {
			s = s.replace(assignments.get(i).a, assignments.get(i).b.toString());
		}
		return s;
	}
	
}
