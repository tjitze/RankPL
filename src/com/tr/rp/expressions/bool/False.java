package com.tr.rp.expressions.bool;

public final class False extends BoolLiteral {

	public False() {
		super(false);
	}

	public boolean equals(Object o) {
		return o instanceof False;
	}
}
