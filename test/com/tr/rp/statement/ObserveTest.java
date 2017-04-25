package com.tr.rp.statement;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.Not;

public class ObserveTest extends RPLBaseTest {

	public void testPartiallyTrue() {
		Observe obs = new Observe(new Equals("a",1)); 
		RankedIterator<VarStore> result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v1);
		assert(result.getRank() == 0);
		assert(result.next() == false);

		obs = new Observe(new Equals("a",2)); 
		result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v2);
		assert(result.getRank() == 0);
		assert(result.next() == false);

		obs = new Observe(new Equals("a",3)); 
		result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v3);
		assert(result.getRank() == 0);
		assert(result.next() == false);

		obs = new Observe(new Not(new Equals("a",1))); 
		result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v2);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getVarStore() == v3);
		assert(result.getRank() == 1);

		assert(result.next() == false);

		obs = new Observe(new Not(new Equals("a",2))); 
		result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v1);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getVarStore() == v3);
		assert(result.getRank() == 2);

		assert(result.next() == false);

		obs = new Observe(new Not(new Equals("a",3))); 
		result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v1);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getVarStore() == v2);
		assert(result.getRank() == 1);

		assert(result.next() == false);

	}
	
	public void testTrue() {
		Observe obs = new Observe(new BoolLiteral(true)); 
		RankedIterator<VarStore> result = obs.getIterator(getTestIterator());
		
		assert(result.next() == true);
		assert(result.getVarStore() == v1);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getVarStore() == v2);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getVarStore() == v3);
		assert(result.getRank() == 2);

		assert(result.next() == false);
		
		Observe obs1 = new Observe(new Equals("a",1)); 
		Observe obs2 = new Observe(new BoolLiteral(true)); 
		Composition c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore() == v1);
		assert(result.getRank() == 0);

		assert(result.next() == false);

		obs1 = new Observe(new Equals("a",2)); 
		obs2 = new Observe(new BoolLiteral(true)); 
		c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore() == v2);
		assert(result.getRank() == 0);

		assert(result.next() == false);

		obs1 = new Observe(new Equals("a",3)); 
		obs2 = new Observe(new BoolLiteral(true)); 
		c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore() == v3);
		assert(result.getRank() == 0);

		assert(result.next() == false);

		obs1 = new Observe(new Not(new Equals("a",1))); 
		obs2 = new Observe(new BoolLiteral(true)); 
		c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getVarStore() == v2);
		assert(result.getRank() == 0);

		assert(result.next() == true);
		assert(result.getVarStore() == v3);
		assert(result.getRank() == 1);

		assert(result.next() == false);

	}
	
	public void testFalse() {
		Observe obs = new Observe(new BoolLiteral(false)); 
		RankedIterator<VarStore> result = obs.getIterator(getTestIterator());
		
		assert(result.next() == false);

		Observe obs1 = new Observe(new Equals("a",1)); 
		Observe obs2 = new Observe(new BoolLiteral(false)); 
		Composition c = new Composition(obs1, obs2);
		result = c.getIterator(getTestIterator());
		
		assert(result.next() == false);

	}
	
	public void testEmptyInput() {
		Observe obs = new Observe(new BoolLiteral(true)); 
		RankedIterator<VarStore> result = obs.getIterator(new AbsurdIterator<VarStore>());
		
		assert(result.next() == false);
	}	

}
