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
	 * Visit a parse tree produced by {@link DefProgParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(DefProgParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#ranked_choice}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRanked_choice(DefProgParser.Ranked_choiceContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#assignment_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_stat(DefProgParser.Assignment_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#if_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stat(DefProgParser.If_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#while_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stat(DefProgParser.While_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#observe_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObserve_stat(DefProgParser.Observe_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#skip_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip_stat(DefProgParser.Skip_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#boolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolexpr(DefProgParser.BoolexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#compareexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareexpr(DefProgParser.CompareexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#booleanexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanexpr(DefProgParser.BooleanexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#negateexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateexpr(DefProgParser.NegateexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#litboolexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLitboolexpr(DefProgParser.LitboolexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#numexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumexpr(DefProgParser.NumexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#arithnumexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithnumexpr(DefProgParser.ArithnumexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#litnumexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLitnumexpr(DefProgParser.LitnumexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link DefProgParser#varnumexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarnumexpr(DefProgParser.VarnumexprContext ctx);
}