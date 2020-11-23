package edu.calpoly.csc305.newsextractor;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Processes messages from logger.
 */
public class CustomHandler extends Handler {
  private final List<String> logList = new LinkedList<>();

  /**
   * Put message from LogRecord into the list.
   *
   * @param record LogRecord with message to be logged.
   */
  public void publish(LogRecord record) {
    logList.add(record.getMessage());
  }

  /**
   * Flushes handler. Does nothing because no need to flush ATM.
   */
  @Override
  public void flush() {
    // Do nothing because no need to flush ATM.
  }

  /**
   * Closes handler. Does nothing because no need to flush ATM.
   */
  @Override
  public void close() {
    // Do nothing because no need to flush ATM.
  }

  /**
   * Gets list of logs detected Errors.
   *
   * @return List of String logs.
   */
  public List<String> getLogList() {
    return this.logList;
  }
}