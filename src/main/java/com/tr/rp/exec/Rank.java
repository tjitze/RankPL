package com.tr.rp.exec;

/**
 * Some convenience methods to do calculations with ranks. In particular,
 * implements addition and subtraction of ranks, which cannot be done using
 * regular + and - operators, because of their integer overflow/underflow
 * behavior.
 * 
 */
public class Rank {
	
	public static final int MAX = Integer.MAX_VALUE;

	public static final int add(int r1, int r2) {
		if (r1 == Rank.MAX || r2 == Rank.MAX) {
			return Rank.MAX;
		}
		return r1 + r2;
	}
	
	public static final int sub(int r1, int r2) {
		if (r2 == Rank.MAX) {
			throw new IllegalArgumentException("Cannot subtract infinity");
		}
		if (r1 == Rank.MAX) {
			return Rank.MAX;
		}
		return r1 - r2;
	}
	
}
