package com.tr.rp.core.rankediterators;

import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Literal;
import com.tr.rp.statement.RPLBaseTest;

public class MergingIteratorTest extends RPLBaseTest {

	public void testEqualInput() throws RPLException {
		for (int i = 0; i < 5; i++) {
			ChooseMergingIterator s = new ChooseMergingIterator(getTestIterator(), getTestIterator(), new Literal<Integer>(i));
			DuplicateRemovingIterator<VarStore> ds = new DuplicateRemovingIterator<VarStore>(s);
			iteratorsEqual(ds, getTestIterator());
		}
	}
	
	public void testWithNonEmptyIterators() throws RPLException {
		for (int i = 0; i < 12; i++) {
			for (int as = 1; as < 6; as++) {
				for (int bs = 1; bs < 6; bs++) {
					int count = 0;
					ChooseMergingIterator s = new ChooseMergingIterator(getIterator("a", as), getIterator("b", bs), new Literal<Integer>(i));
					while (s.next()) {
						count++;
						VarStore v = s.getItem();
						if (v.getValue("a") != null) {
							assertEquals(s.getRank(), v.getIntValue("a"));
						} else if (v.getValue("b") != null) {
							assertEquals(s.getRank() - i, v.getIntValue("b"));
						}
					}
					assert(count == as + bs);
				}
			}
		}
	}
	
	public void testWithEmptyA() throws RPLException {
		for (int i = 0; i < 10; i++) {
			for (int bs = 1; bs < 6; bs++) {
				ChooseMergingIterator s = new ChooseMergingIterator(new AbsurdIterator<VarStore>(), getIterator("b", bs), new Literal<Integer>(i));
				for (int j = 0; j < bs; j++) {
					assert(s.next());
					assertEquals(s.getItem().getIntValue("b"), j);
				}
				assert(!s.next());
			}
		}
	}
	
	public void testWithEmptyB() throws RPLException {
		for (int i = 0; i < 10; i++) {
			for (int as = 1; as < 6; as++) {
				ChooseMergingIterator s = new ChooseMergingIterator(getIterator("a", as), new AbsurdIterator<VarStore>(), new Literal<Integer>(i));
				for (int j = 0; j < as; j++) {
					assert(s.next());
					assertEquals(s.getItem().getIntValue("a"), j);
				}
				assert(!s.next());
			}
		}
	}
	

	private RankedIterator<VarStore> getIterator(String var, int b) {
		return new RankedIterator<VarStore>() {
			private int i = -1;

			@Override
			public boolean next() throws RPLException {
				i++;
				return i < b;
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore v = new VarStore();
				v = v.create(var, i);
				return v;
			}

			@Override
			public int getRank() {
				return i;
			}
			
		};
	}
	
}
