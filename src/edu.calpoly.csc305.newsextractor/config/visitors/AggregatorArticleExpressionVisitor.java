package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.ArticleExpression;
import edu.calpoly.csc305.newsextractor.KeywordArticleExpression;
import edu.calpoly.csc305.newsextractor.LogicalAndArticleExpression;
import edu.calpoly.csc305.newsextractor.LogicalOrArticleExpression;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;

/**
 * Extracts and returns various implementations of ArticleExpression.
 */
public class AggregatorArticleExpressionVisitor
    extends AggregatorConfigParserBaseVisitor<ArticleExpression> {
  /**
   * Visit a parse tree produced by the {@code KeywordExpression}
   * labeled alternative in {@link AggregatorConfigParser#selector}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public ArticleExpression visitKeywordExpression(AggregatorConfigParser.KeywordExpressionContext
                                                      ctx) {
    return new KeywordArticleExpression(ctx.KEYWORD().toString().trim());
  }

  /**
   * Visit a parse tree produced by the {@code AndExpression}
   * labeled alternative in {@link AggregatorConfigParser#selector}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public ArticleExpression visitAndExpression(AggregatorConfigParser.AndExpressionContext ctx) {
    return new LogicalAndArticleExpression(ctx.lft.accept(this), ctx.rht.accept(this));
  }

  /**
   * Visit a parse tree produced by the {@code OrExpression}
   * labeled alternative in {@link AggregatorConfigParser#selector}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public ArticleExpression visitOrExpression(AggregatorConfigParser.OrExpressionContext ctx) {
    return new LogicalOrArticleExpression(ctx.lft.accept(this), ctx.rht.accept(this));
  }

  /**
   * Visit a parse tree produced by the {@code NestedExpression}
   * labeled alternative in {@link AggregatorConfigParser#selector}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public ArticleExpression visitNestedExpression(AggregatorConfigParser.NestedExpressionContext
      ctx) {
    return ctx.selector().accept(this);
  }
}
