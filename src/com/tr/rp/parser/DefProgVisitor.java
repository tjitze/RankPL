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
	 * Visit a parse tree produced by the {@code ObserveL}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserveL(DefProgParser.ObserveLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code array_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_assignment_stat(DefProgParser.Array_assignment_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statement_sequence}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_sequence(DefProgParser.Statement_sequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Observe}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserve(DefProgParser.ObserveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ranked_choice}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRanked_choice(DefProgParser.Ranked_choiceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code choice_assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoice_assignment_stat(DefProgParser.Choice_assignment_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code skip_stat}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObserveJ}
	 * labeled alternative in {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserveJ(DefProgParser.ObserveJContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompareExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpr(DefProgParser.CompareExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumBoolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumBoolExpr(DefProgParser.NumBoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralBoolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralBoolExpr(DefProgParser.LiteralBoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParboolExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParboolExpr(DefProgParser.ParboolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExpr(DefProgParser.BooleanExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(DefProgParser.NegateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AbsExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbsExpr(DefProgParser.AbsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralNumExpr(DefProgParser.LiteralNumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableNumExpr(DefProgParser.VariableNumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RankExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRankExpr(DefProgParser.RankExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticNumExpr(DefProgParser.ArithmeticNumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParNumExpr}
	 * labeled alternative in {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParNumExpr(DefProgParser.ParNumExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(DefProgParser.IndexContext ctx);
}