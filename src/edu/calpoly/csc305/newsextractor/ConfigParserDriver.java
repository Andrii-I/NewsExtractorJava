package edu.calpoly.csc305.newsextractor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
   * Comment: I tried to replace second for loop with a stream but it was not saving space and
   * was drastically reducing readability in my opinion so I decided to go with the for loop.
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

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(
        Runtime.getRuntime().availableProcessors() * 10);

    for (ProcessorScheduler scheduler : processorsSchedulers) {
      if (scheduler.getDelay() != 0) {
        executor.scheduleWithFixedDelay(scheduler, 0, scheduler.getDelay().longValue(),
            TimeUnit.SECONDS);
      } else {
        scheduler.run();
      }
    }
    new Thread(new ArticlePrinter(blockingQueue, logger)).start();
  }


}
