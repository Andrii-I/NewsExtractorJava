package edu.calpoly.csc305.newsextractor;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Drives NewsConfigParser.
 */
public class ConfigParserDriver {
  /**
   * Prints Articles in format defined by the specification.
   *
   * @param articles List of Articles to be printed.
   */
  private static void printArticles(List<Article> articles) {
    if (articles.isEmpty()) {
      return;
    }

    StringBuilder str = new StringBuilder();
    for (Article article : articles) {
      str.append(article.getTitle()).append("\n").append(article.getDescription()).append("\n")
        .append(article.getPublishedAt().toString()).append("\n")
        .append(article.getUrl().toString()).append("\n\n");
    }

    System.out.println(str.toString());
  }

  private static List<NewsProcessor> extractProcessors(String configFile, Logger logger) {
    ConfigParser configParser = new NewsConfigParser(configFile, logger);
    return new LinkedList<>(configParser.getProcessors());
  }

  private static List<Article> extractArticles(List<NewsProcessor> processors) {
    List<Article> articles = new LinkedList<>();
    for (NewsProcessor processor : processors) {
      articles.addAll(processor.getArticles());
    }
    return articles;
  }

  /**
   * Accepts paths to config files as arguments, extracts Articles from config files, and prints
   * the articles.
   *
   * @param  args  paths to config files from project root folder.
   */
  public static void main(String[] args) {

    Logger logger = Logger.getLogger(ConfigParserDriver.class.getName() + ".configParserDriver");
    List<NewsProcessor> processors = new LinkedList<>();

    for (String configFile : args) {
      processors.addAll(extractProcessors(configFile, logger));
    }
    List<Article> articles = extractArticles(processors);
    printArticles(articles);
  }


}
