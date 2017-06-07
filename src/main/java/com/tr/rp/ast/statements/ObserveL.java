package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.ProgramBuilder;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.expressions.Plus;
import com.tr.rp.ast.expressions.RankExpr;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.AbsurdIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

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

	public ObserveL(AbstractExpression b, int rank) {
		this(b, new Literal<Integer>(rank));
	}
	
	public ObserveL(AbstractExpression b, AbstractExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		try {
			AbstractExpression rb = new RankExpr(b);
			AbstractExpression rnb = new RankExpr(new Not(b));
			// Do rank transformation here
			RankTransformIterator rt = 
					new RankTransformIterator(in, this, rb, rnb);
			rb = rt.getExpression(0);
			rnb = rt.getExpression(1);
			// Normal behavior if rank(b) is infinity, is to leave the prior ranking
			// unchanged. If iterative deepening is enabled we need to block execution.
			if (rb.equals(Literal.MAX) && c.isDestructiveLConditioning()) {
				return new AbsurdIterator<VarStore>();
			}
			// Construct observe-L statement
			AbstractExpression cond = Expressions.leq(rb, rank);
			AbstractExpression r1 = Expressions.minus(Expressions.plus(rank, rnb), rb);
			AbstractExpression r2 = Expressions.minus(rb, rank);
			AbstractStatement c1 = new RankedChoice(
					new Observe(b),
					new Observe(new Not(b)),
					r1);
			AbstractStatement c2 = new RankedChoice(
					new Observe(new Not(b)),
					new Observe(b),
					r2);
			AbstractStatement statement = new ProgramBuilder()
					.add(new IfElse(cond, c1, c2))
					.build();
			// Execute
			return statement.getIterator(rt, c);
		} catch (RPLException e) {
			e.setStatement(this);
			throw e;
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
			return new FunctionCallForm(
					new ObserveL(rewrittenExp.getExpression(), rewrittenRank.getExpression()), 
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

}
