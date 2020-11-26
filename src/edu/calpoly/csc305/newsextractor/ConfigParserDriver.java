package edu.calpoly.csc305.newsextractor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

/**
 * Drives NewsConfigParser.
 */
public class ConfigParserDriver {
  private static List<ProcessorScheduler> extractProcessors(String configFile, Logger logger,
                                                       BlockingQueue<Article> queue) {
    ConfigParser configParser = new NewsConfigParser(configFile, logger, queue);
    return new LinkedList<>(configParser.getProcessors());
  }


  /**
   * Accepts paths to config files as arguments, extracts Articles from config files, and prints
   * the articles.
   *
   * @param  args  paths to config files from project root folder.
   */
  public static void main(String[] args) {
    BlockingQueue<Article> blockingQueue = new LinkedBlockingQueue<>();
    Logger logger = Logger.getLogger(ConfigParserDriver.class.getName() + ".configParserDriver");
    List<ProcessorScheduler> processorsSchedulers = new LinkedList<>();

    for (String configFile : args) {
      processorsSchedulers.addAll(extractProcessors(configFile, logger, blockingQueue));
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
      Runtime.getRuntime().availableProcessors() * 10);



    new Thread(new ArticlePrinter(blockingQueue, logger)).start();
  }


}
