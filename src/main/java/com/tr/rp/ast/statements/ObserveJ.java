package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRankException;
import com.tr.rp.executors.EvaluationErrorHandler;
import com.tr.rp.executors.ExceptionExecutor;
import com.tr.rp.executors.ExecuteOnceExecutor;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.Guard;
import com.tr.rp.executors.JShifter;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.types.Type;

/**
 * Implements J-conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveJ extends AbstractStatement implements EvaluationErrorHandler {

	private AbstractExpression condition;
	private AbstractExpression rank;
	
	public ObserveJ(AbstractExpression condition, AbstractExpression rank) {
		this.condition = condition;
		this.rank = rank;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		if (!rank.hasDefiniteValue()) {
			Observe o1 = new Observe(condition) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveJ.this.handleConditionException(e);
				}
			};
			Observe o2 = new Observe(Expressions.not(condition)) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveJ.this.handleConditionException(e);
				}
			};
			RankedChoice rc = new RankedChoice(o1, o2, rank) {
				public void handleRankExpressionException(RPLException e) throws RPLException {
					ObserveJ.this.handleRankExpressionException(e);
				}
			};
			return rc.getExecutor(out, c);
		} else {
			int shift;
			try {
				shift = rank.getDefiniteValue(Type.INT);
			} catch (RPLException e) {
				return new ExecuteOnceExecutor(() -> handleRankExpressionException(e));
			}
			if (shift < 0) {
				return new ExecuteOnceExecutor(() -> handleRankExpressionException(new RPLIllegalRankException(shift, rank)));
			}
			RankTransformer<AbstractExpression> transformCondition = RankTransformer.create(condition);
			JShifter exec = new JShifter(Guard.checkIfEnabled(out), transformCondition::get, shift) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveJ.this.handleConditionException(e);
				}
			};
			return transformCondition.getExecutor(exec, this);
		}
	}	

	public String toString() {
		String bString = condition.toString();
		if (bString.startsWith("(") && bString.endsWith(")")) {
			bString = bString.substring(1, bString.length()-1);
		}
		String rankString = rank.toString();
		if (!(rankString.startsWith("(") && rankString.endsWith(")"))) {
			rankString = "(" + rankString + ")";
		}
		return "observe-j " + rankString + " " + bString;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveJ &&
				((ObserveJ)o).condition.equals(condition);
	}

	@Override
	public int hashCode() {
		return Objects.hash(condition, rank, this.getClass());
	}	

	@Override
	public void getVariables(Set<String> list) {
		condition.getVariables(list);
		rank.getVariables(list);
	}
	
	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenCondition = FunctionCallForm.extractFunctionCalls(condition);
		ExtractedExpression rewrittenRank = FunctionCallForm.extractFunctionCalls(rank);
		if (rewrittenCondition.isRewritten() || rewrittenRank.isRewritten()) {
			ObserveJ oj = new ObserveJ(rewrittenCondition.getExpression(), rewrittenRank.getExpression());
			oj.setLineNumber(getLineNumber());
			return new FunctionCallForm(oj, rewrittenCondition.getAssignments(), rewrittenRank.getAssignments());
		} else {
			return this;
		}
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}
	
	@Override
	public void handleEvaluationError(RPLException e) throws RPLException {
		e.setStatement(this);
		throw e;
	}

	/**
	 * Override for custom handling of condition expression exceptions
	 */
	public void handleConditionException(RPLException e) throws RPLException {
		e.setExpression(condition);
		e.setStatement(this);
		throw e;
	}

	/**
	 * Override for custom handling of rank expression exceptions
	 */
	public void handleRankExpressionException(RPLException e) throws RPLException {
		e.setExpression(rank);
		e.setStatement(this);
		throw e;
	}

}
