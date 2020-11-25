package edu.calpoly.csc305.newsextractor;

import java.util.List;

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
}
