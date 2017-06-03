package com.tr.rp.statement;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Not;

public class ObserveTest extends RPLBaseTest {

	public void testPartiallyTrue() throws RPLException {
		Observe obs = new Observe(eq(var("a"),lit(1))); 
		RankedIterator<VarStore> result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v1, result.getItem());
		assertEquals(0, result.getRank());
		assertEquals(false, result.next());

		obs = new Observe(eq(var("a"), lit(2))); 
		result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v2, result.getItem());
		assertEquals(0, result.getRank());
		assertEquals(false, result.next());

		obs = new Observe(eq(var("a"), lit(3))); 
		result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v3, result.getItem());
		assertEquals(0, result.getRank());
		assertEquals(false, result.next());

		obs = new Observe(new Not(eq(var("a"),lit(1)))); 
		result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v2, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(v3, result.getItem());
		assertEquals(1, result.getRank());

		assertEquals(false, result.next());

		obs = new Observe(new Not(eq(var("a"), lit(2)))); 
		result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v1, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(v3, result.getItem());
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());

		obs = new Observe(new Not(eq(var("a"), lit(3)))); 
		result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v1, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(v2, result.getItem());
		assertEquals(1, result.getRank());

		assertEquals(false, result.next());

	}
	
	public void testTrue() throws RPLException {
		Observe obs = new Observe(lit(true)); 
		RankedIterator<VarStore> result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, result.next());
		assertEquals(v1, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(v2, result.getItem());
		assertEquals(1, result.getRank());

		assertEquals(true, result.next());
		assertEquals(v3, result.getItem());
		assertEquals(2, result.getRank());

		assertEquals(false, result.next());
		
		Observe obs1 = new Observe(eq(var("a"),lit(1))); 
		Observe obs2 = new Observe(lit(true)); 
		Composition c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(v1, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(false, result.next());

		obs1 = new Observe(eq(var("a"), lit(2))); 
		obs2 = new Observe(lit(true)); 
		c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(v2, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(false, result.next());

		obs1 = new Observe(eq(var("a"), lit(3))); 
		obs2 = new Observe(lit(true)); 
		c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(v3, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(false, result.next());

		obs1 = new Observe(new Not(eq(var("a"),lit(1)))); 
		obs2 = new Observe(lit(true)); 
		c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());

		assertEquals(true, result.next());
		assertEquals(v2, result.getItem());
		assertEquals(0, result.getRank());

		assertEquals(true, result.next());
		assertEquals(v3, result.getItem());
		assertEquals(1, result.getRank());

		assertEquals(false, result.next());

	}
	
	public void testFalse() throws RPLException {
		Observe obs = new Observe(lit(false)); 
		RankedIterator<VarStore> result = obs.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(false, result.next());

		Observe obs1 = new Observe(eq(var("a"),lit(1))); 
		Observe obs2 = new Observe(lit(false)); 
		Composition c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator(), ExecutionContext.createDefault());
		
		assertEquals(false, result.next());

	}
	
	public void testEmptyInput() throws RPLException {
		Observe obs = new Observe(lit(true)); 
		RankedIterator<VarStore> result = obs.getIterator(new AbsurdIterator<VarStore>(), ExecutionContext.createDefault());
		
		assertEquals(false, result.next());
	}	

}
