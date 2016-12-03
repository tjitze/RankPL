package com.tr.rp.statement;

import java.util.LinkedList;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.Not;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.tools.ResultPrinter;

import junit.framework.TestCase;

public class IfElseTest extends RPLBaseTest {
	
	public void testIfElse() {
		// if (a == 1) then b = b + 10 else b = b + 20
		IfElse ie = new IfElse(new Equals("a",1), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Assign("b", new Plus(new Var("a"), 20)));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getItem().getValue("b") == 11);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getItem().getValue("b") == 22);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getItem().getValue("b") == 23);
		assert(result.getRank() == 2);

		assert(result.next() == false);

		// if (a != 1) then b = b + 10 else b = b + 20
		ie = new IfElse(new Not(new Equals("a",1)), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Assign("b", new Plus(new Var("a"), 20)));
		result = ie.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getItem().getValue("b") == 21);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getItem().getValue("b") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getItem().getValue("b") == 13);
		assert(result.getRank() == 2);

		assert(result.next() == false);

	}
	
	public void testIfElseAlwaysThen() {
		// if (true) then b = b + 10 else b = b + 20
		IfElse ie = new IfElse(new BoolLiteral(true), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Assign("b", new Plus(new Var("a"), 20)));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getItem().getValue("b") == 11);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getItem().getValue("b") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getItem().getValue("b") == 13);
		assert(result.getRank() == 2);

		assert(result.next() == false);
	}

	public void testIfElseAlwaysElse() {
		// if (false) then b = b + 10 else b = b + 20
		IfElse ie = new IfElse(new BoolLiteral(false), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Assign("b", new Plus(new Var("a"), 20)));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getItem().getValue("b") == 21);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getItem().getValue("b") == 22);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getItem().getValue("b") == 23);
		assert(result.getRank() == 2);

		assert(result.next() == false);
	}

	public void testIfElseBlockedInput() {
		// Test if then else if input is absurd iterator
		IfElse ie = new IfElse(new BoolLiteral(false), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Assign("b", new Plus(new Var("a"), 20)));
		RankedIterator<VarStore> result = ie.getIterator(new AbsurdIterator());
		assert(result.next() == false);

		ie = new IfElse(new BoolLiteral(true), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Assign("b", new Plus(new Var("a"), 20)));
		result = ie.getIterator(new AbsurdIterator());
		assert(result.next() == false);
}
	
	public void testIfElseBlockedElse() {
		// if (a == 1) then b = a + 10 else observe false;
		IfElse ie = new IfElse(new Equals("a",1), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Observe(new BoolLiteral(false)));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getItem().getValue("b") == 11);
		assert(result.getRank() == 0);
		
		assert(result.next() == false);

		// if (a != 1) then b = a + 10 else observe false;
		ie = new IfElse(new Not(new Equals("a",1)), 
				new Assign("b", new Plus(new Var("a"), 10)),
				new Observe(new BoolLiteral(false)));
		result = ie.getIterator(getTestIterator());

		// Note: ranks shifted down by 1
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getItem().getValue("b") == 12);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getItem().getValue("b") == 13);
		assert(result.getRank() == 1);

		assert(result.next() == false);

	}
	
	public void testIfElseBlockedThen() {
		// if (a == 1) then observe false else b = a + 20
		IfElse ie = new IfElse(new Equals("a",1), 
				new Observe(new BoolLiteral(false)),
				new Assign("b", new Plus(new Var("a"), 20)));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		// Note: ranks shifted down by 1
		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 2);
		assert(result.getItem().getValue("b") == 22);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 3);
		assert(result.getItem().getValue("b") == 23);
		assert(result.getRank() == 1);

		assert(result.next() == false);

		// if (a != 1) then observe false else b = a + 20
		ie = new IfElse(new Not(new Equals("a",1)), 
				new Observe(new BoolLiteral(false)),
				new Assign("b", new Plus(new Var("a"), 20)));
		result = ie.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("a") == 1);
		assert(result.getItem().getValue("b") == 21);
		assert(result.getRank() == 0);

		assert(result.next() == false);

	}
}
