package com.tr.rp.statement;

import java.util.LinkedList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.core.ConcreteParser;
import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.expressions.PersistentList;
import com.tr.rp.parser.DefProgLexer;
import com.tr.rp.parser.DefProgParser;
import com.tr.rp.parser.DefProgParser.ExpressionContext;

import junit.framework.TestCase;

public abstract class RPLBaseTest extends TestCase {

	protected static final VarStore v1 = new VarStore();
	protected static final VarStore v2 = new VarStore();
	protected static final VarStore v3 = new VarStore();

	static {
		v1.setValue("a", 1);
		v2.setValue("a", 2);
		v3.setValue("a", 3);
		v1.setValue("b", 5);
		v2.setValue("b", 5);
		v3.setValue("b", 5);
		v1.setValue("c", 0);
		v2.setValue("c", 5);
		v3.setValue("c", 10);
	}

	protected RankedIterator<VarStore> getTestIterator() {
		final LinkedList<VarStore> list = new LinkedList<VarStore>() {{
			addLast(v1);
			addLast(v2);
			addLast(v3);
		}};
		return new RankedIterator<VarStore>() {

			private int c = -1;
			
			@Override
			public boolean next() throws RPLException {
				c++;
				return c < list.size();
			}

			@Override
			public VarStore getItem() throws RPLException {
				if (c < 0 || c >= list.size()) return null;
				return list.get(c);
			}

			@Override
			public int getRank() {
				return c;
			}
		};
	}

	protected void iteratorsEqual(RankedIterator<VarStore> a, RankedIterator<VarStore> b) throws RPLException {
		boolean aNext = a.next();
		boolean bNext = b.next();
		assert(aNext == bNext);
		boolean done = aNext;
		while (!done) {
			assert(a.getRank() == b.getRank());
			if (!a.getItem().equals(b.getItem())) {
				System.out.println(a);
				System.out.println(b);
			}
			assert(a.getItem().equals(b.getItem()));
			aNext = a.next();
			bNext = b.next();
			assert(aNext == bNext);
			if (!aNext) done = true;
		}		
	}
	
	/**
	 * Evaluate expression. The following variables are set and can be
	 * used to construct test expressions:
	 * 
	 * i0 ... in: integer values
	 * s, ss, sss, ssss : strings "s", "ss", "sss", "ssss"
	 * u, uu, uuu, uuuu : strings "u", "uu", "uuu", "uuuu"
	 * t : true
	 * f : false
	 * ai : array [0, 1, ... 4]
	 * aii : array [[0], [0, 1], ... [0, ..., 4]]
	 * as : array ["s", "ss", "sss", "ssss"]
	 * au : array ["u", "uu", "uuu", "uuuu"]
	 * ab : array [true, false]
	 * 
	 * @param expression
	 * @return
	 * @throws RPLException 
	 */
	protected Object evalExp(String expression) throws RPLException {
        CharStream charStream = new ANTLRInputStream(expression);
        DefProgLexer lexer = new DefProgLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        DefProgParser parser = new DefProgParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        ExpressionContext ctx = parser.expression();
        Expression exp = (Expression)classVisitor.visit(ctx);
        assertNotNull(exp);
        VarStore v = getTestVarStore();
        DStatement st = new Assign("$return", exp);
        RankedIterator<VarStore> it = st.getIterator(new InitialVarStoreIterator(v));
        assert (it.next());
        VarStore res = it.getItem();
        assert (res.containsVar("$return"));
        assert (!it.next());
        return res.getValue("$return");
	}
	
	protected static Object evalProgram(String program) {
        CharStream charStream = new ANTLRInputStream(program);
        DefProgLexer lexer = new DefProgLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        DefProgParser parser = new DefProgParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        ExpressionContext ctx = parser.expression();
        Expression res = (Expression)classVisitor.visit(ctx);
        
        
        return res;
	}

	protected static VarStore getTestVarStore() {
		VarStore v = new VarStore();
		for (int i = 0; i < 200; i++) {
			v.setValue("i"+i, i);
		}
		v.setValue("s", "s");
		v.setValue("ss", "ss");
		v.setValue("sss", "sss");
		v.setValue("ssss", "ssss");
		v.setValue("u", "u");
		v.setValue("uu", "uu");
		v.setValue("uuu", "uuu");
		v.setValue("uuuu", "uuuu");
		v.setValue("t", true);
		v.setValue("f", false);
		v.setValue("f", false);
		PersistentList ai = new PersistentList(10);
		for (int i = 0; i < 5; i++) {
			ai = ai.getMutatedCopy(i, i);
		}
		v.setValue("ai", ai);
		PersistentList aii = new PersistentList(10);
		for (int i = 0; i < 5; i++) {
			ai = new PersistentList(i + 1);
			for (int j = 0; j < i + 1; j++) {
				ai = ai.getMutatedCopy(j, j);
			}
			aii = aii.getMutatedCopy(i, ai);
		}
		v.setValue("aii", aii);
		PersistentList as = new PersistentList(4);
		as = as.getMutatedCopy(0, "s");
		as = as.getMutatedCopy(0, "ss");
		as = as.getMutatedCopy(0, "sss");
		as = as.getMutatedCopy(0, "ssss");
		v.setValue("as", as);
		PersistentList au = new PersistentList(4);
		au = au.getMutatedCopy(0, "u");
		au = au.getMutatedCopy(0, "uu");
		au = au.getMutatedCopy(0, "uuu");
		au = au.getMutatedCopy(0, "uuuu");
		v.setValue("au", au);
		v.setValue("t", true);
		v.setValue("f", false);
		return v;
	}
	
	protected void expectTypeError(String expression, String expectedType) {
		expectTypeError(expression, expectedType, null);
	}
	protected void expectTypeError(String expression, String expectedType, String foundType) {
		try {
			Object res = evalExp(expression);
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

	
}
