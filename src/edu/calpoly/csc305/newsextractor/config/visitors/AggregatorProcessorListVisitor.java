package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.Article;
import edu.calpoly.csc305.newsextractor.NewsProcessor;
import edu.calpoly.csc305.newsextractor.ProcessorScheduler;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;


/**
 * Extracts list of NewsProcessors.
 */
public class AggregatorProcessorListVisitor
    extends AggregatorConfigParserBaseVisitor<List<ProcessorScheduler>> {
  private final Logger logger;
  private final BlockingQueue<Article> queue;

  public AggregatorProcessorListVisitor(Logger logger, BlockingQueue<Article> queue) {
    this.logger = logger;
    this.queue = queue;
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#sources}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<ProcessorScheduler> visitSources(AggregatorConfigParser.SourcesContext ctx) {
    List<ProcessorScheduler> sourceNames = new ArrayList<>();

    for (AggregatorConfigParser.SourceTypeContext sourceType : ctx.sourceType()) {
      Optional<ProcessorScheduler> article =
          sourceType.accept(new AggregatorProcessorVisitor(logger, queue));
      article.ifPresent(sourceNames::add);
    }

    return sourceNames;
  }

}
