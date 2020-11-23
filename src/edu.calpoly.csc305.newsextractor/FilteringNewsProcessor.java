package edu.calpoly.csc305.newsextractor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Filters news processor based on expression.
 */
public class FilteringNewsProcessor implements NewsProcessor {
  private final NewsProcessor newsProcessor;
  private final ArticleExpression expression;

  /**
   * Creates an instance of Filtering News Processor.
   *
   * @param newsProcessor original News Processor to get List of Articles from
   * @param expression expression based on which Articles would be evaluated
   */
  public FilteringNewsProcessor(NewsProcessor newsProcessor,
                                ArticleExpression expression) {
    this.newsProcessor = newsProcessor;
    this.expression = expression;
  }

  /**
   * Extracts articles from original Processor and filters them based on expression.
   *
   * @return List of filtered GeneralArticles
   */
  @Override
  public List<Article> getArticles() {
    return newsProcessor.getArticles().stream().filter(expression::test)
      .collect(Collectors.toList());
  }

  /**
   * Source getter.
   *
   * @return news source.
   */
  public NewsSource getSource() {
    return this.newsProcessor.getSource();
  }

  /**
   * Parser getter.
   *
   * @return parser.
   */
  public NewsParser getParser() {
    return this.newsProcessor.getParser();
  }
}
