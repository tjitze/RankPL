package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.AbstractFunctionCall;
import com.tr.rp.expressions.AssignmentTarget;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.Variable;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

/**
 * The RangeChoice statement takes as input an assignment target, a begin 
 * expression and end expression. It represents an indifferent choice (all 
 * ranked 0) between all values from begin to end (exclusive). Note that 
 * the begin and the end expressions may evaluate to different values in 
 * different alternatives. 
 */
public class RangeChoice extends DStatement {

	public final AssignmentTarget variable;
	public final Expression beginExp;
	public final Expression endExp;

	public RangeChoice(AssignmentTarget variable, Expression beginExp, Expression endExp) {
		this.variable = variable;
		this.beginExp = beginExp;
		this.endExp = endExp;
	}

	public RangeChoice(String variableName, Expression beginExp, Expression endExp) {
		this.variable = new AssignmentTarget(variableName);
		this.beginExp = beginExp;
		this.endExp = endExp;
	}

	public RangeChoice(String variableName, int begin, int end) {
		this.variable = new AssignmentTarget(variableName);
		this.beginExp = new Literal<Integer>(begin);
		this.endExp = new Literal<Integer>(end);
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) throws RPLException {
		try {
			RankTransformIterator rt = new RankTransformIterator(in, beginExp, endExp);
			final Expression begin = rt.getExpression(0);
			final Expression end = rt.getExpression(1);
			return new RankedIterator<VarStore>() {
				private boolean initialized = false;
				private boolean finalized = false;
				private int a = 0;
				private int b = 0;
				private int i = 0;
				
				@Override
				public boolean next() throws RPLException {
					try {
						if (finalized) {
							return false;
						}
						if (!initialized) {
							initialized = true;
							if (in.next()) {
								a = begin.getIntValue(in.getItem());
								b = end.getIntValue(in.getItem());
								i = a;
								return true;
							} else {
								finalized = true;
								return false;
							}
						} else {
							i++;
							if (i >= b) {
								if (in.next()) {
									a = begin.getIntValue(in.getItem());
									b = end.getIntValue(in.getItem());
									i = a;
									return true;
								} else {
									finalized = true;
									return false;
								}
							}
							return true;
						}
					} catch (RPLException e) {
						e.addStatement(RangeChoice.this);
						throw e;
					}
				}
	
				@Override
				public VarStore getItem() throws RPLException {
					try {
						VarStore vs = in.getItem();
						if (vs == null) {
							return null;
						} else {
							return variable.assign(vs, i);
						}
					} catch (RPLException e) {
						e.addStatement(RangeChoice.this);
						throw e;
					}
				}
	
				@Override
				public int getRank() {
					return in.getRank();
				}
			};
		} catch (RPLException e) {
			e.addStatement(this);
			throw e;
		}
	}
	
	public String toString() {
		return variable + " := <" + beginExp + " ... " + endExp + ">";
	}
	
	public boolean equals(Object o) {
		return o instanceof RangeChoice &&
				((RangeChoice)o).beginExp.equals(beginExp) &&
				((RangeChoice)o).endExp.equals(endExp) &&
				((RangeChoice)o).variable.equals(variable);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new RangeChoice((AssignmentTarget)variable.replaceVariable(a, b), 
				(Expression)beginExp.replaceVariable(a, b),
				(Expression)endExp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		variable.getVariables(list);
		beginExp.getVariables(list);
		endExp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenVar = FunctionCallForm.extractFunctionCalls(variable);
		ExtractedExpression rewrittenBegin = FunctionCallForm.extractFunctionCalls(beginExp);
		ExtractedExpression rewrittenEnd = FunctionCallForm.extractFunctionCalls(endExp);
		if (rewrittenVar.isRewritten() || rewrittenBegin.isRewritten() || rewrittenEnd.isRewritten()) {
			return new FunctionCallForm(
					new RangeChoice(
							(AssignmentTarget)rewrittenVar.getExpression(), 
							rewrittenBegin.getExpression(), 
							rewrittenEnd.getExpression()), 
					rewrittenVar.getAssignments(),
					rewrittenBegin.getAssignments(),
					rewrittenEnd.getAssignments());
		} else {
			return this;
		}
	}	
}
