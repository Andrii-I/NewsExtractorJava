package edu.calpoly.csc305.newsextractor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Caches the output Articles.
 */
public class CachingNewsProcessor implements NewsProcessor {
  private final NewsProcessor newsProcessor;
  private final HashSet<Article> cache;

  /**
   * Creates an instance of Cached News Processor.
   *
   * @param newsProcessor original News Processor to get List of Articles from to cache
   */
  public CachingNewsProcessor(NewsProcessor newsProcessor) {
    this.newsProcessor = newsProcessor;
    this.cache = new HashSet<>();
  }

  /**
   * Extracts articles from json of standard format from source, checks them against previously
   * cached articles, updates cache, and returns Articles that are new (no match against cache)
   * as a list.
   * Due to the caching each next call will return only new articles.
   *
   * @return List of Articles extracted from the source
   */
  @Override
  public List<Article> getArticles() {
    List<Article> articles = newsProcessor.getArticles();
    List<Article> filteredArticles = articles.stream().filter(Predicate.not(cache::contains))
        .collect(Collectors.toList());
    updateCache(articles);

    return filteredArticles;
  }

  /**
   * Helper function that encapsulates cache update logic: finds earliest date in the most recent
   * batch of Articles (passed as argument), iterates through cache and deletes all articles
   * published earlier than the earliest date, adds all articles from the latest batch to the
   * cache.
   * Comment: I tried converting for loops into streams with use of inline lambda and inline if
   * (?) functions but in my opinion it drastically reduces readability and future maintainability
   * so I chose to revert back
   * to using 2 for loops.
   *
   * @param articles Articles to update cache with
   */
  private void updateCache(List<Article> articles) {
    if (articles.isEmpty()) {
      return;
    }
    LocalDateTime earliestInBatch = LocalDateTime.MAX;
    for (Article article : articles) {
      if (article.getPublishedAt().isBefore(earliestInBatch)) {
        earliestInBatch = article.getPublishedAt();
      }
    }

    for (Iterator<Article> i = cache.iterator(); i.hasNext();) {
      Article article = i.next();
      if (article.getPublishedAt().isBefore(earliestInBatch)) {
        i.remove();
      }
    }

    cache.addAll(articles);
  }

  /**
   * Source getter.
   *
   * @return news source.
   */
  @Override
  public NewsSource getSource() {
    return this.newsProcessor.getSource();
  }

  /**
   * Parser getter.
   *
   * @return parser.
   */
  @Override
  public NewsParser getParser() {
    return this.newsProcessor.getParser();
  }

  /**
   * Logger getter.
   *
   * @return logger.
   */
  @Override
  public Logger getLogger() {
    return newsProcessor.getLogger();
  }
}
