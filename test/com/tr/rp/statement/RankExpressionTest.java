package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.Not;
import com.tr.rp.expressions.num.RankExpression;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.tools.ResultPrinter;

public class RankExpressionTest extends RPLBaseTest {

	public void testSimpleRankExpressions() {

		DStatement s = new Assign("c", new RankExpression(new Equals("a", 1)));
		RankedIterator result = s.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 0);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 0);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 0);
		assert(result.next() == false);

		s = new Assign("c", new RankExpression(new Not(new Equals("a", 1))));
		result = s.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 1);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 1);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 1);
		assert(result.next() == false);

		s = new Assign("c", new RankExpression(new Equals("a", 2)));
		result = s.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 1);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 1);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 1);
		assert(result.next() == false);

		s = new Assign("c", new RankExpression(new Equals("a", 3)));
		result = s.getIterator(getTestIterator());

		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 2);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 2);
		assert(result.next() == true);
		assert(result.getItem().getValue("c") == 2);
		assert(result.next() == false);

	}
}
