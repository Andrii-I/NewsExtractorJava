package edu.calpoly.csc305.newsextractor;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * News processor for articles.
 */
public class ArticlesNewsProcessor implements NewsProcessor {
  private final NewsSource source;
  private final NewsParser parser;
  private final Logger logger;

  /**
   * Creates an instance of a News Processor with immutable dependencies.
   *
   * @param source News Source from which data would be extracted
   * @param parser Parser that will parse the extracted data
   * @param logger Logger for logging
   */
  public ArticlesNewsProcessor(NewsSource source, NewsParser parser,
                               Logger logger) {
    this.source = source;
    this.parser = parser;
    this.logger = logger;
  }

  /**
   * Extracts articles from source and returns them as a list.
   *
   * @return List of GeneralArticles extracted from the source
   */
  @Override
  public List<Article> getArticles() {
    try {
      return parser.getArticles(source.getString(), logger);
    } catch (Exception e) {
      logger.warning(e.getClass().getSimpleName());
    }
    return new LinkedList<>();
  }

  /**
   * Source getter.
   *
   * @return news source.
   */
  public NewsSource getSource() {
    return this.source;
  }

  /**
   * Parser getter.
   *
   * @return parser.
   */
  public NewsParser getParser() {
    return this.parser;
  }

  /**
   * Logger getter.
   *
   * @return logger.
   */
  public Logger getLogger() {
    return this.logger;
  }
}
