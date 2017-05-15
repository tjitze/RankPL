package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Variable;
import com.tr.rp.expressions.Len;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.NumNumBoolOp;
import com.tr.rp.expressions.Plus;
import com.tr.rp.expressions.SubString;

public class StringFunctionsTest extends RPLBaseTest {
	public void testStringFunctions() throws RPLException {

		DStatement p = new ProgramBuilder()
				.add(new Assign("x", new Literal<String>("abc")))
				.add(new Assign("y", new Literal<String>("def")))
				.add(new Assign("a", new Variable("x", new Literal<Integer>(0))))
				.add(new Assign("b", new Variable("x", new Literal<Integer>(1))))
				.add(new Assign("c", new Variable("x", new Literal<Integer>(2))))
				.add(new Assign("y", new Plus(new Variable("x"), new Variable("y"))))
				.add(new Assign("z", new Len(new Variable("y"))))
				.build();
		RankedIterator<VarStore> result = p.getIterator(new InitialVarStoreIterator());

		assert(result.next());
		VarStore vs = result.getItem();
		assert(!result.next());
		assertEquals("abc", vs.getValue("x"));
		assertEquals("abcdef", vs.getValue("y"));
		assertEquals(6, vs.getValue("z"));
		assertEquals("a", vs.getValue("a"));
		assertEquals("b", vs.getValue("b"));
		assertEquals("c", vs.getValue("c"));

	}

}
