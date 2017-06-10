package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.Assign;
import com.tr.rp.ast.statements.Observe;
import com.tr.rp.ast.statements.RankedChoice;
import com.tr.rp.ast.statements.Skip;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

public class RankedChoiceTest extends RPLBaseTest {
	
	public void testDefaultChoice() throws RPLException {
		AbstractStatement s1 = new Assign("b", plus(var("a"), new Literal<Integer>(10)));
		AbstractStatement s2 = new Assign("b", plus(var("a"), new Literal<Integer>(20)));
		
		// s1 [0] s2
		RankedChoice c = new RankedChoice(s1, s2, lit(0));
		RankedIterator<VarStore> result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());
		
		// s1 [1] s2
		c = new RankedChoice(s1, s2, lit(1));
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(3, result.getRank());

		assertEquals(false, result.next());

		// s1 [2] s2
		c = new RankedChoice(s1, s2, lit(2));
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(3, result.getRank());

		assertEquals(true, result.next());
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(4, result.getRank());

		assertEquals(false, result.next());

		// s1 [3] s2
		c = new RankedChoice(s1, s2, lit(3));
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(true, result.next());
		assertEquals(21, result.getItem().getIntValue("b"));
		assertEquals(3, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(22, result.getItem().getIntValue("b"));
		assertEquals(4, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(23, result.getItem().getIntValue("b"));
		assertEquals(5, result.getRank());

		assertEquals(false, result.next());

		// skip left
		c = new RankedChoice(new Skip(), s1, lit(3));
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(3, result.getRank());

		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(4, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(5, result.getRank());

		assertEquals(false, result.next());
		
		// skip right
		c = new RankedChoice(s1, new Skip(), lit(3));
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(3, result.getRank());

		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(4, result.getRank());

		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(5, result.getRank());

		assertEquals(false, result.next());
		
	}
	
	public void testBlockedLeft() throws RPLException {
		AbstractStatement s1 = new Assign("b", plus(var("a"), new Literal<Integer>(10)));
		
		// {observe false [3] b = a + 10}
		RankedChoice c = new RankedChoice(new Observe(new Literal<Boolean>(false)), s1, lit(3));
		RankedIterator<VarStore> result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		// Note: shifted down to 0
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(false, result.next());

	}

	public void testBlockedRight() throws RPLException {
		AbstractStatement s1 = new Assign("b", plus(var("a"), new Literal<Integer>(10)));
		
		// {observe b = a + 10 [3] false}
		RankedChoice c = new RankedChoice(s1, new Observe(new Literal<Boolean>(false)), lit(3));
		RankedIterator<VarStore> result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(11, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(12, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(13, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());
		
		assertEquals(false, result.next());
		
	}

	public void testVariableRanks() throws RPLException {
		RankedChoice c = new RankedChoice(
				new Observe(new Literal<Boolean>(false)), 
				new Observe(new Literal<Boolean>(true)), 
				new Variable("c"));
		RankedIterator<VarStore> result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		int value = result.getItem().getIntValue("c");
		assertEquals(0, value);
		int rank = result.getRank();
		assertEquals(0, rank);
		
		assertEquals(true, result.next());
		value = result.getItem().getIntValue("c");
		assertEquals(5, value);
		rank = result.getRank();
		assertEquals(5 + 1, rank);
		
		assertEquals(true, result.next());
		value = result.getItem().getIntValue("c");
		assertEquals(10, value);
		rank = result.getRank();
		assertEquals(10 + 2, rank);
		
		assertEquals(false, result.next());
		
		c = new RankedChoice(
				new Observe(new Literal<Boolean>(false)), 
				new Observe(new Literal<Boolean>(true)), 
				new Variable("b"));
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(0, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(1, result.getRank());
		
		assertEquals(true, result.next());
		assertEquals(5, result.getItem().getIntValue("b"));
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());

	}
	
//	public testVariableRank() {
//
//		DStatement s1 = new Assign("b", new Plus(var("a"), 10));
//		DStatement s2 = new Assign("b", new Plus(var("a"), 20));
//		NumExpression e = new Var(var("a"));
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
