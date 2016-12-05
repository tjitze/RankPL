package com.tr.rp.core;

/**
 * Some convenience methods to do calculations with ranks. In particular,
 * implements addition and subtraction of ranks, which cannot be done using
 * regular + and - operators, because of their integer overflow/underflow
 * behaviour.
 */
public class Rank {

	public static final int add(int r1, int r2) {
		if (r1 == Integer.MAX_VALUE || r2 == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		return r1 + r2;
	}
	
	public static final int sub(int r1, int r2) {
		if (r2 == Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Cannot subtract infinity");
		}
		if (r1 == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		return r1 - r2;
	}
	
}
