package edu.calpoly.csc305.newsextractor;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Runnable class that prints Articles when they are available in Underlying BlockingQueue.
 */
public class ArticlePrinter implements Runnable {
  private final BlockingQueue<Article> queue;
  private final Logger logger;

  /**
   * Creates an instance of Article Printer.
   *
   * @param queue Blocking Que to get Articles from to be printed.
   */
  public ArticlePrinter(BlockingQueue<Article> queue, Logger logger) {
    this.queue = queue;
    this.logger = logger;
  }

  /**
   * When an object implementing interface {@code Runnable} is used
   * to create a thread, starting the thread causes the object's
   * {@code run} method to be called in that separately executing
   * thread.
   * The general contract of the method {@code run} is that it may
   * take any action whatsoever.
   * Comment: I used "Thread.currentThread().interrupt();" because there is no critical
   * section after the interruption and the function will terminate naturally; also because
   * SonarLint suggested it. Also I have spent some time researching online and this way is
   * considered appropriate accordingly to it.
   */
  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        printArticle(queue.take());
      } catch (InterruptedException e) {
        logger.warning(e.getClass().getSimpleName());
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Prints Articles in format defined by the specification.
   *
   * @param article Article to be printed.
   */
  private static void printArticle(Article article) {
    StringBuilder str = new StringBuilder();
    str.append(article.getTitle()).append("\n").append(article.getDescription()).append("\n")
        .append(article.getPublishedAt().toString()).append("\n")
        .append(article.getUrl().toString()).append("\n\n");

    System.out.println(str.toString());
  }
}
