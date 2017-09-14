package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.EvaluationErrorHandler;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.JShifter;
import com.tr.rp.exec.RankTransformer;
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
		// TODO: properly handle this
		if (!rank.hasDefiniteValue()) {
			throw new RuntimeException("Rank must be definite");
		}
		int shift;
		try {
			shift = rank.getDefiniteValue(Type.INT);
		} catch (RPLException e) {
			throw new RuntimeException(e);
		}

		RankTransformer<AbstractExpression> transformCondition = RankTransformer.create(condition);
		JShifter exec = new JShifter(out, transformCondition::get, shift);
		exec.setErrorHandler(this);
		transformCondition.setOutput(exec, this);
		return transformCondition;
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
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveJ((AbstractExpression)condition.replaceVariable(a, b), (AbstractExpression)rank.replaceVariable(a, b));
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
			return new FunctionCallForm(
					new ObserveJ(rewrittenCondition.getExpression(), rewrittenRank.getExpression()), 
					rewrittenCondition.getAssignments(), 
					rewrittenRank.getAssignments());
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


}
