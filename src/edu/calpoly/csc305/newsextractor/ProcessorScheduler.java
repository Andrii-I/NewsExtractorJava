package edu.calpoly.csc305.newsextractor;

import java.util.concurrent.BlockingQueue;

/**
 * Runnable Processor Scheduler class for scheduling Articles extraction with delay.
 */
public class ProcessorScheduler implements Runnable {
  private final Integer delay;
  private final NewsProcessor processor;
  private final BlockingQueue<Article> queue;

  /**
   * Creates an instance of Processor Scheduler.
   *
   * @param processor NewsProcessor to extract Articles from
   * @param queue BlockingQueue to add extracted Articles to
   * @param delay delay between Article extraction runs
   */
  public ProcessorScheduler(NewsProcessor processor, BlockingQueue<Article> queue, int delay) {
    this.processor = processor;
    this.queue = queue;
    this.delay = delay;
  }

  /**
   * When an object implementing interface {@code Runnable} is used
   * to create a thread, starting the thread causes the object's
   * {@code run} method to be called in that separately executing
   * thread.
   * The general contract of the method {@code run} is that it may
   * take any action whatsoever.
   * Comment: I used "Thread.currentThread().interrupt();" because there is no more critical
   * section after the interruption and also because SonarLint suggested it.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    processor.getArticles().forEach(x -> {
      try {
        queue.put(x);
      } catch (InterruptedException e) {
        processor.getLogger().warning(e.getClass().getSimpleName());
        Thread.currentThread().interrupt();
      }
    });
  }

  /**
   * Execution delay getter.
   *
   * @return Integer signifying execution in seconds
   */
  public Integer getDelay() {
    return this.delay;
  }
}
