package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.AssignmentTargetTerminal;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.types.Type;

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
		this.target = new AssignmentTargetTerminal(targetVariable);
		this.beginExp = beginExp;
		this.endExp = endExp;
	}

	public RangeChoice(String targetVariable, int begin, int end) {
		this.target = new AssignmentTargetTerminal(targetVariable);
		this.beginExp = new Literal<Integer>(begin);
		this.endExp = new Literal<Integer>(end);
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AssignmentTarget> transformTarget = RankTransformer.create(target);
		RankTransformer<AbstractExpression> transformBegin = RankTransformer.create(beginExp);
		RankTransformer<AbstractExpression> transformEnd = RankTransformer.create(endExp);
		Executor exec = new Executor() {
			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				int begin = transformBegin.get().getValue(s.getVarStore(), Type.INT);
				int end = transformEnd.get().getValue(s.getVarStore(), Type.INT);
				if (begin > end) {
					throw new RPLMiscException("Begin larger than end", RangeChoice.this);
				}
				for (int i = begin; i < end; i++) {
					out.push(transformTarget.get().assign(s.getVarStore(), i), s.getRank());
				}
			}
		};
		transformTarget.setOutput(transformBegin, this);
		transformBegin.setOutput(transformEnd, this);
		transformEnd.setOutput(exec, this);
		return transformTarget;
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
	public int hashCode() {
		return Objects.hash(getClass(), beginExp, endExp, target);
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
		target.getAssignedVariables(variables);
	}


}
