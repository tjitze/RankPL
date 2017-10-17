package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRankException;
import com.tr.rp.executors.Deduplicator;
import com.tr.rp.executors.DynamicMerger;
import com.tr.rp.executors.ExceptionExecutor;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.Merger;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.executors.Splitter;
import com.tr.rp.varstore.types.Type;

/**
 * The RankedChoice class is constructed with two statements (s1, s2) and a rank r. 
 * It implements the ranked choice construct, where "normally" (with rank 0) s1 is 
 * executed, and "exceptionally" (with rank r) s2 is executed. Different constructors 
 * are provided for ease of use.
 */
public class RankedChoice extends AbstractStatement {

	private final AbstractStatement s1;
	private final AbstractStatement s2;
	private final AbstractExpression rank;

	public RankedChoice(AbstractStatement s1, AbstractStatement s2, AbstractExpression rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = rank;
	}

	public RankedChoice(AssignmentTarget target, AbstractExpression v1, AbstractExpression v2, 
			AbstractExpression rank) {
		this.s1 = new Assign(target, v1);
		this.s2 = new Assign(target, v2);
		this.rank = rank;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		if (rank.hasDefiniteValue()) {
			int shift = 0;
			try {
				shift = rank.getDefiniteValue(Type.INT);
			} catch (RPLException ex) {
				return new ExceptionExecutor(ex);
			}
			if (shift < 0) {
				return new ExceptionExecutor(new RPLIllegalRankException(shift, rank, this));
			}
			Merger m = new Merger(out, shift);
			Executor exec1 = s1.getExecutor(m.getIn1(), c);
			Executor exec2 = s2.getExecutor(m.getIn2(), c);
			return new Splitter(exec1, exec2);
		} else {
			RankTransformer<AbstractExpression> tr = RankTransformer.create(rank);
			DynamicMerger m = new DynamicMerger(out, tr) {
				public void handleRankExpressionException(RPLException e) throws RPLException {
					RankedChoice.this.handleRankExpressionException(e);
				}
			};
			Executor exec1 = s1.getExecutor(m.getIn1(), c);
			Executor exec2 = s2.getExecutor(m.getIn2(), c);
			Splitter sp = new Splitter(exec1, exec2);
			return tr.getExecutor(sp, this);
		}
	}	
	
	public String toString() {
		String rankString = rank.toString();
		if (rankString.startsWith("(") && rankString.endsWith(")")) {
			rankString = rankString.substring(1, rankString.length()-1);
		}
		return "normally ("+rankString+") " + s1 + " exceptionally " + s2;
	}
	
	public boolean equals(Object o) {
		return o instanceof RankedChoice &&
				((RankedChoice)o).s1.equals(s1) &&
				((RankedChoice)o).s2.equals(s2) &&
				((RankedChoice)o).rank.equals(rank);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass(), s1, s2, rank);
	}	


	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new RankedChoice((AbstractStatement)s1.replaceVariable(a, b),
				(AbstractStatement)s2.replaceVariable(a, b),
				(AbstractExpression)rank.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		s1.getVariables(list);
		s2.getVariables(list);
		rank.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		AbstractStatement s1r = s1.rewriteEmbeddedFunctionCalls();
		AbstractStatement s2r = s2.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenRank = FunctionCallForm.extractFunctionCalls(rank);
		if (rewrittenRank.isRewritten()) {
			return new FunctionCallForm(new RankedChoice(s1r, s2r, rewrittenRank.getExpression()), rewrittenRank.getAssignments());
		} else {
			return new RankedChoice(s1r, s2r, rank);
		}
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		s1.getAssignedVariables(variables);
		s2.getAssignedVariables(variables);
	}

	/**
	 * Override to handle exception resulting from rank expression evaluation
	 */
	public void handleRankExpressionException(RPLException e) throws RPLException {
		e.setStatement(this);
		throw e;
	}

}
