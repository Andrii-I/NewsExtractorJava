package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.Article;
import edu.calpoly.csc305.newsextractor.ArticleExpression;
import edu.calpoly.csc305.newsextractor.ArticlesNewsProcessor;
import edu.calpoly.csc305.newsextractor.CachingNewsProcessor;
import edu.calpoly.csc305.newsextractor.FileNewsSource;
import edu.calpoly.csc305.newsextractor.FilteringNewsProcessor;
import edu.calpoly.csc305.newsextractor.NewsParser;
import edu.calpoly.csc305.newsextractor.NewsProcessor;
import edu.calpoly.csc305.newsextractor.NewsSource;
import edu.calpoly.csc305.newsextractor.ProcessorScheduler;
import edu.calpoly.csc305.newsextractor.UrlNewsSource;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Extracts NewsProcessor.
 */
public class AggregatorProcessorVisitor
    extends AggregatorConfigParserBaseVisitor<Optional<ProcessorScheduler>> {
  private final Logger logger;
  private final BlockingQueue<Article> queue;

  /**B
   * Creates an instance of AggregatorProcessorVisitor.
   *
   * @param logger Logger for logging.
   */
  public AggregatorProcessorVisitor(Logger logger, BlockingQueue<Article> queue) {
    this.logger = logger;
    this.queue = queue;
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#fileSourceType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Optional<ProcessorScheduler>
      visitFileSourceType(AggregatorConfigParser.FileSourceTypeContext ctx) {
    NewsSource source =
        new FileNewsSource(ctx.source().accept(new AggregatorSourceStringVisitor()));
    NewsParser parser = ctx.format().accept(new AggregatorNewsParserVisitor());
    Optional<ArticleExpression> expression = ctx.filter().accept(new AggregatorFilterVisitor());
    Optional<Integer> delay = Optional.empty();
    if (ctx.delay() != null) {
      delay = Optional.of(Integer.valueOf(ctx.delay().accept(new AggregatorSourceStringVisitor())));
    }

    if (expression.isPresent()) {
      return Optional.of(
        new ProcessorScheduler(
          new CachingNewsProcessor(
            new FilteringNewsProcessor(
              new ArticlesNewsProcessor(source, parser, logger), expression.get()
            )
          ), queue, delay)
      );
    }

    return Optional.of(
      new ProcessorScheduler(new CachingNewsProcessor(
        new ArticlesNewsProcessor(source, parser, logger)), queue, delay)
    );
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#urlSourceType}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public Optional<ProcessorScheduler>
      visitUrlSourceType(AggregatorConfigParser.UrlSourceTypeContext ctx) {
    Optional<NewsSource> source = Optional.empty();
    try {
      source =
        Optional.of(new UrlNewsSource(ctx.source().accept(new AggregatorSourceStringVisitor())));
    } catch (MalformedURLException e) {
      logger.warning(e.getClass().getSimpleName());
    }
    Optional<Integer> delay = Optional.empty();
    if (ctx.delay() != null) {
      delay = Optional.of(Integer.valueOf(ctx.delay().accept(new AggregatorSourceStringVisitor())));
    }
    NewsParser parser = ctx.format().accept(new AggregatorNewsParserVisitor());
    Optional<ArticleExpression> expression = ctx.filter().accept(new AggregatorFilterVisitor());

    if (source.isEmpty()) {
      return Optional.empty();
    }

    if (expression.isPresent()) {
      return Optional.of(
        new ProcessorScheduler(
          new CachingNewsProcessor(
            new FilteringNewsProcessor(
              new ArticlesNewsProcessor(source.get(), parser, logger), expression.get()
            )
          ), queue, delay)
      );
    } else {
      return Optional.of(new ProcessorScheduler(new CachingNewsProcessor(
        new ArticlesNewsProcessor(source.get(), parser, logger)), queue, delay));
    }
  }

}
