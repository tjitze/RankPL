package com.tr.rp.ast;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.ast.statements.Composition;
import com.tr.rp.parser.ConcreteParser;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;

/**
 * A convenience class for constructing a program
 * consisting of a sequence of statements.
 */
public class ProgramBuilder {

	private AbstractStatement a, b;
	
	public ProgramBuilder add(AbstractStatement s) {
		if (a == null) {
			a = s;
		} else if (b == null) {
			b = s;
		} else {
			a = new Composition(a, b);
			b = s;
		}
		return this;
	}
	
	public ProgramBuilder add(String statement) {
        CharStream charStream = new ANTLRInputStream(statement);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);
        ConcreteParser classVisitor = new ConcreteParser();
        return add((AbstractStatement)classVisitor.visit(parser.program()));
	}
	
	public AbstractStatement build() {
		if (a == null) {
			throw new IllegalStateException();
		} else if (b == null) {
			return a;
		} else {
			return new Composition(a, b);
		}
	}
}

