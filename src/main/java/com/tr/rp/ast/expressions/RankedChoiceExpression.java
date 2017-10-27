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

public class RankedChoiceExpression extends AbstractFunctionCall {

	private final AbstractExpression exp1;
	private final AbstractExpression exp2;
	private final AbstractExpression rank;
	
	public RankedChoiceExpression(AbstractExpression exp1, AbstractExpression exp2, AbstractExpression rank) {
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.rank = rank;
	}
	
	@Override
	public String toString() {
		return exp1 + " <<" + rank + ">> " + exp2;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof RankedChoiceExpression &&
				((RankedChoiceExpression)o).exp1.equals(exp1) &&
				((RankedChoiceExpression)o).exp2.equals(exp2) &&
				((RankedChoiceExpression)o).rank.equals(rank);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), exp1, rank, exp2);
	}

	@Override
	public Executor getExecutor(ExecutionContext c, String assignToVar, Executor out, FunctionCallForm fc) {
		return new MultiMergeExecutor(out) {
			@Override
			public void transform(VarStore in, Executor out2) throws RPLException {
				out2.push(in.create(assignToVar, exp1.getValue(in)), 0);
				out2.push(in.create(assignToVar, exp2.getValue(in)), rank.getIntValue(in));
				out2.close();
			}
		};
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int r) throws RPLException {
		AbstractExpression newExp1 = (AbstractExpression)exp1.transformRankExpressions(v, r);
		AbstractExpression newExp2 = (AbstractExpression)exp2.transformRankExpressions(v, r);
		AbstractExpression newRank = (AbstractExpression)rank.transformRankExpressions(v, r);
		if (newExp1 != exp1 || newExp2 != exp2 || newRank != rank) {
			RankedChoiceExpression rce = new RankedChoiceExpression(newExp1, newExp2, newRank);
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
			AbstractExpression newExp1 = (AbstractExpression)exp1.replaceEmbeddedFunctionCall(fc, var);
			AbstractExpression newExp2 = (AbstractExpression)exp2.replaceEmbeddedFunctionCall(fc, var);
			AbstractExpression newRank = (AbstractExpression)rank.replaceEmbeddedFunctionCall(fc, var);
			if (newExp1 != exp1 || newExp2 != exp2 || newRank != rank) {
				RankedChoiceExpression rce = new RankedChoiceExpression(newExp1, newExp2, newRank);
				rce.setLineNumber(getLineNumber());
				return rce;
			} else {
				return this;
			}
		}
	}

	@Override
	public void getVariables(Set<String> list) {
		exp1.getVariables(list);
		rank.getVariables(list);
		exp2.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return exp1.hasRankExpression() ||
				rank.hasRankExpression() ||
				exp2.hasRankExpression();
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = exp1.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		fc = rank.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		fc = exp2.getEmbeddedFunctionCall();
		if (fc != null) {
			return fc;
		}
		return this;
	}

}
