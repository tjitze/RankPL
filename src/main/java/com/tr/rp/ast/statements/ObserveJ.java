package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

/**
 * Implements J-conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveJ extends AbstractStatement implements ObserveErrorHandler, RankedChoiceErrorHandler {

	private AbstractExpression b;
	private AbstractExpression rank;
	
	public ObserveJ(AbstractExpression b, int rank) {
		this(b, new Literal<Integer>(rank));
	}
	public ObserveJ(AbstractExpression b, AbstractExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		return new RankedChoice(new Observe(b, this), new Observe(new Not(b), this), rank, this).getIterator(in, c);
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
		return "observe-j " + rankString + " " + bString;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveJ &&
				((ObserveJ)o).b.equals(b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(b, rank);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveJ((AbstractExpression)this.b.replaceVariable(a, b), (AbstractExpression)rank.replaceVariable(a, b));
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
			return new FunctionCallForm(
					new ObserveJ(rewrittenExp.getExpression(), rewrittenRank.getExpression()), 
					rewrittenExp.getAssignments(), 
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
	public void observeConditionError(RPLException e) throws RPLException {
		e.setExpression(b);
		e.setStatement(this);
		throw e;
	}

	@Override
	public void handleRankExpressionError(RPLException e) throws RPLException {
		e.setExpression(rank);
		e.setStatement(this);
		throw e;
	}	

}
