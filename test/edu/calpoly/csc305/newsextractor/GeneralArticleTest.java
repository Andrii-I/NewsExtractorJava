package edu.calpoly.csc305.newsextractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;


/**
 * Tests GeneralArticle.
 */
class GeneralArticleTest {
  /**
   * Tests Article equality/comparasion with equal article.
   */
  @Test
  void sameFields() throws MalformedURLException {
    Article article1 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    Article article2 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    assertEquals(article1, article2);
  }

  /**
   * Tests Article equality/comparasion with different titles article.
   */
  @Test
  void differentTitle() throws MalformedURLException {
    Article article1 = new GeneralArticle("BBC", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    Article article2 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    assertNotEquals(article1, article2);
  }

  /**
   * Tests Article equality/comparasion with different description article.
   */
  @Test
  void differentDescription() throws MalformedURLException {
    Article article1 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    Article article2 = new GeneralArticle("BBQ", "do you like BBC?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    assertNotEquals(article1, article2);
  }

  /**
   * Tests Article equality/comparasion with different date article.
   */
  @Test
  void differentDate() throws MalformedURLException {
    Article article1 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    Article article2 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2014, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    assertNotEquals(article1, article2);
  }

  /**
   * Tests Article equality/comparasion with different date article.
   */
  @Test
  void differentUrl() throws MalformedURLException {
    Article article1 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://example.com/"));
    Article article2 = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));
    assertNotEquals(article1, article2);
  }
}