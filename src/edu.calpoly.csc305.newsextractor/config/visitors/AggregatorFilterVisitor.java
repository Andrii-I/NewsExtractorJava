package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.ArticleExpression;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;
import java.util.Optional;

/**
 * Extracts filter (ArticleExpression) if present.
 */
public class AggregatorFilterVisitor
    extends AggregatorConfigParserBaseVisitor<Optional<ArticleExpression>> {
  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#filter}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Optional<ArticleExpression> visitFilter(AggregatorConfigParser.FilterContext ctx) {
    if (ctx.selector() != null) {
      return Optional.of(ctx.selector().accept(new AggregatorArticleExpressionVisitor()));
    }
    return Optional.empty();
  }
}
