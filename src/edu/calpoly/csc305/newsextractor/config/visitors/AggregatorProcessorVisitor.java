package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.ArticleExpression;
import edu.calpoly.csc305.newsextractor.ArticlesNewsProcessor;
import edu.calpoly.csc305.newsextractor.FileNewsSource;
import edu.calpoly.csc305.newsextractor.FilteringNewsProcessor;
import edu.calpoly.csc305.newsextractor.NewsParser;
import edu.calpoly.csc305.newsextractor.NewsProcessor;
import edu.calpoly.csc305.newsextractor.NewsSource;
import edu.calpoly.csc305.newsextractor.UrlNewsSource;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Extracts NewsProcessor.
 */
public class AggregatorProcessorVisitor
    extends AggregatorConfigParserBaseVisitor<Optional<NewsProcessor>> {
  private final Logger logger;

  /**B
   * Creates an instance of AggregatorProcessorVisitor.
   *
   * @param logger Logger for logging.
   */
  public AggregatorProcessorVisitor(Logger logger) {
    this.logger = logger;
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#fileSourceType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Optional<NewsProcessor>
      visitFileSourceType(AggregatorConfigParser.FileSourceTypeContext ctx) {
    NewsSource source =
        new FileNewsSource(ctx.source().accept(new AggregatorSourceStringVisitor()));
    NewsParser parser = ctx.format().accept(new AggregatorNewsParserVisitor());
    Optional<ArticleExpression> expression = ctx.filter().accept(new AggregatorFilterVisitor());

    if (expression.isPresent()) {
      return Optional.of(new FilteringNewsProcessor(new ArticlesNewsProcessor(source, parser,
        logger), expression.get()));
    }

    return Optional.of(new ArticlesNewsProcessor(source, parser, logger));
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#urlSourceType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Optional<NewsProcessor>
      visitUrlSourceType(AggregatorConfigParser.UrlSourceTypeContext ctx) {
    Optional<NewsSource> source = Optional.empty();
    try {
      source =
        Optional.of(new UrlNewsSource(ctx.source().accept(new AggregatorSourceStringVisitor())));
    } catch (MalformedURLException e) {
      logger.warning(e.getClass().getSimpleName());
    }
    NewsParser parser = ctx.format().accept(new AggregatorNewsParserVisitor());
    Optional<ArticleExpression> expression = ctx.filter().accept(new AggregatorFilterVisitor());

    if (source.isEmpty()) {
      return Optional.empty();
    }

    if (expression.isPresent()) {
      return Optional.of(new FilteringNewsProcessor(new ArticlesNewsProcessor(source.get(), parser,
        logger), expression.get()));
    } else {
      return Optional.of(new ArticlesNewsProcessor(source.get(), parser, logger));
    }
  }

}
