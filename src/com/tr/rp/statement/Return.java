package com.tr.rp.statement;

import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Variable;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

/**
 * The return statement makes a function or program exit and return
 * the value of a given expression.
 * 
 * Internally it is implemented by assigning a value to a special 
 * $return variable. The Composition construct skips execution of
 * the remainder for any state in which this variable is set.
 */
public class Return extends DStatement {

	private final Expression exp;
	
	public Return(Expression exp) {
		this.exp = exp;
	}

	public Return(String variable) {
		this.exp = new Variable(variable);
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) throws RPLException {
		Assign assign = new Assign("$return", exp);
		return assign.getIterator(in);
	}
		
	public String toString() {
		return "return " + exp;
	}
	
	public boolean equals(Object o) {
		return o instanceof Return &&
				((Return)o).exp.equals(exp);
	}

	@Override
	public boolean containsVariable(String var) {
		return exp.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Return((Expression)exp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new Return(rewrittenExp.getExpression()), rewrittenExp.getAssignments());
		} else {
			return this;
		}
	}	
}
