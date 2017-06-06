package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

/**
 * The RangeChoice statement takes as input an assignment target, a begin 
 * expression and end expression. It represents an indifferent choice (all 
 * ranked 0) between all values from begin to end (exclusive). Note that 
 * the begin and the end expressions may evaluate to different values in 
 * different alternatives. 
 */
public class RangeChoice extends AbstractStatement {

	public final AssignmentTarget target;
	public final AbstractExpression beginExp;
	public final AbstractExpression endExp;

	public RangeChoice(AssignmentTarget target, AbstractExpression beginExp, AbstractExpression endExp) {
		this.target = target;
		this.beginExp = beginExp;
		this.endExp = endExp;
	}

	public RangeChoice(AssignmentTarget target, int begin, int end) {
		this.target = target;
		this.beginExp = new Literal<Integer>(begin);
		this.endExp = new Literal<Integer>(end);
	}

	public RangeChoice(String targetVariable, AbstractExpression beginExp, AbstractExpression endExp) {
		this.target = new AssignmentTarget(targetVariable);
		this.beginExp = beginExp;
		this.endExp = endExp;
	}

	public RangeChoice(String targetVariable, int begin, int end) {
		this.target = new AssignmentTarget(targetVariable);
		this.beginExp = new Literal<Integer>(begin);
		this.endExp = new Literal<Integer>(end);
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		try {
			RankTransformIterator rt = new RankTransformIterator(in, this, target, beginExp, endExp);
			final AssignmentTarget target = (AssignmentTarget)rt.getExpression(0);
			final AbstractExpression begin = rt.getExpression(1);
			final AbstractExpression end = rt.getExpression(2);
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
						e.setStatement(RangeChoice.this);
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
							return target.assign(vs, i);
						}
					} catch (RPLException e) {
						e.setStatement(RangeChoice.this);
						throw e;
					}
				}
	
				@Override
				public int getRank() {
					return in.getRank();
				}
			};
		} catch (RPLException e) {
			e.setStatement(this);
			throw e;
		}
	}
	
	public String toString() {
		return target + " := <" + beginExp + " ... " + endExp + ">";
	}
	
	public boolean equals(Object o) {
		return o instanceof RangeChoice &&
				((RangeChoice)o).beginExp.equals(beginExp) &&
				((RangeChoice)o).endExp.equals(endExp) &&
				((RangeChoice)o).target.equals(target);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new RangeChoice((AssignmentTarget)target.replaceVariable(a, b), 
				(AbstractExpression)beginExp.replaceVariable(a, b),
				(AbstractExpression)endExp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		target.getVariables(list);
		beginExp.getVariables(list);
		endExp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		ExtractedExpression rewrittenBegin = FunctionCallForm.extractFunctionCalls(beginExp);
		ExtractedExpression rewrittenEnd = FunctionCallForm.extractFunctionCalls(endExp);
		if (rewrittenTarget.isRewritten() || rewrittenBegin.isRewritten() || rewrittenEnd.isRewritten()) {
			return new FunctionCallForm(
					new RangeChoice(
							(AssignmentTarget)rewrittenTarget.getExpression(), 
							rewrittenBegin.getExpression(), 
							rewrittenEnd.getExpression()), 
					rewrittenTarget.getAssignments(),
					rewrittenBegin.getAssignments(),
					rewrittenEnd.getAssignments());
		} else {
			return this;
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(target.getAssignedVariable());
	}	

}
