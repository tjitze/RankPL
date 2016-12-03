package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.LessThan;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.tools.ResultPrinter;

public class WhileTest extends RPLBaseTest {

	public void testWhile() {
		
		BoolExpression c = new LessThan("x", 3);
		DStatement s = new ProgramBuilder()
				.add(new Choose("y", new Plus("y", "x"), new Plus("y", 10), 1))
				.add(new Assign("x", new Plus("x", 1)))
				.build();
		While w = new While(c, s);
		RankedIterator<VarStore> result = w.getIterator(new InitialVarStoreIterator());
		//	x = 0	x = 1	x = 2	rank
		//	0		1		3		0
		//					11		1
		//			10		12		1
		//					20		2
		//	10		11		13		1
		//					21		2
		//			20		22		2
		//					30		3
		
		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 3);
		assert(result.getRank() == 0);
		
		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 11);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 12);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 13);
		assert(result.getRank() == 1);

		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 21);
		assert(result.getRank() == 2);

		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 20);
		assert(result.getRank() == 2);

		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 22);
		assert(result.getRank() == 2);

		assert(result.next() == true);
		assert(result.getItem().getValue("y") == 30);
		assert(result.getRank() == 3);

		assert(result.next() == false);
	}
}
