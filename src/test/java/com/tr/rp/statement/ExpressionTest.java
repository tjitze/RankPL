package com.tr.rp.statement;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.core.ConcreteParser;
import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.expressions.Len;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.PersistentList;
import com.tr.rp.expressions.Plus;
import com.tr.rp.expressions.Variable;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.parser.RankPLParser.ExpContext;

public class ExpressionTest extends RPLBaseTest {

	private Object evalExp(String expression) throws RPLException {
        CharStream charStream = new ANTLRInputStream(expression);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        ExpContext ctx = parser.exp();
        Expression exp = (Expression)classVisitor.visit(ctx);
        assertNotNull(exp);

        DStatement st = new Assign("$return", exp);
        RankedIterator<VarStore> it = st.getIterator(new InitialVarStoreIterator(new TestVarStore()), ExecutionContext.createDefault());
        assert (it.next());
        VarStore res = it.getItem();
        assert (res.containsVar("$return"));
        assert (!it.next());
        return res.getValue("$return");
	}


	private void expectTypeError(String expression, String expectedType) {
		expectTypeError(expression, expectedType, null);
	}
	
	private void expectTypeError(String expression, String expectedType, String foundType) {
		try {
			evalExp(expression);
			fail("Expected type error");
		} catch (RPLTypeError e) {
			assertEquals(expectedType, e.getExpectedType());
			if (foundType != null) {
				assertEquals(foundType, e.getFound());
			}
		} catch (RPLException e) {
			fail("Incorrect exception thrown (expected type error): " + e);
		}
	}

	public void testExpressions() throws RPLException {
		
		// Literals and variables
		assertEquals(100, evalExp("100"));
		assertEquals(-100, evalExp("-100"));
		assertEquals(0, evalExp("0"));
		assertEquals(0, evalExp("-0"));
		assertEquals(true, evalExp("t"));
		assertEquals(true, evalExp("true"));
		assertEquals(false, evalExp("f"));
		assertEquals(false, evalExp("false"));
		assertEquals("s", evalExp("s"));
		assertEquals("u", evalExp("u"));
		assertEquals("s", evalExp("\"s\""));
		assertEquals("u", evalExp("\"u\""));

		// abs
		assertEquals(100, evalExp("abs(100)"));
		assertEquals(100, evalExp("abs(-100)"));
		assertEquals(0, evalExp("abs(0)"));
		assertEquals(100, evalExp("abs(i100)"));
		assertEquals(100, evalExp("abs(-i100)"));
		assertEquals(0, evalExp("abs(i0)"));
		expectTypeError("abs(true)", "integer", "true");
		expectTypeError("abs(false)", "integer", "false");
		expectTypeError("abs(t)", "integer", "true");
		expectTypeError("abs(f)", "integer", "false");
		expectTypeError("abs(s)", "integer", "s");
		expectTypeError("abs(ai)", "integer");
		
		// array constructor
		assertEquals(new PersistentList(null, null), evalExp("array(2)"));
		assertEquals(new PersistentList(0, 0), evalExp("array(2, 0)"));
		assertEquals(new PersistentList(new PersistentList(null, null), new PersistentList(null, null)), evalExp("array(2, array(2))"));
		assertEquals(new PersistentList(new PersistentList(0, 0), new PersistentList(0, 0)), evalExp("array(2, array(2, 0))"));
		assertEquals(new PersistentList("s", "s"), evalExp("array(2, s)"));
		assertEquals(new PersistentList("s", "s"), evalExp("array(2, \"s\")"));
		assertEquals(new PersistentList(true, true), evalExp("array(2, t)"));
		assertEquals(new PersistentList(true, true), evalExp("array(2, true)"));
		assertEquals(new PersistentList(new PersistentList(0, 0)), evalExp("array(1, array(2, 0))"));
		// TODO: error testing (negative index, type error) 

		// array literal
//		assertEquals(new PersistentList(0, 1), evalExp("[0, 1]"));
//		assertEquals(new PersistentList(new PersistentList(0, 1), new PersistentList(2, 3)), 
//				evalExp("[[0, 1], [2, 3]]"));
//		assertEquals(new PersistentList(0, 1), evalExp("[i0, i1]"));
//		assertEquals(new PersistentList(new PersistentList(0, 1), new PersistentList(2, 3)), 
//				evalExp("[[i0, i1], [i2, i3]]"));
//		assertEquals(new PersistentList("s", "u"), evalExp("[\"s\", \"u\"]"));
//		assertEquals(new PersistentList(new PersistentList("s", "u"), new PersistentList("ss", "uu")), 
//				evalExp("[[\"s\", \"u\"], [\"ss\", \"uu\"]]"));
//		assertEquals(new PersistentList("s", "u"), evalExp("[s, u]"));
//		assertEquals(new PersistentList(new PersistentList("s", "u"), new PersistentList("ss", "uu")), 
//				evalExp("[[s, u], [ss, uu]]"));
//		assertEquals(new PersistentList(true, false), evalExp("[true, false]"));
//		assertEquals(new PersistentList(new PersistentList(true, false), new PersistentList(false, true)), 
//				evalExp("[[true, false], [false, true]]"));
//		assertEquals(new PersistentList(true, false), evalExp("[t, f]"));
//		assertEquals(new PersistentList(new PersistentList(true, false), new PersistentList(false, true)), 
//				evalExp("[[t, f], [f, t]]"));

		// boolean AND
		assertEquals(true, evalExp("true & true"));
		assertEquals(false, evalExp("false & true"));
		assertEquals(false, evalExp("true & false"));
		assertEquals(false, evalExp("false & false"));
		assertEquals(true, evalExp("t & t"));
		assertEquals(false, evalExp("f & t"));
		assertEquals(false, evalExp("t & f"));
		assertEquals(false, evalExp("f & f"));
		expectTypeError("0 & f", "boolean", "0");
		expectTypeError("t & 0", "boolean", "0");
		expectTypeError("s & f", "boolean", "s");
		expectTypeError("t & s", "boolean", "s");

		// boolean OR
		assertEquals(true, evalExp("true | true"));
		assertEquals(true, evalExp("false | true"));
		assertEquals(true, evalExp("true | false"));
		assertEquals(false, evalExp("false | false"));
		assertEquals(true, evalExp("t | t"));
		assertEquals(true, evalExp("f | t"));
		assertEquals(true, evalExp("t | f"));
		assertEquals(false, evalExp("f | f"));
		expectTypeError("0 | t", "boolean", "0");
		expectTypeError("f | 0", "boolean", "0");
		expectTypeError("s | t", "boolean", "s");
		expectTypeError("f | s", "boolean", "s");

		// boolean XOR
		assertEquals(false, evalExp("true ^ true"));
		assertEquals(true, evalExp("false ^ true"));
		assertEquals(true, evalExp("true ^ false"));
		assertEquals(false, evalExp("false ^ false"));
		assertEquals(false, evalExp("t ^ t"));
		assertEquals(true, evalExp("f ^ t"));
		assertEquals(true, evalExp("t ^ f"));
		assertEquals(false, evalExp("f ^ f"));
		expectTypeError("0 ^ t", "boolean", "0");
		expectTypeError("f ^ 0", "boolean", "0");
		expectTypeError("s ^ t", "boolean", "s");
		expectTypeError("f ^ s", "boolean", "s");

		// boolean NOT
		assertEquals(false, evalExp("!true"));
		assertEquals(true, evalExp("!false"));
		assertEquals(false, evalExp("!t"));
		assertEquals(true, evalExp("!f"));
		expectTypeError("!0", "boolean", "0");

		// Equality
		assertEquals(true, evalExp("1 == 1"));
		assertEquals(true, evalExp("1 == i1"));
		assertEquals(true, evalExp("i1 == 1"));
		assertEquals(true, evalExp("i1 == i1"));
		assertEquals(false, evalExp("1 == 2"));
		assertEquals(false, evalExp("1 == i2"));
		assertEquals(false, evalExp("i1 == 2"));
		assertEquals(false, evalExp("i1 == i2"));
		assertEquals(true, evalExp("\"s\" == \"s\""));
		assertEquals(true, evalExp("\"s\" == s"));
		assertEquals(true, evalExp("s == \"s\""));
		assertEquals(true, evalExp("\"s\" == \"s\""));
		assertEquals(false, evalExp("\"s\" == \"u\""));
		assertEquals(false, evalExp("\"s\" == u"));
		assertEquals(false, evalExp("s == \"u\""));
		assertEquals(false, evalExp("\"s\" == \"u\""));
		assertEquals(true, evalExp("true == true"));
		assertEquals(true, evalExp("true == t"));
		assertEquals(true, evalExp("t == true"));
		assertEquals(true, evalExp("true == true"));
		assertEquals(false, evalExp("t == f"));
		assertEquals(false, evalExp("true == f"));
		assertEquals(false, evalExp("t == false"));
		assertEquals(false, evalExp("true == false"));
		assertEquals(true, evalExp("[0, 1] == [0, 1]"));
		assertEquals(false, evalExp("[0, 1] == [0]"));
		assertEquals(true, evalExp("[[0,1], [2,3]] == [[0,1], [2,3]]"));
		assertEquals(false, evalExp("[[0,1], [2,3]] == [[0,1]]"));
		assertEquals(false, evalExp("[[0,1], [2,3]] == [[2,3], [1,2]]"));
		// TOOD: test type error/mismatch
		
		// Inequality
		assertEquals(false, evalExp("1 != 1"));
		assertEquals(false, evalExp("1 != i1"));
		assertEquals(false, evalExp("i1 != 1"));
		assertEquals(false, evalExp("i1 != i1"));
		assertEquals(true, evalExp("1 != 2"));
		assertEquals(true, evalExp("1 != i2"));
		assertEquals(true, evalExp("i1 != 2"));
		assertEquals(true, evalExp("i1 != i2"));
		assertEquals(false, evalExp("\"s\" != \"s\""));
		assertEquals(false, evalExp("\"s\" != s"));
		assertEquals(false, evalExp("s != \"s\""));
		assertEquals(false, evalExp("\"s\" != \"s\""));
		assertEquals(true, evalExp("\"s\" != \"u\""));
		assertEquals(true, evalExp("\"s\" != u"));
		assertEquals(true, evalExp("s != \"u\""));
		assertEquals(true, evalExp("\"s\" != \"u\""));
		assertEquals(false, evalExp("true != true"));
		assertEquals(false, evalExp("true != t"));
		assertEquals(false, evalExp("t != true"));
		assertEquals(false, evalExp("true != true"));
		assertEquals(true, evalExp("t != f"));
		assertEquals(true, evalExp("true != f"));
		assertEquals(true, evalExp("t != false"));
		assertEquals(true, evalExp("true != false"));
		assertEquals(false, evalExp("[0, 1] != [0, 1]"));
		assertEquals(true, evalExp("[0, 1] != [0]"));
		assertEquals(false, evalExp("[[0,1], [2,3]] != [[0,1], [2,3]]"));
		assertEquals(true, evalExp("[[0,1], [2,3]] != [[0,1]]"));
		assertEquals(true, evalExp("[[0,1], [2,3]] != [[2,3], [1,2]]"));
		// TOOD: test type error/mismatch

		// Array element
		assertEquals(new PersistentList(0,1), evalExp("aii[1]"));
		assertEquals(new PersistentList(0,1,2), evalExp("aii[2]"));
		assertEquals(new PersistentList(0,1,2,3), evalExp("aii[3]"));
		assertEquals(0, evalExp("aii[1][0]"));
		assertEquals(1, evalExp("aii[1][1]"));
		// TODO: test index out of bounds, type error
		
		// String element
		assertEquals("a", evalExp("abcde[0]"));
		assertEquals("b", evalExp("abcde[1]"));
		assertEquals("c", evalExp("abcde[2]"));
		assertEquals("d", evalExp("abcde[3]"));
		assertEquals("e", evalExp("abcde[4]"));
		// TODO: index out of bounds
		
		assertEquals("a", evalExp("\"abcde\"[0]"));
		assertEquals("b", evalExp("\"abcde\"[1]"));
		assertEquals("c", evalExp("\"abcde\"[2]"));
		assertEquals("d", evalExp("\"abcde\"[3]"));
		assertEquals("e", evalExp("\"abcde\"[4]"));
		// TODO: index out of bounds
		
		// isset
		assertEquals(true, evalExp("isset(s)"));
		assertEquals(true, evalExp("isset(t)"));
		assertEquals(true, evalExp("isset(i0)"));
		assertEquals(true, evalExp("isset(ai)"));
		assertEquals(true, evalExp("isset(ai[0])"));
		assertEquals(false, evalExp("isset(x)"));
		
		// len
		assertEquals(0, evalExp("len(\"\")"));
		assertEquals(1, evalExp("len(s)"));
		assertEquals(2, evalExp("len(ss)"));
		assertEquals(0, evalExp("len(array(0))"));
		assertEquals(10, evalExp("len(array(10))"));
		assertEquals(2, evalExp("len(aii[1])"));
		assertEquals(3, evalExp("len(aii[2])"));
		
		// Comparators
		assertEquals(true, evalExp("1 < 2"));
		assertEquals(false, evalExp("2 < 2"));
		assertEquals(false, evalExp("3 < 2"));
		assertEquals(true, evalExp("1 <= 2"));
		assertEquals(true, evalExp("2 <= 2"));
		assertEquals(false, evalExp("3 <= 2"));
		assertEquals(false, evalExp("1 > 2"));
		assertEquals(false, evalExp("2 > 2"));
		assertEquals(true, evalExp("3 > 2"));
		assertEquals(false, evalExp("1 >= 2"));
		assertEquals(true, evalExp("2 >= 2"));
		assertEquals(true, evalExp("3 >= 2"));
		// TODO: type checking

		// Plus
		assertEquals(3, evalExp("1 + 2"));
		assertEquals(-1, evalExp("1 + -2"));
		assertEquals(-3, evalExp("-1 + -2"));
		assertEquals("s1", evalExp("\"s\" + 1"));
		assertEquals("1s", evalExp("1 + \"s\""));
		assertEquals("strue", evalExp("\"s\" + true"));
		assertEquals("falses", evalExp("false + \"s\""));
		assertEquals("su", evalExp("\"s\" + \"u\""));
		assertEquals("su", evalExp("s + u"));

		// Other numerical ops
		assertEquals(-1, evalExp("1 - 2"));
		assertEquals(3, evalExp("1 - -2"));
		assertEquals(1, evalExp("-1 - -2"));
		assertEquals(6, evalExp("2 * 3"));
		assertEquals(-6, evalExp("2 * -3"));
		assertEquals(6, evalExp("-2 * -3"));
		assertEquals(3, evalExp("10 / 3"));
		assertEquals(-3, evalExp("10 / -3"));
		assertEquals(3, evalExp("-10 / -3"));
		assertEquals(1, evalExp("10 % 3"));
		assertEquals(1, evalExp("10 % -3"));
		assertEquals(-1, evalExp("-10 % -3"));
		// TODO: check overflow behavior
		
		// substring
		assertEquals("abcde", evalExp("substring(\"abcde\", 0, 5)"));
		assertEquals("abcde", evalExp("substring(abcde, 0, 5)"));
		assertEquals("bcde", evalExp("substring(\"abcde\", 1, 5)"));
		assertEquals("bcde", evalExp("substring(abcde, 1, 5)"));
		assertEquals("bcd", evalExp("substring(\"abcde\", 1, 4)"));
		assertEquals("bcd", evalExp("substring(abcde, 1, 4)"));
		assertEquals("abcd", evalExp("substring(\"abcde\", 0, 4)"));
		assertEquals("abcd", evalExp("substring(abcde, 0, 4)"));
		// TODO: check index out of bounds
		
		// conditional
		assertEquals(1, evalExp("true?1:2"));
		assertEquals(2, evalExp("false?1:2"));
		// TODO: type error
		
//		rank(b)		Rank of boolean expression b***
//		functionname(e_1, … e_n)	Function call****

	}

	/**
	 * Variable store for testing the following variables are set:
	 * 
	 * i0 ... in: integer values
	 * s, ss, sss, ssss : strings "s", "ss", "sss", "ssss"
	 * u, uu, uuu, uuuu : strings "u", "uu", "uuu", "uuuu"
	 * abcde : "abcde"
	 * t : true
	 * f : false
	 * ai : array [0, 1, ... 4]
	 * aii : array [[0], [0, 1], ... [0, ..., 4]]
	 * as : array ["s", "ss", "sss", "ssss"]
	 * au : array ["u", "uu", "uuu", "uuuu"]
	 * ab : array [true, false]
	 */
	private static class TestVarStore extends VarStore {

		public TestVarStore() {
			for (int i = 0; i < 200; i++) {
				setValue("i"+i, i);
			}
			setValue("s", "s");
			setValue("ss", "ss");
			setValue("sss", "sss");
			setValue("ssss", "ssss");
			setValue("u", "u");
			setValue("uu", "uu");
			setValue("uuu", "uuu");
			setValue("uuuu", "uuuu");
			setValue("abcde", "abcde");
			setValue("t", true);
			setValue("f", false);
			setValue("f", false);
			PersistentList ai = new PersistentList(10);
			for (int i = 0; i < 5; i++) {
				ai = ai.getMutatedCopy(i, i);
			}
			setValue("ai", ai);
			PersistentList aii = new PersistentList(10);
			for (int i = 0; i < 5; i++) {
				ai = new PersistentList(i + 1);
				for (int j = 0; j < i + 1; j++) {
					ai = ai.getMutatedCopy(j, j);
				}
				aii = aii.getMutatedCopy(i, ai);
			}
			setValue("aii", aii);
			PersistentList as = new PersistentList(4);
			as = as.getMutatedCopy(0, "s");
			as = as.getMutatedCopy(0, "ss");
			as = as.getMutatedCopy(0, "sss");
			as = as.getMutatedCopy(0, "ssss");
			setValue("as", as);
			PersistentList au = new PersistentList(4);
			au = au.getMutatedCopy(0, "u");
			au = au.getMutatedCopy(0, "uu");
			au = au.getMutatedCopy(0, "uuu");
			au = au.getMutatedCopy(0, "uuuu");
			setValue("au", au);
			setValue("t", true);
			setValue("f", false);
		}
	}

}
