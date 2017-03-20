package com.tr.rp.core;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.parser.DefProgLexer;
import com.tr.rp.parser.DefProgParser;
import com.tr.rp.statement.Composition;

/**
 * A convenience class for constructing a program
 * consisting of a sequence of statements.
 */
public class ProgramBuilder {
	private DStatement a, b;
	public ProgramBuilder() {
		
	}
	public ProgramBuilder add(DStatement s) {
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
        DefProgLexer lexer = new DefProgLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        DefProgParser parser = new DefProgParser(tokens);
        ConcreteParser classVisitor = new ConcreteParser();
        return add((DStatement)classVisitor.visit(parser.statement()));
	}
	
	public DStatement build() {
		if (a == null) {
			throw new IllegalStateException();
		} else if (b == null) {
			return a;
		} else {
			return new Composition(a, b);
		}
	}
}

