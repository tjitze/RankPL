package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

/**
 * The assign statement takes as input a variable, an (optional) list
 * of array indices, and an expression. 
 * 
 * If the list of array indices is empty, the effect of this statement
 * is that the given variable is assigned the value of the given 
 * expression. If the list of array indices is nonempty, the effect is
 * that the array associated with the variable name (indexed by the 
 * array indices) is assigned the value of the given expression.
 */
public class Assign extends AbstractStatement {
	
	private final AbstractExpression value;
	private final AssignmentTarget variable;
	
	public Assign(AssignmentTarget var, AbstractExpression exp) {
		this.variable = var;
		this.value = exp;
	}

	public Assign(AssignmentTarget var, int value) {
		this(var, new Literal<Integer>(value));
	}

	public Assign(String variableName, int value) {
		this(new AssignmentTarget(variableName), new Literal<Integer>(value));
	}

	public Assign(String variableName, AbstractExpression value) {
		this(new AssignmentTarget(variableName), value);
	}

	public Assign(String variableName, String otherVariable) {
		this(new AssignmentTarget(variableName), new Variable(otherVariable));
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		try {
			RankTransformIterator rt = 
				new RankTransformIterator(in, this, this.value);
			final AbstractExpression exp2 = rt.getExpression(0);
			RankedIterator<VarStore> ai = new RankedIterator<VarStore>() {
	
				@Override
				public boolean next() throws RPLException {
					return rt.next();
				}
	
				@Override
				public VarStore getItem() throws RPLException {
					if (rt.getItem() == null) return null;
					try {
						return variable.assign(rt.getItem(), exp2.getValue(rt.getItem()));
					} catch (RPLException e) {
						e.setStatement(Assign.this);
						throw e;
					}
				}
	
				@Override
				public int getRank() {
					return rt.getRank();
				}
			};
			return ai;
		} catch (RPLException e) {
			e.setStatement(this);
			throw e;
		}
	}
	
	
	public String toString() {
		String varString = variable.toString();
		String expString = value.toString();
		if (expString.startsWith("(") && expString.endsWith(")")) {
			expString = expString.substring(1, expString.length()-1);
		}
		return varString + " := " + expString;
	}
	
	public boolean equals(Object o) {
		return o instanceof Assign &&
				((Assign)o).variable.equals(variable) &&
				((Assign)o).value.equals(value);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Assign((AssignmentTarget)variable.replaceVariable(a, b), (AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		variable.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenVar = FunctionCallForm.extractFunctionCalls(variable);
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenVar.isRewritten() || rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new Assign((AssignmentTarget)rewrittenVar.getExpression(), 
							rewrittenValue.getExpression()), 
						rewrittenVar.getAssignments(),
						rewrittenValue.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(variable.getAssignedVariable());
	}	
}
