package edu.calpoly.csc305.newsextractor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Url-based news source.
 */
public class UrlNewsSource implements NewsSource {
  private final URL url;

  /**
   * Creates an instance of UrlNewsSource.
   *
   * @param url string url to access json from url string.
   */
  public UrlNewsSource(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  /**
   * Extracts json from the source and returns it as a String.
   *
   * @return json String
   */
  @Override
  public String getString() throws IOException {
    byte[] bytes;
    try (InputStream in = url.openStream()) {
      bytes = in.readAllBytes();
    }
    return new String(bytes);
  }
}
