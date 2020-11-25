package edu.calpoly.csc305.newsextractor;

/**
 * Keyword Article Expression class.
 */
public class KeywordArticleExpression implements ArticleExpression {
  private final String keyword;

  /**
   * Creates an instance of KeywordArticleExpression.
   *
   * @param keyword keyword for presence of which the Article would be tested.
   */
  public KeywordArticleExpression(String keyword) {
    this.keyword = keyword.toLowerCase();
  }

  /**
   * Evaluates if Description or Title or PublishedAt or Url of the Article contain the keyword
   * (case insensitive).
   *
   * @param article article to be tested for containing the keyword
   * @return boolean result of testing if Article contains the keyword
   */
  @Override
  public boolean test(Article article) {
    return article.getDescription().toLowerCase().contains(keyword)
           || article.getTitle().toLowerCase().contains(keyword)
           || article.getPublishedAt().toString().toLowerCase().contains(keyword)
           || article.getUrl().toString().toLowerCase().contains(keyword);
  }
}
