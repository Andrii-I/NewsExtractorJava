package edu.calpoly.csc305.newsextractor;

/**
 * Logical And Expression.
 */
public class LogicalAndArticleExpression implements ArticleExpression {
  private final ArticleExpression leftExpression;
  private final ArticleExpression rightExpression;

  /**
   * Creates an instance of Logical And article expression.
   *
   * @param leftExpression left child expression
   * @param rightExpression right child expression
   */
  public LogicalAndArticleExpression(ArticleExpression leftExpression,
                                     ArticleExpression rightExpression) {
    this.leftExpression = leftExpression;
    this.rightExpression = rightExpression;
  }

  /**
   * Tests leftExpression LOGICAL AND rightExpression.
   *
   * @param article article to be used in testing
   * @return boolean result of testing
   */
  @Override
  public boolean test(Article article) {
    return leftExpression.test(article) && rightExpression.test(article);
  }
}
