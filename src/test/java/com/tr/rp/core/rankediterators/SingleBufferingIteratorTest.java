package com.tr.rp.core.rankediterators;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.iterators.ranked.SingleBufferingIterator;
import com.tr.rp.ranks.Rank;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.VarStore;

public class SingleBufferingIteratorTest extends RPLBaseTest {

	public void testNoMoveBack() throws RPLException {
		SingleBufferingIterator<VarStore> bi = new SingleBufferingIterator<VarStore>(getTestIterator());

		assert(!bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v1);
		assert(bi.getRank() == 0);
		
		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v2);
		assert(bi.getRank() == 1);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);

		assert(bi.canMoveBack());

		assert(!bi.next());
		
		assert(bi.canMoveBack());

	}

	public void testMoveBackFirstIteration() throws RPLException {
		SingleBufferingIterator<VarStore> bi = new SingleBufferingIterator<VarStore>(getTestIterator());

		assert(!bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v1);
		assert(bi.getRank() == 0);

		assert(bi.canMoveBack());
		bi.moveBack();
		
		assert(bi.next());
		assert(bi.getItem() == v1);
		assert(bi.getRank() == 0);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v2);
		assert(bi.getRank() == 1);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);

		assert(bi.canMoveBack());

		assert(!bi.next());
		
		assert(bi.canMoveBack());

	}

	public void testMoveBackSecondIteration() throws RPLException {
		SingleBufferingIterator<VarStore> bi = new SingleBufferingIterator<VarStore>(getTestIterator());

		assert(!bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v1);
		assert(bi.getRank() == 0);
		
		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v2);
		assert(bi.getRank() == 1);
		
		assert(bi.canMoveBack());
		bi.moveBack();

		assert(bi.next());
		assert(bi.getItem() == v2);
		assert(bi.getRank() == 1);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);

		assert(bi.canMoveBack());

		assert(!bi.next());

		assert(bi.canMoveBack());
	}

	public void testMoveBackThirdIteration() throws RPLException {
		SingleBufferingIterator<VarStore> bi = new SingleBufferingIterator<VarStore>(getTestIterator());

		assert(!bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v1);
		assert(bi.getRank() == 0);
		
		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v2);
		assert(bi.getRank() == 1);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);

		assert(bi.canMoveBack());
		bi.moveBack();

		assert(bi.next());
		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);

		assert(bi.canMoveBack());

		assert(!bi.next());
		
		assert(bi.canMoveBack());

	}
	
	public void testMoveBackFinalIteration() throws RPLException {
		SingleBufferingIterator<VarStore> bi = new SingleBufferingIterator<VarStore>(getTestIterator());

		assert(!bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v1);
		assert(bi.getRank() == 0);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v2);
		assert(bi.getRank() == 1);

		assert(bi.canMoveBack());

		assert(bi.next());
		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);

		assert(bi.canMoveBack());

		assert(!bi.next());
		
		assert(bi.canMoveBack());
		bi.moveBack();

		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);
		
		assert(!bi.canMoveBack());

		assert(!bi.next());
		
		assert(bi.canMoveBack());

		bi.moveBack();

		assert(bi.getItem() == v3);
		assert(bi.getRank() == 2);
		
		assert(!bi.canMoveBack());

		assert(!bi.next());
		
		assert(bi.canMoveBack());

	}
	
}
