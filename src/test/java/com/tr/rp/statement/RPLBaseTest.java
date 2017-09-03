package com.tr.rp.statement;

import java.util.LinkedList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.iterators.ranked.InitialVarStoreIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.parser.ConcreteParser;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.parser.RankPLParser.ExpContext;
import com.tr.rp.varstore.PMapVarStore;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentArray;

import junit.framework.TestCase;

public abstract class RPLBaseTest extends TestCase {

	protected static VarStore v1 = new PMapVarStore();
	protected static VarStore v2 = new PMapVarStore();
	protected static VarStore v3 = new PMapVarStore();

	static {
		v1 = v1.create("a", 1);
		v2 = v2.create("a", 2);
		v3 = v3.create("a", 3);
		v1 = v1.create("b", 5);
		v2 = v2.create("b", 5);
		v3 = v3.create("b", 5);
		v1 = v1.create("c", 0);
		v2 = v2.create("c", 5);
		v3 = v3.create("c", 10);
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
        AbstractExpression res = (AbstractExpression)classVisitor.visit(ctx);
        
        
        return res;
	}

	
}
