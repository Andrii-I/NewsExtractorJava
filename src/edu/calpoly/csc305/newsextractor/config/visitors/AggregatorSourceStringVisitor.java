package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;

/**
 * Extracts source as a string.
 */
public class AggregatorSourceStringVisitor extends AggregatorConfigParserBaseVisitor<String> {
  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#sourceName}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public String visitSource(AggregatorConfigParser.SourceContext ctx) {
    return ctx.LINE().toString().trim();
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#delay}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public String visitDelay(AggregatorConfigParser.DelayContext ctx) {
    return ctx.NUM().toString().trim();
  }
}
