package edu.calpoly.csc305.newsextractor;

import java.util.List;
import java.util.logging.Logger;

/**
 * This is an interface for different types of Parsers.
 */
public interface NewsParser {
  /**
   * Getter for Title.
   *
   * @return title String
   */
  List<Article> getArticles(String json, Logger logger);
}