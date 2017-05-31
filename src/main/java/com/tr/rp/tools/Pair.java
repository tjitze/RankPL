package com.tr.rp.tools;

public class Pair<A,B> {

	public final A a;
	public final B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Pair) &&
				((Pair<?,?>)o).a.equals(a) &&
				((Pair<?,?>)o).b.equals(b);
	}
	
	public int hashCode() {
		return a.hashCode() + 31*b.hashCode();
	}
	
	public String toString() {
		return "<"+a+","+b+">";
	}
}
