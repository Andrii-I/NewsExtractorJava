package edu.calpoly.csc305.newsextractor;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Caches the output Articles.
 */
public class CachingNewsProcessor implements NewsProcessor {
  private final NewsProcessor newsProcessor;
  private final List<Article> cache;

  /**
   * Creates an instance of Cached News Processor.
   *
   * @param newsProcessor original News Processor to get List of Articles from to cache
   */
  public CachingNewsProcessor(NewsProcessor newsProcessor) {
    this.newsProcessor = newsProcessor;
    this.cache = new LinkedList<>();
  }

  /**
   * Extracts articles from json of standard format from source and returns them as a list.
   *
   * @return List of GeneralArticles extracted from the source
   */
  @Override
  public List<Article> getArticles() {
    List<Article> articles = newsProcessor.getArticles();
    List<Article> filteredArticles = articles.stream().filter(Predicate.not(this::cacheContains)).
      collect(Collectors.toList());
    updateCache(articles);
    return filteredArticles;
  }

  private void updateCache(List<Article> articles) {
    if (articles.isEmpty()) {
      return;
    }
    LocalDateTime earliestInBatch = LocalDateTime.MAX;
    for (Article article : articles) {
      if (article.getPublishedAt().isBefore(earliestInBatch) ) {
        earliestInBatch = article.getPublishedAt();
      }
    }
    for (int i = 0; i < cache.size(); i++) {
      System.out.println();
      if (cache.get(i).getPublishedAt().isBefore(earliestInBatch)) {
        cache.remove(i);
      }
    }
    cache.addAll(articles);
  }

  private boolean cacheContains(Article article) {
    for (Article cachedArticle : cache) {
      GeneralArticle generalCachedArticle = (GeneralArticle)cachedArticle;
      GeneralArticle generalArticle = (GeneralArticle)article;
      if (generalCachedArticle.equals(generalArticle)) {
        return true;
      }
    }
    return false;
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
}
