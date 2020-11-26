package edu.calpoly.csc305.newsextractor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigLexer;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.visitors.AggregatorProcessorSchedulerListVisitor;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;


/**
 * Tests Aggregator Visitors.
 */
class AggregatorVisitorsTest {

  /**
   * Tests aggregator visitors with the config string of the correct format.
   */
  @Test
  void correctConfig() {
    Logger logger = Logger.getLogger(ConfigParserDriver.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    BlockingQueue<Article> queue = new LinkedBlockingQueue<>();

    StringBuilder config = new StringBuilder("\n");
    config.append("file\nname: NewsAPI Fixed\n").append("format: newsapi\n")
      .append("source: inputs/newsapi.json\n").append("filter:\n\n")
      .append("url\n")
      .append("name: NewsAPI Headlines (Live)\n").append("format: newsapi\n")
      .append("source:  http://newsapi.org/v2/top-headlines?country=us&apiKey"
        + "=9b6f008e0af3411e94c6a6c37ae88805\n").append("filter:\n\n")
      .append("file\n").append("name: Simple Fixed\n")
      .append("format: simple\n").append("source: inputs/simple.json\n")
      .append("filter:");

    CommonTokenStream tokens =
        new CommonTokenStream(new AggregatorConfigLexer(CharStreams.fromString(config.toString())));
    AggregatorConfigParser parser = new AggregatorConfigParser(tokens);
    List<ProcessorScheduler> processors =
        parser.sources().accept(new AggregatorProcessorSchedulerListVisitor(logger, queue));

    assertEquals(3, processors.size());
  }

  /**
   * Tests aggregator visitors with an empty config string.
   */
  @Test
  void emptyConfig() {
    Logger logger = Logger.getLogger(ConfigParserDriver.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    BlockingQueue<Article> queue = new LinkedBlockingQueue<>();

    CommonTokenStream tokens =
        new CommonTokenStream(new AggregatorConfigLexer(CharStreams.fromString(new String())));
    AggregatorConfigParser parser = new AggregatorConfigParser(tokens);
    List<ProcessorScheduler> processors =
        parser.sources().accept(new AggregatorProcessorSchedulerListVisitor(logger, queue));

    assertEquals(0, processors.size());
  }
}