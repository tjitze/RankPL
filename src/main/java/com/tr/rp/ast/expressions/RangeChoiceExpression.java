package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.statements.FunctionCallForm;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.MultiMergeExecutor;
import com.tr.rp.varstore.VarStore;

/**
 * Represents ranked choice between consecutive integers in a given range (specified
 * by the startInclusive and endExclusive expressions) where each choice is equally 
 * likely.
 */
public class RangeChoiceExpression extends AbstractFunctionCall {

	private final AbstractExpression startInclusiveExp;
	private final AbstractExpression endExclusiveExp;
	
	public RangeChoiceExpression(AbstractExpression startInclusiveExp, AbstractExpression endExclusiveExp) {
		this.startInclusiveExp = startInclusiveExp;
		this.endExclusiveExp = endExclusiveExp;
	}
	
	@Override
	public String toString() {
		return "<<" + startInclusiveExp + " ... " + endExclusiveExp + ">>";
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof RangeChoiceExpression &&
				((RangeChoiceExpression)o).startInclusiveExp.equals(startInclusiveExp) &&
				((RangeChoiceExpression)o).endExclusiveExp.equals(endExclusiveExp);
	}

	@Override
	public Executor getExecutor(ExecutionContext c, String assignToVar, Executor out, FunctionCallForm fc) {
		return new MultiMergeExecutor(out) {
			@Override
			public void transform(VarStore in, Executor out2) throws RPLException {
				int startInclusive;
				int endExclusive;
				try {
					startInclusive = startInclusiveExp.getIntValue(in);
					endExclusive = endExclusiveExp.getIntValue(in);
				} catch (RPLException e) {
					e.setStatement(fc);
					throw e;
				}
				for (int i = startInclusive; i < endExclusive; i++) {
					out2.push(in.create(assignToVar, i), 0);
				}
				out2.close();
			}
		};
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int r) throws RPLException {
		AbstractExpression newExp1 = (AbstractExpression)startInclusiveExp.transformRankExpressions(v, r);
		AbstractExpression newExp2 = (AbstractExpression)endExclusiveExp.transformRankExpressions(v, r);
		if (newExp1 != startInclusiveExp || newExp2 != endExclusiveExp) {
			RangeChoiceExpression rce = new RangeChoiceExpression(newExp1, newExp2);
			rce.setLineNumber(getLineNumber());
			return rce;
		} else {
			return this;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		if (fc == this) {
			return new Variable(var);
		} else {
			AbstractExpression newExp1 = (AbstractExpression)startInclusiveExp.replaceEmbeddedFunctionCall(fc, var);
			AbstractExpression newExp2 = (AbstractExpression)endExclusiveExp.replaceEmbeddedFunctionCall(fc, var);
			if (newExp1 != startInclusiveExp || newExp2 != endExclusiveExp) {
				RangeChoiceExpression rce = new RangeChoiceExpression(newExp1, newExp2);
				rce.setLineNumber(getLineNumber());
				return rce;
			} else {
				return this;
			}
		}
	}

	@Override
	public void getVariables(Set<String> list) {
		startInclusiveExp.getVariables(list);
		endExclusiveExp.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return startInclusiveExp.hasRankExpression() || endExclusiveExp.hasRankExpression();
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = startInclusiveExp.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		fc = endExclusiveExp.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), startInclusiveExp, endExclusiveExp);
	}

}
