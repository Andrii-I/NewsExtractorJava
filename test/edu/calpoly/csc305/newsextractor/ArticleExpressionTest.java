package edu.calpoly.csc305.newsextractor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;




/**
 * Tests Article Expressions.
 */
class ArticleExpressionTest {
  /**
   * Tests keyword Expressions.
   */
  @Test
  void keywordAbsent() throws MalformedURLException {
    ArticleExpression expression = new KeywordArticleExpression("James");
    Article article = new GeneralArticle("Shaun", "news about Shaun",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://example.com/"));
    assertFalse(expression.test(article));
  }

  /**
   * Tests keyword Expressions.
   */
  @Test
  void keywordPresent() throws MalformedURLException {
    ArticleExpression expression = new KeywordArticleExpression("shaun");
    Article article = new GeneralArticle("Paul", "news about Shaun",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://example.com/"));
    assertTrue(expression.test(article));
  }

  /**
   * Tests Logical And Expressions.
   */
  @Test
  void logicalAndPresent() throws MalformedURLException {
    ArticleExpression expression = new LogicalAndArticleExpression(
        new KeywordArticleExpression("Paul"), new KeywordArticleExpression("Shaun"));

    Article article = new GeneralArticle("Paul", "news about only Paul",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://example.com/"));
    assertFalse(expression.test(article));
  }

  /**
   * Tests Logical And Expressions.
   */
  @Test
  void logicalAndAbsent() throws MalformedURLException {
    ArticleExpression expression = new LogicalAndArticleExpression(
        new KeywordArticleExpression("Paul"), new KeywordArticleExpression("Shaun"));

    Article article = new GeneralArticle("some title", "news about Shaun Paul",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://example.com/"));
    assertTrue(expression.test(article));
  }

  /**
   * Tests Logical Or Expressions.
   */
  @Test
  void logicalOrAbsent() throws MalformedURLException {
    ArticleExpression expression = new LogicalOrArticleExpression(
        new KeywordArticleExpression("example"), new KeywordArticleExpression("Shaun"));

    Article article = new GeneralArticle("some title", "some news about",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://ex.com/"));
    assertFalse(expression.test(article));
  }

  /**
   * Tests Logical Or Expressions.
   */
  @Test
  void logicalOrPresent() throws MalformedURLException {
    ArticleExpression expression = new LogicalOrArticleExpression(
        new KeywordArticleExpression("example"), new KeywordArticleExpression("Shaun"));

    Article article = new GeneralArticle("some title", "some news about",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://example.com/"));
    assertTrue(expression.test(article));
  }
}