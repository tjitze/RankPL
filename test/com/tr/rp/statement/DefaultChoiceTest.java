package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.Var;

public class DefaultChoiceTest extends RPLBaseTest {
	
	public void testDefaultChoice() {
		DStatement s1 = new Assign("b", new Plus("a", 10));
		DStatement s2 = new Assign("b", new Plus("a", 20));
		
		// s1 [0] s2
		Choose c = new Choose(s1, s2, 0);
		RankedIterator<VarStore> result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 21);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 22);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 23);
		assert(result.getRank() == 2);

		assert(result.next() == false);
		
		// s1 [1] s2
		c = new Choose(s1, s2, 1);
		result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 21);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 22);
		assert(result.getRank() == 2);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 23);
		assert(result.getRank() == 3);

		assert(result.next() == false);

		// s1 [2] s2
		c = new Choose(s1, s2, 2);
		result = c.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 21);
		assert(result.getRank() == 2);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 22);
		assert(result.getRank() == 3);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 23);
		assert(result.getRank() == 4);

		assert(result.next() == false);

		// s1 [3] s2
		c = new Choose(s1, s2, 3);
		result = c.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 21);
		assert(result.getRank() == 3);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 22);
		assert(result.getRank() == 4);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 23);
		assert(result.getRank() == 5);

		assert(result.next() == false);

		// skip left
		c = new Choose(new Skip(), s1, 3);
		result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 2);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 3);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 4);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 5);

		assert(result.next() == false);
		
		// skip right
		c = new Choose(s1, new Skip(), 3);
		result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 3);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 4);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 5);

		assert(result.next() == false);
		
	}
	
	public void testBlockedLeft() {
		DStatement s1 = new Assign("b", new Plus("a", 10));
		
		// {observe false [3] b = a + 10}
		Choose c = new Choose(new Observe(new BoolLiteral(false)), s1, 3);
		RankedIterator<VarStore> result = c.getIterator(getTestIterator());
		
		// Note: shifted down to 0
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);
		
		assert(result.next() == false);

	}

	public void testBlockedRight() {
		DStatement s1 = new Assign("b", new Plus("a", 10));
		
		// {observe b = a + 10 [3] false}
		Choose c = new Choose(s1, new Observe(new BoolLiteral(false)), 3);
		RankedIterator<VarStore> result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 13);
		assert(result.getRank() == 2);
		
		assert(result.next() == false);
		
	}

	public void testVariableRanks() {
		Choose c = new Choose(
				new Observe(new BoolLiteral(false)), 
				new Observe(new BoolLiteral(true)), 
				new Var("c"));
		RankedIterator<VarStore> result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		int value = result.getVarStore().getValue("c");
		assert(value == 0);
		int rank = result.getRank();
		assert(rank == 0);
		
		assert(result.next() == true);
		value = result.getVarStore().getValue("c");
		assert(value == 5);
		rank = result.getRank();
		assert(rank == 5 + 1);
		
		assert(result.next() == true);
		value = result.getVarStore().getValue("c");
		assert(value == 10);
		rank = result.getRank();
		assert(rank == 10 + 2);
		
		assert(result.next() == false);
		
		c = new Choose(
				new Observe(new BoolLiteral(false)), 
				new Observe(new BoolLiteral(true)), 
				new Var("b"));
		result = c.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 1);
		
		assert(result.next() == true);
		assert(result.getVarStore().getValue("b") == 5);
		assert(result.getRank() == 2);

		assert(result.next() == false);

	}
	
//	public testVariableRank() {
//
//		DStatement s1 = new Assign("b", new Plus("a", 10));
//		DStatement s2 = new Assign("b", new Plus("a", 20));
//		NumExpression e = new Var("a");
//		
//		// 0
//		Choose c = new Choose(s1, s2, 0);
//		
//		// > 0
//		c = new Choose(s1, s2, n);
//		
//		// skip left
//		c = new Choose(new Skip(), s1);
//		
//		// skip right
//		c = new Choose(s1, new Skip());
//
//	}
	
}
