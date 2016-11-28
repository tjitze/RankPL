package com.tr.rp.statement;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.DStatement;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.IteratorSplitter;
import com.tr.rp.core.rankediterators.MergingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;

public class Choose implements DStatement {

	public DStatement s1, s2;
	public IntLiteral rank;

//	public Choose(DStatement s1, DStatement s2, DExpression rank) {
//		this.s1 = s1;
//		this.s2 = s2;
//		this.rank = rank;
//	}
//
//	public Choose(String var, DExpression v1, DExpression v2, DExpression rank) {
//		this.s1 = new Assign(var, v1);
//		this.s2 = new Assign(var, v2);
//		this.rank = rank;
//	}

	public Choose(String var, DExpression v1, DExpression v2, int rank) {
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

	public Choose(String var, DExpression e1, int v2, int rank) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, DExpression e1, String var2, int rank) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, int v1, DExpression e2, int rank) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(String var, String var1, DExpression e2, int rank) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(rank);
	}

	public Choose(DStatement s1, DStatement s2, int rank) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = new IntLiteral(rank);
	}
	
	public Choose(String var, DExpression v1, DExpression v2) {
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

	public Choose(String var, DExpression e1, int v2) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, v2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, DExpression e1, String var2) {
		this.s1 = new Assign(var, e1);
		this.s2 = new Assign(var, var2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, int v1, DExpression e2) {
		this.s1 = new Assign(var, v1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(0);
	}

	public Choose(String var, String var1, DExpression e2) {
		this.s1 = new Assign(var, var1);
		this.s2 = new Assign(var, e2);
		this.rank = new IntLiteral(0);
	}

	public Choose(DStatement s1, DStatement s2) {
		this.s1 = s1;
		this.s2 = s2;
		this.rank = new IntLiteral(0);
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> parent) {
		IteratorSplitter<VarStore> split = new IteratorSplitter<VarStore>(parent);
		RankedIterator<VarStore> ria = s1.getIterator(split.getA());
		RankedIterator<VarStore> rib = s2.getIterator(split.getB());
		return new MergingIterator<VarStore>(ria, rib, 0, rank.value);
	}
	
}
