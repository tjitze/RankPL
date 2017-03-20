package com.tr.rp.statement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ChooseMergingIterator;
import com.tr.rp.core.rankediterators.DuplicateRemovingIterator;
import com.tr.rp.core.rankediterators.IteratorSplitter;
import com.tr.rp.core.rankediterators.MergingIterator;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;

public class Choose implements DStatement {

	public DStatement s1, s2;
	public NumExpression rank;

	public Choose(DStatement s1, DStatement s2, NumExpression rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = rank;
	}

	public Choose(String var, NumExpression v1, NumExpression v2, NumExpression rank) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, v2);
		this.rank = rank;
	}

	public Choose(String var, NumExpression v1, NumExpression v2, int rank) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, int v1, int v2, int rank) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, String var1, int var2, int rank) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, int v1, String var2, int rank) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, String var1, String var2, int rank) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, NumExpression e1, int v2, int rank) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, NumExpression e1, String var2, int rank) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, int v1, NumExpression e2, int rank) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, String var1, NumExpression e2, int rank) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(DStatement s1, DStatement s2, int rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = new IntLiteral(rank);
	}
	
	public Choose(String var, NumExpression v1, NumExpression v2) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, int v1, int v2) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, String var1, int var2) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, int v1, String var2) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, String var1, String var2) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, NumExpression e1, int v2) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, NumExpression e1, String var2) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, int v1, NumExpression e2) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, String var1, NumExpression e2) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(0);
	}

	public Choose(DStatement s1, DStatement s2) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = new IntLiteral(0);
	}
	
	public Choose(String var, int[] values, int rankIncrement) {
		this.rank = new IntLiteral(rankIncrement);
		if (values.length == 2) {
			this.s1 = new Assign(var, values[0]);
			this.s2 = new Assign(var, values[1]);
		} else {
			this.s1 = new Assign(var, values[0]);
			this.s2 = new Choose(var, Arrays.copyOfRange(values, 1, values.length), rankIncrement);
		}
	}

	public Choose(String var, int[] values, int[] ranks) {
		if (ranks.length == 0) throw new IllegalArgumentException();
		if (values.length != ranks.length + 1) throw new IllegalArgumentException();
		this.rank = new IntLiteral(ranks[0]);
		this.s1 = new Assign(var, values[0]);
		if (values.length == 2) {
			this.s2 = new Assign(var, values[1]);
		} else {
			this.s2 = new Choose(var, Arrays.copyOfRange(values, 1, values.length), 
					Arrays.copyOfRange(ranks, 1, ranks.length));
		}
	}

	@Override
	public RankedIterator getIterator(RankedIterator in) {
		RankTransformIterator<NumExpression> rt = 
				new RankTransformIterator<NumExpression>(in, rank);
		NumExpression rank2 = rt.getExpression(0);
		IteratorSplitter split = new IteratorSplitter(rt);
		RankedIterator merge = new ChooseMergingIterator(
				s1.getIterator(split.getA()), 
				s2.getIterator(split.getB()), 
				rank2);
		return new DuplicateRemovingIterator(merge);
	}
	
	public String toString() {
		return "{" + s1 + " <" + rank + "> " + s2 + "}";
	}
	
	public boolean equals(Object o) {
		return o instanceof Choose &&
				((Choose)o).s1.equals(s1) &&
				((Choose)o).s2.equals(s2) &&
				((Choose)o).rank.equals(rank);
	}

}
