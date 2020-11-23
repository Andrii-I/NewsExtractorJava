package edu.calpoly.csc305.newsextractor;

/**
 * Interface for Article expressions.
 */
public interface ArticleExpression {

  /**
   * Evaluates if article meets the predicate's condition.
   *
   * @param article article to be tested for meeting conditions
   * @return boolean result of testing
   */
  boolean test(Article article);
}
