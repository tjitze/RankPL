package com.tr.rp.core;

import static com.tr.rp.ast.expressions.Expressions.*;

import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.Function;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.ProgramBuilder;
import com.tr.rp.ast.expressions.ArrayConstructExpression;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.expressions.RankExpr;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.Assign;
import com.tr.rp.ast.statements.Composition;
import com.tr.rp.ast.statements.Dec;
import com.tr.rp.ast.statements.FunctionCallForm;
import com.tr.rp.ast.statements.IfElse;
import com.tr.rp.ast.statements.Inc;
import com.tr.rp.ast.statements.Observe;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.ast.statements.RankedChoice;
import com.tr.rp.ast.statements.Return;
import com.tr.rp.ast.statements.Skip;
import com.tr.rp.ast.statements.While;
import com.tr.rp.parser.ConcreteParser;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.parser.RankPLParser.ExpContext;
import com.tr.rp.parser.RankPLParser.FunctiondefContext;
import com.tr.rp.statement.RPLBaseTest;

public class ParseTest extends RPLBaseTest {

	public void testParseVariable() {
		String program = "x := 0;";
		assertEquals(parseStatement(program), (new Assign("x",0)));
		program = "x1 := 0;";
		AbstractStatement s = (AbstractStatement) parseStatement(program);
		assertEquals(s, (new Assign("x1",0)));
	}
	
	public void testParseAssign() {
		assertEquals(parseStatement("x := 20;"), (new Assign("x",20)));
		assertEquals(parseStatement("x[1] := 20;"), (new Assign(target("x", 1), 20)));
		assertEquals(parseStatement("x[1][2] := 20;"), (new Assign(target("x", 1, 2), 20)));
		assertEquals(parseStatement("x[x + y] := 20;"), (new Assign(target("x", plus(var("x"), var("y"))), 20)));
		assertEquals(parseStatement("x[x + y][p * q] := 20;"), (new Assign(target("x", plus(var("x"), var("y")), times(var("p"), var("q"))), 20)));
	}

	public void testParseArrayAssign() {
		ProgramBuilder b = new ProgramBuilder();
		b.add(new Assign("x", new ArrayConstructExpression(10, 11, 12)));
		assertEquals(parseStatement("x := [10, 11, 12];"), b.build());

		b = new ProgramBuilder();
		b.add(new Assign("x", new ArrayConstructExpression(plus(var("x"),var("y")))));
		assertEquals(parseStatement("x := [x + y];"), (b.build()));

		
		b = new ProgramBuilder();
		b.add(new Assign("x", new ArrayConstructExpression(plus(var("x"),var("y")), times(var("p"),var("q")))));
		assertEquals(parseStatement("x := [x + y, p * q];"), (b.build()));

		b = new ProgramBuilder();
		b.add(new Assign(target("x", 1), new ArrayConstructExpression(10, 11, 12)));
		assertEquals(parseStatement("x[1] := [10, 11, 12];"), (b.build()));

		b = new ProgramBuilder();
		b.add(new Assign(target("x", 10, 20), new ArrayConstructExpression(plus(var("x"),var("y")))));
		assertEquals(parseStatement("x[10][20] := [x + y];"), (b.build()));

		b = new ProgramBuilder();
		b.add(new Assign(target("x", 10, 20), new ArrayConstructExpression(plus(var("x"),var("y")), times(var("p"),var("q")))));
		assertEquals(parseStatement("x[10][20] := [x + y, p * q];"), (b.build()));
	}
	
	public void testParseComposition() {
		String program = "{x := 20; x := 30; x := 40}";
		assertEquals(parseStatement(program), (new Composition(
				new Assign("x",20),
				new Assign("x",30),
				new Assign("x",40))));
		program = "{{x := 20; x := 30 }; x := 40}";
		assertEquals(parseStatement(program), (new Composition(
				new Composition(
						new Assign("x",20),
						new Assign("x",30)),
				new Assign("x",40))));
		program = "{x := 20; {x := 30; x := 40}}";
		assertEquals(parseStatement(program), (new Composition(
				new Assign("x",20),
				new Composition(
						new Assign("x",30),
						new Assign("x",40)))));
		program = "{{x := 20}; {x := 30}; {x := 40}}";
		assertEquals(parseStatement(program), (new Composition(
				new Assign("x",20),
				new Assign("x",30),
				new Assign("x",40))));
	}

	public void testParseIfElse() {
		String program = "if (x == 10) THEN x := 20 else x := 30;";
		assertEquals(parseStatement(program), (new IfElse(
				eq(var("x"), lit(10)),
				new Assign("x",20),
				new Assign("x",30))));
		program = "if (x == 10) THEN { x := 20 } ELSE { x := 30 };";
		assertEquals(parseStatement(program), (new IfElse(
				eq(var("x"),lit(10)),
				new Assign("x",20),
				new Assign("x",30))));
		program = "{ if (x == 10) THEN { x := 20 } ELSE x := 30; x := 40; }";
		assertEquals(parseStatement(program), (
				new Composition(
					new IfElse(
							eq(var("x"),lit(10)),
							new Assign("x",20),
							new Assign("x",30)),
					new Assign("x", 40))));
		program = "{ if (x == 10) THEN { x := 20 } ELSE NORMALLY (1) x := 30 EXCEPTIONALLY x := 40; x := 50; }";
		assertEquals(parseStatement(program), (
				new Composition(
					new IfElse(
							eq(var("x"),lit(10)),
							new Assign("x",20),
							new RankedChoice(new Assign("x",30), new Assign("x", 40), lit(1))),
					new Assign("x", 50))));
	}

	public void testParseWhile() {
		String program = "while (x == 0) DO x := 0;";
		assertEquals(parseStatement(program), (new While(
				eq(var("x"), lit(0)),
				new Assign("x",0))));
	}

	public void testParseAssignChoose() {
		String program = "x := 0 << 5 >> 20;";
		assertEquals(parseStatement(program), (new RankedChoice(
				new Assign("x",0), new Assign("x",20), lit(5))));
	}

	public void testParseChoose() {
		assertEquals(parseStatement("normally(5) x := 0 exceptionally x := 20;"), (new RankedChoice(
				new Assign("x",0), new Assign("x",20), lit(5))));
		assertEquals(parseStatement("x := 0 << 5 >> 20;"), (new RankedChoice(
				new Assign("x",0), new Assign("x",20), lit(5))));
		assertEquals(parseStatement("x[0] := 0 << 5 >> 20;"), (new RankedChoice(
				new Assign(target("x", 0), 0), 
				new Assign(target("x", 0), 20), lit(5))));
		assertEquals(parseStatement("x[1][2] := 1 << 5 >> 20;"), (new RankedChoice(
				new Assign(target("x", 1, 2), 1), 
				new Assign(target("x", 1, 2), 20), lit(5))));
		assertEquals(parseStatement("x[x + y] := 2 << 5 >> 20;"), (new RankedChoice(
				new Assign(target("x", plus(var("x"), var("y"))), 2), 
				new Assign(target("x", plus(var("x"), var("y"))), 20), lit(5))));
		assertEquals(parseStatement("x[x + y][p * q] := 3 << 5 >> 20;"), (new RankedChoice(
				new Assign(target("x", plus(var("x"), var("y")), times(var("p"), var("q"))), 3), 
				new Assign(target("x", plus(var("x"), var("y")), times(var("p"), var("q"))), 20), lit(5))));
	}

	public void testParseObserve() {
		String program = "OBSERVE (x == 10);";
		assertEquals(parseStatement(program), (new Observe(eq(var("x"), lit(10)))));
	}
	
	public void testParseBoolExpr() {
		assertEquals(parseExpr("((a == 0) & (b == 1))"), and(eq(var("a"), lit(0)), eq(var("b"), lit(1))));
		assertEquals(parseExpr("(a == 0) | b == 1"), or(eq(var("a"), lit(0)), eq(var("b"), lit(1))));
		assertEquals(parseExpr("a == 0 ^ (b == 1)"), xor(eq(var("a"), lit(0)), eq(var("b"), lit(1))));
		assertEquals(parseExpr("a == 0"), (eq(var("a"), lit(0))));
		assertEquals(parseExpr("a != 0"), (notEquals(var("a"), lit(0))));
		assertEquals(parseExpr("a <= 0"), (leq(var("a"), lit(0))));
		assertEquals(parseExpr("a < 0"), (lt(var("a"), lit(0))));
		assertEquals(parseExpr("true"), (lit(true)));
		assertEquals(parseExpr("false"), (lit(false)));
		assertEquals(parseExpr("! FALSE"), (parseExpr("! false")));

		assertEquals(parseExpr("!true & false"), and(not(lit(true)), lit(false)));

		// associativity of boolean operators
		assertEquals(parseExpr("true & false & true"), and(lit(true), and(lit(false), lit(true))));
		assertEquals(parseExpr("true & false | true"), and(lit(true), or(lit(false), lit(true))));
		assertEquals(parseExpr("true | false & true"), or(lit(true), and(lit(false), lit(true))));
		assertEquals(parseExpr("true | false | true"), or(lit(true), or(lit(false), lit(true))));
		
		// associativity of equality
		assertEquals(parseExpr("true == false & true"), and(eq(lit(true), lit(false)), lit(true)));
		assertEquals(parseExpr("1 == 1 + 2"), eq(lit(1), plus(lit(1), lit(2))));
	}
	
	public void testParseNumExpr() {
		assertEquals(parseExpr("(a + 1)"), plus(var("a"), lit(1)));
		assertEquals(parseExpr("a - (1)"), minus(var("a"), lit(1)));
		assertEquals(parseExpr("1 * b"), times(lit(1), var("b")));
		assertEquals(parseExpr("((100) / 0)"), div(lit(100), lit(0)));
		assertEquals(parseExpr("a + b * c"), plus(var("a"), times(var("b"), var("c"))));
		assertEquals(parseExpr("a / b + c"), plus(div(var("a"), var("b")), var("c")));
		assertEquals(parseExpr("RANK(x == 0)"), rank(eq(var("x"), lit(0))));
		// precedence of minus
		assertEquals(parseExpr("-1 + -2"), plus(lit(-1), lit(-2)));
		assertEquals(parseExpr("a + -a"), plus(var("a"), minus(var("a"))));
		assertEquals(parseExpr("-a + -2"), plus(minus(var("a")), lit(-2)));
		// associativity
		assertEquals(parseExpr("10 - 4 - 2"), minus(lit(10), minus(lit(4), lit(2))));
		assertEquals(parseExpr("10 - 4 + 2"), minus(lit(10), plus(lit(4), lit(2))));
		assertEquals(parseExpr("10 + 4 - 2"), plus(lit(10), minus(lit(4), lit(2))));
		assertEquals(parseStatement("i++"), new Inc("i"));
		assertEquals(parseStatement("i--"), new Dec("i"));
	}

	public void testParseArrayExpr() {
		assertEquals(parseExpr("a[0]"), var("a", lit(0)));
		assertEquals(parseExpr("a[100]"), var("a", lit(100)));
		assertEquals(parseExpr("a[x + y]"), var("a", plus(var("x"), var("y"))));
		assertEquals(parseExpr("a[0][1]"), var("a", lit(0), lit(1)));
		assertEquals(parseExpr("a[100][200]"), var("a", lit(100), lit(200)));
		assertEquals(parseExpr("a[x + y][p * q]"), var("a", plus(var("x"), var("y")), times(var("p"), var("q"))));
	}

	public void testParseFunctionDefinition() {
		assertEquals(parseFunctionDef("define fun() { return 0; }"), (new Function("fun", new Return(lit(0)), new String[]{})));
		assertEquals(parseFunctionDef("define fun(a) { return a; }"), (new Function("fun", new Return("a"), new String[]{"a"})));
		assertEquals(parseFunctionDef("define fun(a) { skip; skip; return a; }"), (new Function("fun", new Composition(new Skip(), new Skip(), new Return("a")), new String[]{"a"})));
		assertEquals(parseFunctionDef("define fun(a) { skip; {skip;}; return a; }"), (new Function("fun", new Composition(new Skip(), new Skip(), new Return("a")), new String[]{"a"})));
		assertEquals(parseFunctionDef("define fun(a, b) { return a + b; }"), (new Function("fun", new Return(plus(new Variable("a"), new Variable("b"))), new String[]{"a", "b"})));
	}
	
	public void testParseFunctionCall() {
		assertEquals(parseStatement("x := fun()").toString(), ("x := fun()"));
		assertEquals(parseStatement("x := fun(a, b)").toString(), ("x := fun(a, b)"));
		assertEquals(parseStatement("x := fun(fun(a, b), c)").toString(), ("x := fun(fun(a, b), c)"));
		assertEquals(parseStatement("if (fun1(fun2(a, b), c)) then x := fun3(d) else x := fun4(e)").toString(), ("if (fun1(fun2(a, b), c)) then x := fun3(d) else x := fun4(e)"));
		assertEquals(parseStatement("while (x == fun1(y)) do y := fun2(z)").toString(), ("while (x == fun1(y)) do y := fun2(z)"));
		assertEquals(parseStatement("x := fun1() <<fun2()>> fun3()").toString(), ("normally (fun2()) x := fun1() exceptionally x := fun3()"));
		assertEquals(parseStatement("normally (fun2()) x := fun1() exceptionally x := fun3()").toString(), ("normally (fun2()) x := fun1() exceptionally x := fun3()"));
		assertEquals(parseStatement("observe fun()").toString(), ("observe fun()"));
		assertEquals(parseStatement("observe fun1() == fun2()").toString(), ("observe fun1() == fun2()"));
		assertEquals(parseStatement("observe-j (fun1()) fun2()").toString(), ("observe-j (fun1()) fun2()"));
		assertEquals(parseStatement("observe-j (fun1()) fun2() == fun3()").toString(), ("observe-j (fun1()) fun2() == fun3()"));
		assertEquals(parseStatement("observe-l (fun1()) fun2()").toString(), ("observe-l (fun1()) fun2()"));
		assertEquals(parseStatement("observe-l (fun1()) fun2() == fun3()").toString(), ("observe-l (fun1()) fun2() == fun3()"));
	}
	
	public void testParseInferringFunctionCall() {
		assertEquals(parseStatement("x := infer(fun())").toString(), ("x := infer(fun())"));
		assertEquals(parseStatement("x := infer(fun(a, b))").toString(), ("x := infer(fun(a, b))"));
		assertEquals(parseStatement("x := infer(fun(fun(a, b), c))").toString(), ("x := infer(fun(fun(a, b), c))"));
		assertEquals(parseStatement("x := fun(infer(fun(a, b)), c)").toString(), ("x := fun(infer(fun(a, b)), c)"));
	}
	
	public void testParseStrings() {
		assertEquals(parseStatement("x := \"\""), new Assign("x", lit("")));
		assertEquals(parseStatement("x := \"\\\"\""), new Assign("x", lit("\"")));
		assertEquals(parseStatement("x := \"abc\""), new Assign("x", lit("abc")));
		assertEquals(parseStatement("x := \"abc\" + \"def\""), new Assign("x", plus(lit("abc"), lit("def"))));
		// test white spaces in string literals
		assertEquals(parseStatement("x := \"x \""), new Assign("x", lit("x ")));
		assertEquals(parseStatement("x := \"x x\""), new Assign("x", lit("x x")));
		assertEquals(parseStatement("x := \" x\""), new Assign("x", lit(" x")));
	}
	
	private LanguageElement parseStatement(String code) {
		if (!code.endsWith(";")) {
			code += ";";
		}
        CharStream charStream = new ANTLRInputStream(code);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        return ((Program)classVisitor.visit(parser.program())).getBody();
	}
	
	private AbstractExpression parseExpr(String code) {
        CharStream charStream = new ANTLRInputStream(code);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        ExpContext ctx = parser.exp();
        AbstractExpression res = (AbstractExpression)classVisitor.visit(ctx);
        return res;
	}

	private Function parseFunctionDef(String code) {
        CharStream charStream = new ANTLRInputStream(code);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        FunctiondefContext ctx = parser.functiondef();
        Function res = (Function)classVisitor.visit(ctx);
        return res;
	}
}
