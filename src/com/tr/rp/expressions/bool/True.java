package com.tr.rp.expressions.bool;

public class True extends BoolLiteral {

	public True() {
		super(true);
	}

	public boolean equals(Object o) {
		return o instanceof True;
	}
}
