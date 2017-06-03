package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.expressions.PersistentList;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.expressions.AbstractFunctionCall;
import com.tr.rp.expressions.AssignmentTarget;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Variable;
import com.tr.rp.expressions.Literal;
import com.tr.rp.tools.Pair;

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
public class Assign extends DStatement {
	
	private final Expression value;
	private final AssignmentTarget variable;
	
	public Assign(AssignmentTarget var, Expression exp) {
		this.variable = var;
		this.value = exp;
	}

	public Assign(AssignmentTarget var, int value) {
		this(var, new Literal<Integer>(value));
	}

	public Assign(String variableName, int value) {
		this(new AssignmentTarget(variableName), new Literal<Integer>(value));
	}

	public Assign(String variableName, Expression value) {
		this(new AssignmentTarget(variableName), value);
	}

	public Assign(String variableName, String otherVariable) {
		this(new AssignmentTarget(variableName), new Variable(otherVariable));
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		try {
			RankTransformIterator rt = 
				new RankTransformIterator(in, this.value);
			final Expression exp2 = rt.getExpression(0);
			return new RankedIterator<VarStore>() {
	
				@Override
				public boolean next() throws RPLException {
					try {
						return rt.next();
					} catch (RPLException e) {
						e.addStatement(Assign.this);
						throw e;
					}
				}
	
				@Override
				public VarStore getItem() throws RPLException {
					try {
						if (rt.getItem() == null) return null;
						return variable.assign(rt.getItem(), exp2.getValue(rt.getItem()));
					} catch (RPLException e) {
						e.addStatement(Assign.this);
						throw e;
					}
				}
	
				@Override
				public int getRank() {
					return rt.getRank();
				}
			};
		} catch (RPLException e) {
			e.addStatement(this);
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
		return new Assign((AssignmentTarget)variable.replaceVariable(a, b), (Expression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		variable.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
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
}
