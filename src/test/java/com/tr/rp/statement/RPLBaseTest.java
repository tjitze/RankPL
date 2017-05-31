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
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.parser.RankPLParser.ExpContext;

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
	
	
	protected static Object evalProgram(String program) {
        CharStream charStream = new ANTLRInputStream(program);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        ExpContext ctx = parser.exp();
        Expression res = (Expression)classVisitor.visit(ctx);
        
        
        return res;
	}

	
}
