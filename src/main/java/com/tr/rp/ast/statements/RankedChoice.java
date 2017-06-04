package com.tr.rp.ast.statements;

import java.util.Arrays;
import java.util.Set;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ChooseMergingIterator;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.IteratorSplitter;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

/**
 * The RankedChoice class is constructed with two statements (s1, s2) and a rank r. 
 * It implements the ranked choice construct, where "normally" (with rank 0) s1 is 
 * executed, and "exceptionally" (with rank r) s2 is executed. Different constructors 
 * are provided for ease of use.
 */
public class RankedChoice extends AbstractStatement {

	public final AbstractStatement s1;
	public final AbstractStatement s2;
	public final AbstractExpression rank;

	public RankedChoice(AbstractStatement s1, AbstractStatement s2, AbstractExpression rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = rank;
	}

	public RankedChoice(AssignmentTarget target, AbstractExpression v1, AbstractExpression v2, int rank) {
		this(target, v1, v2, new Literal<Integer>(rank));
	}

	public RankedChoice(AssignmentTarget target, AbstractExpression v1, AbstractExpression v2, AbstractExpression rank) {
		this.s1 = new Assign(target, v1);
		this.s2 = new Assign(target, v2);
		this.rank = rank;
	}

	public RankedChoice(String variable, AbstractExpression v1, AbstractExpression v2, int rank) {
		this(new AssignmentTarget(variable), v1, v2, rank);
	}

	public RankedChoice(AbstractStatement s1, AbstractStatement s2, int rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = new Literal<Integer>(rank);
	}
	
	public RankedChoice(AbstractStatement s1, AbstractStatement s2) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = Literal.ZERO;
	}
	
	public RankedChoice(AssignmentTarget var, int[] values, int rankIncrement) {
		this.rank = new Literal<Integer>(rankIncrement);
		if (values.length == 2) {
			this.s1 = new Assign(var, values[0]);
			this.s2 = new Assign(var, values[1]);
		} else {
			this.s1 = new Assign(var, values[0]);
			this.s2 = new RankedChoice(var, Arrays.copyOfRange(values, 1, values.length), rankIncrement);
		}
	}

	public RankedChoice(AssignmentTarget var, int[] values, int[] ranks) {
		if (ranks.length == 0) throw new IllegalArgumentException();
		if (values.length != ranks.length + 1) throw new IllegalArgumentException();
		this.rank = new Literal<Integer>(ranks[0]);
		this.s1 = new Assign(var, values[0]);
		if (values.length == 2) {
			this.s2 = new Assign(var, values[1]);
		} else {
			this.s2 = new RankedChoice(var, Arrays.copyOfRange(values, 1, values.length), 
					Arrays.copyOfRange(ranks, 1, ranks.length));
		}
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, rank);
		AbstractExpression rank2 = rt.getExpression(0);
		IteratorSplitter<VarStore> split = new IteratorSplitter<VarStore>(rt);
		RankedIterator<VarStore> merge = new ChooseMergingIterator(
				s1.getIterator(split.getA(), c), 
				s2.getIterator(split.getB(), c), 
				rank2,
				this);
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

}
