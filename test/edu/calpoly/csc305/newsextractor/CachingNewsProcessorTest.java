package edu.calpoly.csc305.newsextractor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

/**
 * Tests Cached News Processor.
 */
class CachingNewsProcessorTest {
  /**
   * No articles filtered out.
   */
  @Test
  void noFilteringOut() throws MalformedURLException {
    NewsProcessor nestedProcessor = Mockito.mock(NewsProcessor.class);

    List<Article> articles1 = new LinkedList<>();
    articles1.add(new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/")));
    GeneralArticle matchingArticle = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));

    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles1);
    CachingNewsProcessor cachingProcessor = new CachingNewsProcessor(nestedProcessor);

    List<Article> return1 = cachingProcessor.getArticles();
    assertEquals(1, return1.size());
    assertEquals(matchingArticle, return1.get(0));

    List<Article> articles2 = new LinkedList<>();
    articles2.add(new GeneralArticle("Horses", "something about them",
      LocalDateTime.of(2016, Month.JUNE, 15, 19, 30, 40),
      new URL("http://birds.com/")));
    GeneralArticle matchingArticle2 = new GeneralArticle("Horses", "something about them",
      LocalDateTime.of(2016, Month.JUNE, 15, 19, 30, 40),
      new URL("http://birds.com/"));
    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles2);

    List<Article> return2 = cachingProcessor.getArticles();
    assertEquals(1, return2.size());
    assertEquals(matchingArticle2, return2.get(0));
  }

  /**
   * Tests if cache is getting cleared out by newer articles.
   */
  @Test
  void filteringOut() throws MalformedURLException {
    NewsProcessor nestedProcessor = Mockito.mock(NewsProcessor.class);

    List<Article> articles1 = new LinkedList<>();
    articles1.add(new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/")));
    GeneralArticle matchingArticle = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));

    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles1);
    CachingNewsProcessor cachingProcessor = new CachingNewsProcessor(nestedProcessor);
    List<Article> return1 = cachingProcessor.getArticles();
    assertEquals(1, return1.size());
    assertEquals(matchingArticle, return1.get(0));

    List<Article> articles2 = new LinkedList<>();
    articles2.add(new GeneralArticle("Horses", "something about them",
      LocalDateTime.of(2014, Month.JUNE, 15, 19, 30, 40),
      new URL("http://birds.com/")));
    GeneralArticle matchingArticle2 = new GeneralArticle("Horses", "something about them",
      LocalDateTime.of(2014, Month.JUNE, 15, 19, 30, 40),
      new URL("http://birds.com/"));
    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles2);

    List<Article> return2 = cachingProcessor.getArticles();
    assertEquals(1, return2.size());
    assertEquals(matchingArticle2, return2.get(0));

    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles1);
    assertEquals(0, cachingProcessor.getArticles().size());
  }

  /**
   * Tests if cache is getting cleared out by newer articles.
   */
  @Test
  void cacheClearingOut() throws MalformedURLException {
    NewsProcessor nestedProcessor = Mockito.mock(NewsProcessor.class);

    List<Article> articles1 = new LinkedList<>();
    articles1.add(new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/")));
    GeneralArticle matchingArticle = new GeneralArticle("BBQ", "do you like BBQ?",
      LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
      new URL("http://jocko.com/"));

    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles1);
    CachingNewsProcessor cachingProcessor = new CachingNewsProcessor(nestedProcessor);
    List<Article> return1 =  cachingProcessor.getArticles();
    assertEquals(1, return1.size());
    assertEquals(matchingArticle, return1.get(0));

    List<Article> articles2 = new LinkedList<>();
    articles2.add(new GeneralArticle("Horses", "something about them",
      LocalDateTime.of(2016, Month.JUNE, 15, 19, 30, 40),
      new URL("http://birds.com/")));
    GeneralArticle matchingArticle2 = new GeneralArticle("Horses", "something about them",
      LocalDateTime.of(2016, Month.JUNE, 15, 19, 30, 40),
      new URL("http://birds.com/"));
    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles2);

    List<Article> return2 =  cachingProcessor.getArticles();
    assertEquals(1, return2.size());
    assertEquals(matchingArticle2, return2.get(0));

    Mockito.when(nestedProcessor.getArticles()).thenReturn(articles1);
    List<Article> return3 =  cachingProcessor.getArticles();
    assertEquals(1, return3.size());
    assertEquals(matchingArticle, return3.get(0));
  }
}