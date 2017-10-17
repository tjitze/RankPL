package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import static com.tr.rp.ast.expressions.Expressions.*;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.Guard;
import com.tr.rp.executors.LShifter;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.types.Type;

/**
 * Implements L-conditioning.
 * This is equivalent to:
 * 	if rank(b) <= x then
 *		observe b [x-rank(b)+rank(-b)] observe -b
 *	else
 *		observe -b [rank(b)-x] observe b
 */
public class ObserveL extends AbstractStatement {

	private AbstractExpression b;
	private AbstractExpression rank;

	public ObserveL(AbstractExpression b, AbstractExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		if (!rank.hasDefiniteValue()) {
			ObserveJ obs1 = new ObserveJ(b, plus(minus(rank, rank(b)), rank(not(b)))) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveL.this.handleConditionException(e);
				}
				public void handleRankExpressionException(RPLException e) throws RPLException {
					ObserveL.this.handleRankExpressionException(e);
				}
			};
			ObserveJ obs2 = new ObserveJ(not(b), minus(rank(b), rank)) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveL.this.handleConditionException(e);
				}
				public void handleRankExpressionException(RPLException e) throws RPLException {
					ObserveL.this.handleRankExpressionException(e);
				}
			};
			IfElse ie = new IfElse(leq(rank(b), rank), obs1, obs2) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveL.this.handleConditionException(e);
				}
			};
			return ie.getExecutor(out, c);
		} else {
			int shift;
			try {
				shift = rank.getDefiniteValue(Type.INT);
			} catch (RPLException e) {
				throw new RuntimeException(e);
			}
			RankTransformer<AbstractExpression> transformCondition = RankTransformer.create(b);
			LShifter exec = new LShifter(Guard.checkIfEnabled(out), transformCondition::get, shift) {
				public void handleConditionException(RPLException e) throws RPLException {
					ObserveL.this.handleConditionException(e);
				}
			};
			return transformCondition.getExecutor(exec, this);
		}
	}

	public String toString() {
		String bString = b.toString();
		if (bString.startsWith("(") && bString.endsWith(")")) {
			bString = bString.substring(1, bString.length()-1);
		}
		String rankString = rank.toString();
		if (!(rankString.startsWith("(") && rankString.endsWith(")"))) {
			rankString = "(" + rankString + ")";
		}
		return "observe-l " + rankString + " " + bString;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveL &&
				((ObserveL)o).b.equals(b) &&
				((ObserveL)o).rank == rank;
	}	

	@Override
	public int hashCode() {
		return Objects.hash(b, rank);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveL((AbstractExpression)this.b.replaceVariable(a, b), (AbstractExpression)rank.replaceVariable(a, b));
	}
	
	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
		rank.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(b);
		ExtractedExpression rewrittenRank = FunctionCallForm.extractFunctionCalls(rank);
		if (rewrittenExp.isRewritten() || rewrittenRank.isRewritten()) {
			ObserveL ol = new ObserveL(rewrittenExp.getExpression(), rewrittenRank.getExpression());
			ol.setLineNumber(getLineNumber());
			return new FunctionCallForm(ol, rewrittenExp.getAssignments(), rewrittenRank.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}
	
	public void handleConditionException(RPLException e) throws RPLException {
		e.setExpression(b);
		e.setStatement(this);
		throw e;
	}

	public void handleRankExpressionException(RPLException e) throws RPLException {
		e.setExpression(rank);
		e.setStatement(this);
		throw e;
	}


}
