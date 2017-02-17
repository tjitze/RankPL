package com.tr.rp.statement;

import java.util.Arrays;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.LessThan;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.tools.ResultPrinter;

public class ArraysTest extends RPLBaseTest {

	public void test1D() {

		DStatement s = new ProgramBuilder()
				.add(new IndexedAssign("x", new IntLiteral(0), new IntLiteral(5)))
				.add(new IndexedAssign("x", new IntLiteral(1), new IntLiteral(6)))
				.add(new IndexedAssign("x", new IntLiteral(2), new IntLiteral(7)))
				.add(new IndexedAssign("x", new IntLiteral(3), new IntLiteral(8)))
				.add(new IndexedAssign("x", new IntLiteral(4), new IntLiteral(9)))
				.build();

		VarStore vs = new VarStore();
		vs.initializeArray("x", 5);
		RankedIterator result = s.getIterator(new InitialVarStoreIterator(vs));
		
		assert(result.next() == true);
		vs = result.getVarStore();
		assert(result.getRank() == 0);
		assert(result.next() == false);
		
		int[] array = vs.get1DArray("x", 5);
		System.out.println(Arrays.toString(array));
		assert(array[0] == 5);
		assert(array[1] == 6);
		assert(array[2] == 7);
		assert(array[3] == 8);
		assert(array[4] == 9);
	}
}
