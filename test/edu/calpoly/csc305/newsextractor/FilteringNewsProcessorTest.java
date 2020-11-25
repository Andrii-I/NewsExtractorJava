package edu.calpoly.csc305.newsextractor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;





/**
 * Tests Filtering News Processor.
 */
class FilteringNewsProcessorTest {
  /**
   * Filter out one of the articles based on keyword.
   */
  @Test
  void filteringOutArticle() throws MalformedURLException {
    NewsProcessor basicProcessor = Mockito.mock(NewsProcessor.class);
    List<Article> articles = new LinkedList<>();
    articles.add(new GeneralArticle("BBQ", "do you like BBQ?",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://jocko.com/")));
    articles.add(new GeneralArticle("Paul and Lily married", "marriage Paul and Lily",
        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
        new URL("http://marriage.com/")));
    Mockito.when(basicProcessor.getArticles()).thenReturn(articles);
    ArticleExpression expression = new KeywordArticleExpression("lily");

    assertEquals(2, articles.size());
    NewsProcessor filteringProcessor = new FilteringNewsProcessor(basicProcessor, expression);
    List<Article> filteredReturn = filteringProcessor.getArticles();
    assertEquals(1, filteredReturn.size());
    assertEquals("Paul and Lily married", filteredReturn.get(0).getTitle());
    assertEquals("marriage Paul and Lily", filteredReturn.get(0).getDescription());
    assertEquals("http://marriage.com/", filteredReturn.get(0).getUrl().toString());
  }

  /**
   * No articles filtered out.
   */
  @Test
  void notFilteringOutArticle() throws MalformedURLException {
    NewsProcessor basicProcessor = Mockito.mock(NewsProcessor.class);
    List<Article> articles = new LinkedList<>();
    articles.add(new GeneralArticle("Horses", "something about them",
        LocalDateTime.of(2015, Month.JUNE, 15, 19, 30, 40),
        new URL("http://birds.com/")));
    articles.add(new GeneralArticle("Birds", "other stuff about birds",
        LocalDateTime.of(2015, Month.AUGUST, 29, 19, 30, 40),
        new URL("http://other.com/")));
    Mockito.when(basicProcessor.getArticles()).thenReturn(articles);
    ArticleExpression expression = new KeywordArticleExpression("about");

    assertEquals(2, articles.size());
    NewsProcessor filteringProcessor = new FilteringNewsProcessor(basicProcessor, expression);
    List<Article> filteredReturn = filteringProcessor.getArticles();
    assertEquals(2, filteredReturn.size());
  }

  /**
   * No articles filtered out.
   */
  @Test
  void allFilteringOutArticle() throws MalformedURLException {
    NewsProcessor basicProcessor = Mockito.mock(NewsProcessor.class);
    List<Article> articles = new LinkedList<>();
    articles.add(new GeneralArticle("TV", "what about TV",
        LocalDateTime.of(2015, Month.FEBRUARY, 25, 19, 30, 40),
        new URL("http://tv.com/")));
    articles.add(new GeneralArticle("Cinema", "is the best",
        LocalDateTime.of(2015, Month.MARCH, 29, 19, 30, 40),
        new URL("http://cinema.com/")));
    Mockito.when(basicProcessor.getArticles()).thenReturn(articles);
    ArticleExpression expression = new KeywordArticleExpression("winter");

    assertEquals(2, articles.size());
    NewsProcessor filteringProcessor = new FilteringNewsProcessor(basicProcessor, expression);
    List<Article> filteredReturn = filteringProcessor.getArticles();
    assertEquals(0, filteredReturn.size());
  }

}