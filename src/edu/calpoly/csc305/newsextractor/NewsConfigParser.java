package edu.calpoly.csc305.newsextractor;

import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigLexer;
import edu.calpoly.csc305.newsextractor.config.grammars.AggregatorConfigParser;
import edu.calpoly.csc305.newsextractor.config.visitors.AggregatorProcessorListVisitor;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Parses news config files and returns Processors.
 */
public class NewsConfigParser implements ConfigParser {
  private final String configFilePath;
  private final Logger logger;
  private final BlockingQueue<Article> queue;

  /**
   * Creates an instance of NewsConfigParser.
   *
   * @param configFilePath path to config file relative to project root folder.
   * @param logger         Logger for logging.
   */
  public NewsConfigParser(String configFilePath, Logger logger, BlockingQueue<Article> queue) {
    this.configFilePath = configFilePath;
    this.logger = logger;
    this.queue = queue;
  }

  /**
   * Extracts List of Processors from config files.
   *
   * @return List of Processors.
   */
  @Override
  public List<ProcessorScheduler> getProcessors() {
    Optional<ParseTree> parseTree = parseFile(configFilePath, logger);
    if (parseTree.isPresent()) {
      return parseTree.get().accept(new AggregatorProcessorListVisitor(logger, queue));
    }
    return new LinkedList<>();
  }

  private static Optional<ParseTree> parseFile(String filename, Logger logger) {
    Optional<ParseTree> parseTree = Optional.empty();
    try {
      CommonTokenStream tokens =
          new CommonTokenStream(new AggregatorConfigLexer(CharStreams.fromFileName(filename)));
      AggregatorConfigParser parser = new AggregatorConfigParser(tokens);
      parseTree = Optional.of(parser.sources());

      if (parser.getNumberOfSyntaxErrors() == 0) {
        return parseTree;
      }
    } catch (IOException e) {
      logger.warning(e.getMessage());
    }

    return parseTree;
  }
}
