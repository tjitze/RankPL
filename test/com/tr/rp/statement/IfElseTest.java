package com.tr.rp.statement;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;

public class IfElseTest extends RPLBaseTest {
	
	public void testIfElse() throws RPLException {
		// if (a == 1) then b = b + 10 else b = b + 20
		IfElse ie = new IfElse(eq(var("a"), lit(1)), 
				new Assign("b", plus(var("a"), lit(10))),
				new Assign("b", plus(var("a"), lit(20))));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());

		// if (a != 1) then b = b + 10 else b = b + 20
		ie = new IfElse(not(eq(var("a"), lit(1))), 
				new Assign("b", plus(var("a"), lit(10))),
				new Assign("b", plus(var("a"), lit(20))));
		result = ie.getIterator(getTestIterator());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());

	}
	
	public void testIfElseAlwaysThen() throws RPLException {
		// if (true) then b = b + 10 else b = b + 20
		IfElse ie = new IfElse(lit(true), 
				new Assign("b", plus(var("a"), lit(10))),
				new Assign("b", plus(var("a"), lit(20))));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());
	}

	public void testIfElseAlwaysElse() throws RPLException {
		// if (false) then b = b + 10 else b = b + 20
		IfElse ie = new IfElse(lit(false), 
				new Assign("b", plus(var("a"), lit(10))),
				new Assign("b", plus(var("a"), lit(20))));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());
	}

	public void testIfElseBlockedInput() throws RPLException {
		// Test if then else if input is absurd iterator
		IfElse ie = new IfElse(lit(false), 
				new Assign("b", plus(var("a"), lit(10))),
				new Assign("b", plus(var("a"), lit(20))));
		RankedIterator<VarStore> result = ie.getIterator(new AbsurdIterator<VarStore>());
		assertEquals(false, result.next());

		ie = new IfElse(lit(true), 
				new Assign("b", plus(var("a"), lit(10))),
				new Assign("b", plus(var("a"), lit(20))));
		result = ie.getIterator(new AbsurdIterator<VarStore>());
		assertEquals(false, result.next());
}
	
	public void testIfElseBlockedElse() throws RPLException {
		// if (a == 1) then b = a + 10 else observe false;
		IfElse ie = new IfElse(eq(var("a"),lit(1)), 
				new Assign("b", plus(var("a"), lit(10))),
				new Observe(lit(false)));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(false, result.next());

		// if (a != 1) then b = a + 10 else observe false;
		ie = new IfElse(not(eq(var("a"),lit(1))), 
				new Assign("b", plus(var("a"), lit(10))),
				new Observe(lit(false)));
		result = ie.getIterator(getTestIterator());

		// Note: ranks shifted down by 1
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(false, result.next());

	}
	
	public void testIfElseBlockedThen() throws RPLException {
		// if (a == 1) then observe false else b = a + 20
		IfElse ie = new IfElse(eq(var("a"), lit(1)), 
				new Observe(lit(false)),
				new Assign("b", plus(var("a"), lit(20))));
		RankedIterator<VarStore> result = ie.getIterator(getTestIterator());

		// Note: ranks shifted down by 1
		assertEquals(true, result.next());
		assertEquals(2, result.getItem().getIntValue("a"));
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(3, result.getItem().getIntValue("a"));
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(false, result.next());

		// if (a != 1) then observe false else b = a + 20
		ie = new IfElse(not(eq(var("a"),lit(1))), 
				new Observe(lit(false)),
				new Assign("b", plus(var("a"), lit(20))));
		result = ie.getIterator(getTestIterator());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("a"));
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(false, result.next());

	}
	
	public void testCorrectMerging() throws RPLException {
		
		// fx1 = 0 <5> fx1 = 1;
		// a1 = 0 <0> a1 = 1;
		// if (fx1 == 0) then skip else skip;
		// fx1 a rank
		// 0   0 0
		// 0   1 0
		// 1   0 5
		// 1   1 5
		
		DStatement p = new ProgramBuilder()
				.add(new RankedChoice(target("fx1"), lit(0), lit(1), lit(5)))
				.add(new RankedChoice(target("a1"), lit(0), lit(1), lit(0)))
				.add(new IfElse(eq(var("fx1"), lit(0)),
						new Skip(),
						new Skip()))
				.build();
		RankedIterator<VarStore> result = p.getIterator(new InitialVarStoreIterator());

		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("fx1"));
		assertEquals(0, result.getItem().getIntValue("a1"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(0, result.getItem().getIntValue("fx1"));
		assertEquals(1, result.getItem().getIntValue("a1"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("fx1"));
		assertEquals(0, result.getItem().getIntValue("a1"));
		assertEquals(5, result.getRank());

		assertEquals(true, result.next());
		assertEquals(1, result.getItem().getIntValue("fx1"));
		assertEquals(1, result.getItem().getIntValue("a1"));
		assertEquals(5, result.getRank());
	}
}
