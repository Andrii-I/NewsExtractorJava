package edu.calpoly.csc305.newsextractor;

/**
 * Logical Or Expression.
 */
public class LogicalOrArticleExpression implements ArticleExpression {
  private final ArticleExpression leftExpression;
  private final ArticleExpression rightExpression;

  /**
   * Creates an instance of Logical Or article expression.
   *
   * @param leftExpression left child expression
   * @param rightExpression right child expression
   */
  public LogicalOrArticleExpression(ArticleExpression leftExpression,
                                    ArticleExpression rightExpression) {
    this.leftExpression = leftExpression;
    this.rightExpression = rightExpression;
  }

  /**
   * Tests leftExpression LOGICAL OR rightExpression.
   *
   * @param article article to be used in testing
   * @return boolean result of testing
   */
  @Override
  public boolean test(Article article) {
    return leftExpression.test(article) || rightExpression.test(article);
  }
}
