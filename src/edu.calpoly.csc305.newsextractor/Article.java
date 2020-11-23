package edu.calpoly.csc305.newsextractor;

import java.net.URL;
import java.time.LocalDateTime;

/**
 * This is an interface for different types of Articles.
 */
public interface Article {
  /**
   * Getter for Title.
   *
   * @return title String
   */
  String getTitle();

  /**
   * Getter for description.
   *
   * @return Description String
   */
  String getDescription();

  /**
   * Getter for PublishedAt date and time of publishing of the article .
   *
   * @return PublishedAt date and time of publishing
   */
  LocalDateTime getPublishedAt();

  /**
   * Getter for URL of the article.
   *
   * @return URL of the article
   */
  URL getUrl();
}
