package edu.calpoly.csc305.newsextractor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import java.time.LocalDateTime;
import org.jetbrains.annotations.NotNull;

/**
 * General type of Articles containing title, description, date and time of publishing, url.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralArticle implements Article {
  private final String title;
  private final String description;
  private final LocalDateTime publishedAt;
  private final URL url;

  /**
   * Stores data about the Article extracted from JSON file(s), depends on Awards class.
   * Accordingly to specification, only cares about title, description, publishedAt, and url
   * fields. Therefore makes sure they are not Null and are of appropriate data type.
   *
   * @param title       title of the article
   * @param description description of the article
   * @param publishedAt date and time when the article was published
   * @param url         hyperlink to the article
   */
  public GeneralArticle(@JsonProperty("title") @NotNull String title,
                        @JsonProperty("description") @NotNull String description,
                        @JsonProperty("publishedAt") @NotNull LocalDateTime publishedAt,
                        @JsonProperty("url") @NotNull URL url) {
    this.title = title;
    this.description = description;
    this.publishedAt = publishedAt;
    this.url = url;
  }

  /**
   * Getter for Title.
   *
   * @return title String
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Getter for description.
   *
   * @return Description String
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Getter for PublishedAt date and time of publishing of the article .
   *
   * @return PublishedAt date and time of publishing
   */
  public LocalDateTime getPublishedAt() {
    return this.publishedAt;
  }

  /**
   * Getter for URL of the article.
   *
   * @return URL of the article
   */
  public URL getUrl() {
    return this.url;
  }

}
