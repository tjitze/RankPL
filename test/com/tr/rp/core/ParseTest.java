package com.tr.rp.core;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.parser.DefProgLexer;
import com.tr.rp.parser.DefProgParser;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.RPLBaseTest;

public class ParseTest extends RPLBaseTest {

	public void testParseVariable() {
		String program = "x := 0;";
		assert(parse(program).equals(new Assign("x",0)));
		program = "x1 := 0;";
		DStatement s = (DStatement) parse(program);
		assert(s.equals(new Assign("x1",0)));
	}
	
	public void testParseAssign() {
		String program = "x := 20;";
		assert(parse(program).equals(new Assign("x",20)));
	}

	public void testParseComposition() {
		String program = "x := 20; x := 30;";
		assert(parse(program).equals(new Composition(
				new Assign("x",20),
				new Assign("x",30))));
	}

	public void testParseIfElse() {
		String program = "IF (x == 10) THEN x := 20 ELSE x := 30;";
		assert(parse(program).equals(new IfElse(
				new Equals("x",10),
				new Assign("x",20),
				new Assign("x",30))));
	}

	public void testParseAssignChoose() {
		String program = "x := 0 << 5 >> 20;";
		assert(parse(program).equals(new Choose(
				new Assign("x",0), new Assign("x",20), 5)));
	}

	public void testParseChoose() {
		String program = "{ x := 0 } << 5 >> { x := 20 };";
		assert(parse(program).equals(new Choose(
				new Assign("x",0), new Assign("x",20), 5)));
	}

	public void testParseObserve() {
		String program = "OBSERVE (x == 10);";
		assert(parse(program).equals(new Observe(new Equals("x", 10))));
	}

	
	private LanguageElement parse(String code) {
        CharStream charStream = new ANTLRInputStream(code);
        DefProgLexer lexer = new DefProgLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        DefProgParser parser = new DefProgParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        return classVisitor.visit(parser.program());
	}
}
