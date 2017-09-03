package com.tr.rp.core.rankediterators;

import static com.tr.rp.ast.expressions.Expressions.geq;
import static com.tr.rp.ast.expressions.Expressions.lit;
import static com.tr.rp.ast.expressions.Expressions.var;

import java.util.HashSet;
import java.util.Set;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.DecreasingIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.RankedItem;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.PMapVarStore;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class DecreasingIteratorTest extends RPLBaseTest {

	public void testIterator() throws RPLException {
		RankedIterator<VarStore> it = getIterator("x", 6);
		assert(it.next());
		assert(it.getItem().getValue("x", Type.INT) == 0);
		assert(it.getRank() == 0);
		assert(it.next());
		assert(it.getItem().getValue("x", Type.INT) == 1);
		assert(it.getRank() == 1);
		assert(it.next());
		assert(it.getItem().getValue("x", Type.INT) == 2);
		assert(it.getRank() == 2);
		assert(it.next());
		assert(it.getItem().getValue("x", Type.INT) == 3);
		assert(it.getRank() == 3);
		assert(it.next());
		assert(it.getItem().getValue("x", Type.INT) == 4);
		assert(it.getRank() == 4);
		assert(it.next());
		assert(it.getItem().getValue("x", Type.INT) == 5);
		assert(it.getRank() == 5);
		assert(!it.next());
	}
	
	public void testDecTopItems() throws RPLException {
		for (int s = 0; s < 10; s++) { // size
			for (int r = 0; r < 10; r++) { // rank decrease
				for (int b = 1; b < s; b++) { // min rank to decrease
					if (b < r) continue;
					RankedIterator<VarStore> it = getIterator("x", s);
					DecreasingIterator ii = new DecreasingIterator(it, geq(var("x"), lit(b)), r);
					Set<RankedItem<Integer>> actualItems = new HashSet<RankedItem<Integer>>();
					int previousRank = -1;
					for (int i = 0; i < s; i++) {
						assert(ii.next());
						assert(ii.getRank() >= previousRank);
						previousRank = ii.getRank();
						actualItems.add(new RankedItem<Integer>(ii.getItem().getValue("x", Type.INT), ii.getRank()));
					}
					Set<RankedItem<Integer>> expectedItems = new HashSet<RankedItem<Integer>>();
					for (int i = 0; i < s; i++) {
						expectedItems.add(new RankedItem<Integer>(i, i >= b? i - r: i));
					}
					assert(actualItems.equals(expectedItems));
				}
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
				VarStore v = new PMapVarStore();
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
