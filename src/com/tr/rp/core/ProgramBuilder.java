package com.tr.rp.core;

import com.tr.rp.statement.Composition;

public class ProgramBuilder {
	private DStatement a, b;
	public ProgramBuilder() {
		
	}
	public ProgramBuilder add(DStatement s) {
		if (a == null) {
			a = s;
		} else if (b == null) {
			b = s;
		} else {
			a = new Composition(a, b);
			b = s;
		}
		return this;
	}
	public DStatement build() {
		if (a == null) {
			throw new IllegalStateException();
		} else if (b == null) {
			return a;
		} else {
			return new Composition(a, b);
		}
	}
}

