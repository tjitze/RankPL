// Generated from /Users/tjitze/Desktop/workspace/RankPL/src/com/tr/rp/parser/DefProg.g by ANTLR 4.5

package com.tr.rp.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DefProgParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DefProgVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DefProgParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(DefProgParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(DefProgParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#functiondef_or_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondef_or_statement(DefProgParser.Functiondef_or_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#functiondef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondef(DefProgParser.FunctiondefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code choice_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoice_assignment_stat(DefProgParser.Choice_assignment_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code range_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_choice(DefProgParser.Range_choiceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code for_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stat(DefProgParser.For_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Observe}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserve(DefProgParser.ObserveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObserveL}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserveL(DefProgParser.ObserveLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObserveJ}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserveJ(DefProgParser.ObserveJContext ctx);
	/**
	 * Visit a parse tree produced by the {@code skip_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ranked_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRanked_choice(DefProgParser.Ranked_choiceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indifferent_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndifferent_choice(DefProgParser.Indifferent_choiceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statement_sequence}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_sequence(DefProgParser.Statement_sequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code return_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(DefProgParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print_statement}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_statement(DefProgParser.Print_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(DefProgParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolExpression}
	 * labeled alternative in {@link DefProgParser#expr1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpression(DefProgParser.BoolExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompareExpr}
	 * labeled alternative in {@link DefProgParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpr(DefProgParser.CompareExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Arithmetic1Expression}
	 * labeled alternative in {@link DefProgParser#expr3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic1Expression(DefProgParser.Arithmetic1ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Arithmetic2Expression}
	 * labeled alternative in {@link DefProgParser#expr4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic2Expression(DefProgParser.Arithmetic2ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralIntExpression}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralIntExpression(DefProgParser.LiteralIntExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralBoolExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralBoolExpr(DefProgParser.LiteralBoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralStringExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralStringExpr(DefProgParser.LiteralStringExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExpression(DefProgParser.VariableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InferringFunctionCall}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInferringFunctionCall(DefProgParser.InferringFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(DefProgParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(DefProgParser.NegateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MinusExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusExpr(DefProgParser.MinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsSetExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsSetExpr(DefProgParser.IsSetExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbsExpr(DefProgParser.AbsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LenExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLenExpr(DefProgParser.LenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubStringExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubStringExpr(DefProgParser.SubStringExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RankExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRankExpr(DefProgParser.RankExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayInitExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitExpr(DefProgParser.ArrayInitExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayConstructExpr}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayConstructExpr(DefProgParser.ArrayConstructExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(DefProgParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParExpression}
	 * labeled alternative in {@link DefProgParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(DefProgParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(DefProgParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(DefProgParser.IndexContext ctx);
}