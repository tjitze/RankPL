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
		String as = a.toString();
		String bs = b.toString();
		if (as.startsWith("{") && as.endsWith("}") && as.length() > 2) {
			as = as.substring(1, as.length() - 1);
		}
		if (bs.startsWith("{") && bs.endsWith("}") && bs.length() > 2) {
			bs = bs.substring(1, bs.length() - 1);
		}
		return "{" + a + ";" + b + "}";
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
