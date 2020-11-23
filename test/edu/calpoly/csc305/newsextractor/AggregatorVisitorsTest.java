package edu.calpoly.csc305.newsextractor;

import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigLexer;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.visitors.AggregatorProcessorListVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    List<NewsProcessor> processors =
        parser.sources().accept(new AggregatorProcessorListVisitor(logger));

    assertEquals(3, processors.size());

    assertEquals(FileNewsSource.class, processors.get(0).getSource().getClass());
    assertEquals(StandardOrgJsonNewsParser.class, processors.get(0).getParser().getClass());

    assertEquals(UrlNewsSource.class, processors.get(1).getSource().getClass());
    assertEquals(StandardOrgJsonNewsParser.class, processors.get(1).getParser().getClass());

    assertEquals(FileNewsSource.class, processors.get(2).getSource().getClass());
    assertEquals(SimpleOrgJsonNewsParser.class, processors.get(2).getParser().getClass());
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

    String config = new String();
    CommonTokenStream tokens =
        new CommonTokenStream(new AggregatorConfigLexer(CharStreams.fromString(config)));
    AggregatorConfigParser parser = new AggregatorConfigParser(tokens);
    List<NewsProcessor> processors =
        parser.sources().accept(new AggregatorProcessorListVisitor(logger));

    assertEquals(0, processors.size());
  }
}