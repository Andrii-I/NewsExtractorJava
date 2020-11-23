package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.NewsParser;
import edu.calpoly.csc305.newsextractor.SimpleOrgJsonNewsParser;
import edu.calpoly.csc305.newsextractor.StandardOrgJsonNewsParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;

/**
 * Extracts list of NewsProcessors.
 */
public class AggregatorNewsParserVisitor extends AggregatorConfigParserBaseVisitor<NewsParser> {

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#format}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public NewsParser visitFormat(AggregatorConfigParser.FormatContext ctx) {
    return ctx.format_option().accept(this);
  }

  /**
   * Visit a parse tree produced by the {@code NewsApiFormat}
   * labeled alternative in {@link AggregatorConfigParser#format_option}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public NewsParser visitNewsApiFormat(AggregatorConfigParser.NewsApiFormatContext ctx) {
    return new StandardOrgJsonNewsParser();
  }


  /**
   * Visit a parse tree produced by the {@code SimpleFormat}
   * labeled alternative in {@link AggregatorConfigParser#format_option}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public NewsParser visitSimpleFormat(AggregatorConfigParser.SimpleFormatContext ctx) {
    return new SimpleOrgJsonNewsParser();
  }

}
