package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIllegalRankException;
import com.tr.rp.iterators.ranked.ChooseMergingIteratorFixed;
import com.tr.rp.iterators.ranked.ChooseMergingIteratorVariable;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.IteratorSplitter;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * The RankedChoice class is constructed with two statements (s1, s2) and a rank r. 
 * It implements the ranked choice construct, where "normally" (with rank 0) s1 is 
 * executed, and "exceptionally" (with rank r) s2 is executed. Different constructors 
 * are provided for ease of use.
 */
public class RankedChoice extends AbstractStatement implements RankedChoiceErrorHandler {

	private final AbstractStatement s1;
	private final AbstractStatement s2;
	private final AbstractExpression rank;
	private final RankedChoiceErrorHandler errorHandler;
	
	public RankedChoice(AbstractStatement s1, AbstractStatement s2, AbstractExpression rank, 
			RankedChoiceErrorHandler errorHandler) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = rank;
		this.errorHandler = errorHandler;
	}

	public RankedChoice(AssignmentTarget target, AbstractExpression v1, AbstractExpression v2, 
			AbstractExpression rank, RankedChoiceErrorHandler errorHandler) {
		this.s1 = new Assign(target, v1);
		this.s2 = new Assign(target, v2);
		this.rank = rank;
		this.errorHandler = errorHandler;
	}

	public RankedChoice(AbstractStatement s1, AbstractStatement s2, AbstractExpression rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = rank;
		this.errorHandler = this;
	}

	public RankedChoice(AssignmentTarget target, AbstractExpression v1, AbstractExpression v2, 
			AbstractExpression rank) {
		this.s1 = new Assign(target, v1);
		this.s2 = new Assign(target, v2);
		this.rank = rank;
		this.errorHandler = this;
	}


	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, rank);
		AbstractExpression rank2 = rt.getExpression(0);
		IteratorSplitter<VarStore> split = new IteratorSplitter<VarStore>(rt);
		RankedIterator<VarStore> merge;
		if (rank2.hasDefiniteValue()) {
			int rankValue = rank2.getDefiniteValue(Type.INT);
			if (rankValue < 0) {
				throw new RPLIllegalRankException(rankValue, rank, this);
			}
			merge = new ChooseMergingIteratorFixed(
					s1.getIterator(split.getA(), c), 
					s2.getIterator(split.getB(), c), 
					rankValue);
		} else {
			merge = new ChooseMergingIteratorVariable(
					s1.getIterator(split.getA(), c), 
					s2.getIterator(split.getB(), c), 
					rank2,
					errorHandler);
		}
		return new DuplicateRemovingIterator<VarStore>(merge);
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
		return Objects.hash(s1, s2, rank);
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

	@Override
	public void handleRankExpressionError(RPLException e) throws RPLException {
		e.setStatement(this);
		e.setExpression(rank);
		throw e;
	}

	@Override
	public void illegalRank(int ri) throws RPLException {
		throw new RPLIllegalRankException(ri, rank, this);
	}	
	
}
