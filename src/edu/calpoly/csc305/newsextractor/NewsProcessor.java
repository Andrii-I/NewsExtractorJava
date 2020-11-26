package edu.calpoly.csc305.newsextractor;

import java.util.List;
import java.util.logging.Logger;

/**
 * Interface for different types of Processor-s.
 */
public interface NewsProcessor {

  /**
   * Extracts articles from json of standard format from source and returns them as a list.
   *
   * @return List of GeneralArticles extracted from the source
   */
  List<Article> getArticles();

  /**
   * Source getter.
   *
   * @return news source.
   */
  NewsSource getSource();

  /**
   * Parser getter.
   *
   * @return parser.
   */
  NewsParser getParser();

  /**
   * Logger getter.
   *
   * @return logger.
   */
  Logger getLogger();
}
