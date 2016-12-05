package com.tr.rp.statement;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;

import junit.framework.TestCase;

public abstract class RPLBaseTest extends TestCase {

	protected static final VarStore v1 = new VarStore();
	protected static final VarStore v2 = new VarStore();
	protected static final VarStore v3 = new VarStore();

	static {
		v1.setValue("a", 1);
		v2.setValue("a", 2);
		v3.setValue("a", 3);
		v1.setValue("b", 5);
		v2.setValue("b", 5);
		v3.setValue("b", 5);
	}

	protected RankedIterator getTestIterator() {
		final LinkedList<VarStore> list = new LinkedList<VarStore>() {{
			addLast(v1);
			addLast(v2);
			addLast(v3);
		}};
		return new RankedIterator() {

			private int c = -1;
			
			@Override
			public boolean next() {
				c++;
				return c < list.size();
			}

			@Override
			public VarStore getVarStore() {
				if (c < 0 || c >= list.size()) return null;
				return list.get(c);
			}

			@Override
			public int getRank() {
				return c;
			}
		};
	}

}
