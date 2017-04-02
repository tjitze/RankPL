package com.tr.rp.statement;

import java.util.Arrays;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.core.rankediterators.RestrictIterator;

public class Composition implements DStatement {

	private final DStatement a, b;
	private final int maxRank;
	
	public Composition(DStatement ... s) {
		this(Integer.MAX_VALUE, s);
	}
	
	public Composition(int maxRank, DStatement ... s) {
		this.a = s[0];
		this.maxRank = maxRank;
		if (s.length > 2) {
			this.b = new Composition(maxRank, Arrays.copyOfRange(s, 1, s.length));
		} else {
			this.b = s[1];
		}
	}
	
	@Override
	public RankedIterator getIterator(RankedIterator in) {
		in = new RestrictIterator(in, maxRank);
		in = a.getIterator(in);
		in = new RestrictIterator(in, maxRank);
		in = b.getIterator(in);
		return in;
	}

	public String toString() {
		return a + ": " + b;
	}
	
	public boolean equals(Object o) {
		return o instanceof Composition &&
				((Composition)o).a.equals(a) &&
				((Composition)o).b.equals(b);
	}

	@Override
	public boolean containsVariable(String var) {
		return a.containsVariable(var) || b.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Composition(maxRank, (DStatement)this.a.replaceVariable(a, b),
				(DStatement)this.b.replaceVariable(a, b));
	}

}
