package com.tr.rp.core.rankediterators;

import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.AbsurdIterator;
import com.tr.rp.iterators.ranked.ChooseMergingIteratorFixed;
import com.tr.rp.iterators.ranked.ChooseMergingIteratorVariable;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.VarStore;

public class MergingIteratorTest extends RPLBaseTest {

	public void testEqualInput1() throws RPLException {
		for (int i = 0; i < 5; i++) {
			ChooseMergingIteratorFixed s = new ChooseMergingIteratorFixed(getTestIterator(), getTestIterator(), i);
			DuplicateRemovingIterator<VarStore> ds = new DuplicateRemovingIterator<VarStore>(s);
			iteratorsEqual(ds, getTestIterator());
		}
	}
	
	public void testWithNonEmptyIterators1() throws RPLException {
		for (int i = 0; i < 12; i++) {
			for (int as = 1; as < 6; as++) {
				for (int bs = 1; bs < 6; bs++) {
					int count = 0;
					ChooseMergingIteratorFixed s = new ChooseMergingIteratorFixed(getIterator("a", as), getIterator("b", bs), i);
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
	
	public void testWithEmptyA1() throws RPLException {
		for (int i = 0; i < 10; i++) {
			for (int bs = 1; bs < 6; bs++) {
				ChooseMergingIteratorFixed s = new ChooseMergingIteratorFixed(new AbsurdIterator<VarStore>(), getIterator("b", bs), i);
				for (int j = 0; j < bs; j++) {
					assert(s.next());
					assertEquals(s.getItem().getIntValue("b"), j);
				}
				assert(!s.next());
			}
		}
	}
	
	public void testWithEmptyB1() throws RPLException {
		for (int i = 0; i < 10; i++) {
			for (int as = 1; as < 6; as++) {
				ChooseMergingIteratorFixed s = new ChooseMergingIteratorFixed(getIterator("a", as), new AbsurdIterator<VarStore>(), i);
				for (int j = 0; j < as; j++) {
					assert(s.next());
					assertEquals(s.getItem().getIntValue("a"), j);
				}
				assert(!s.next());
			}
		}
	}
	
	public void testEqualInput2() throws RPLException {
		for (int i = 0; i < 5; i++) {
			ChooseMergingIteratorVariable s = new ChooseMergingIteratorVariable(getTestIterator(), getTestIterator(), new Literal<Integer>(i));
			DuplicateRemovingIterator<VarStore> ds = new DuplicateRemovingIterator<VarStore>(s);
			iteratorsEqual(ds, getTestIterator());
		}
	}
	
	public void testWithNonEmptyIterators2() throws RPLException {
		for (int i = 0; i < 12; i++) {
			for (int as = 1; as < 6; as++) {
				for (int bs = 1; bs < 6; bs++) {
					int count = 0;
					ChooseMergingIteratorVariable s = new ChooseMergingIteratorVariable(getIterator("a", as), getIterator("b", bs), new Literal<Integer>(i));
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
	
	public void testWithEmptyA2() throws RPLException {
		for (int i = 0; i < 10; i++) {
			for (int bs = 1; bs < 6; bs++) {
				ChooseMergingIteratorVariable s = new ChooseMergingIteratorVariable(new AbsurdIterator<VarStore>(), getIterator("b", bs), new Literal<Integer>(i));
				for (int j = 0; j < bs; j++) {
					assert(s.next());
					assertEquals(s.getItem().getIntValue("b"), j);
				}
				assert(!s.next());
			}
		}
	}
	
	public void testWithEmptyB2() throws RPLException {
		for (int i = 0; i < 10; i++) {
			for (int as = 1; as < 6; as++) {
				ChooseMergingIteratorVariable s = new ChooseMergingIteratorVariable(getIterator("a", as), new AbsurdIterator<VarStore>(), new Literal<Integer>(i));
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
