package edu.calpoly.csc305.newsextractor.config.visitors;

import edu.calpoly.csc305.newsextractor.NewsProcessor;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParserBaseVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


/**
 * Extracts list of NewsProcessors.
 */
public class AggregatorProcessorListVisitor
    extends AggregatorConfigParserBaseVisitor<List<NewsProcessor>> {
  private final Logger logger;

  public AggregatorProcessorListVisitor(Logger logger) {
    this.logger = logger;
  }

  /**
   * Visit a parse tree produced by {@link AggregatorConfigParser#sources}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  @Override
  public List<NewsProcessor> visitSources(AggregatorConfigParser.SourcesContext ctx) {
    List<NewsProcessor> sourceNames = new ArrayList<>();

    for (AggregatorConfigParser.SourceTypeContext sourceType : ctx.sourceType()) {
      Optional<NewsProcessor> article =
          sourceType.accept(new AggregatorProcessorVisitor(logger));
      article.ifPresent(sourceNames::add);
    }

    return sourceNames;
  }

}
