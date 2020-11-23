package edu.calpoly.csc305.newsextractor;

import java.util.List;

/**
 * This is an interface for different types of config parsers.
 */
public interface ConfigParser {

  /**
   * Extracts List of Processors from config files.
   *
   * @return List of Processors.
   */
  List<NewsProcessor> getProcessors();
}
