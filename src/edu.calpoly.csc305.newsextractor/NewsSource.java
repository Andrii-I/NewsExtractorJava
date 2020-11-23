package edu.calpoly.csc305.newsextractor;

import java.io.IOException;

/**
 * This is an interface for different types of News Sources for json extraction.
 */
public interface NewsSource {

  /**
   * Extracts json from the source and returns it as a String.
   *
   * @return json String
   */
  String getString() throws IOException;
}
