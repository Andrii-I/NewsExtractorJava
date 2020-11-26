package edu.calpoly.csc305.newsextractor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

/**
 * Tests NewsApiParser.
 */
class SimpleOrgJsonNewsParserTest {

  /**
   * Tests with correct mocked Json string as an input.
   */
  @Test
  void correctInput() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode article = mapper.createObjectNode();
    article.put("title", "Java into Json is not as easy.");
    article.put("description", "It turned out that the process is hard.");
    article.put("publishedAt", "2020-10-04 11:50:17.601812");
    article.put("url", "http://example.com/");
    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);

    Logger logger = Logger.getLogger(NewsParser.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    NewsParser parser = new SimpleOrgJsonNewsParser();

    List<Article> articles = parser.getArticles(content, logger);


    assertEquals("Java into Json is not as easy.", articles.get(0).getTitle());
    assertEquals("It turned out that the process is hard.",
        articles.get(0).getDescription());
    assertEquals("2020-10-04T11:50:17.601812", articles.get(0).getPublishedAt().toString());
    assertEquals("http://example.com/", articles.get(0).getUrl().toString());

    assertEquals(0, handler.getLogList().size());
  }


  /**
   * Tests json with missing field.
   */
  @Test
  void incorrectInputMissingFields() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode article = mapper.createObjectNode();
    article.put("title", "Java into Json is not as easy.");
    article.put("description", "It turned out that the process is hard.");
    article.put("url", "http://example.com/");
    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);

    Logger logger = Logger.getLogger(NewsParser.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    NewsParser parser = new SimpleOrgJsonNewsParser();

    List<Article> articles = parser.getArticles(content, logger);

    assertEquals(0, articles.size());
    assertEquals(1, handler.getLogList().size());
    assertEquals("JSONException", handler.getLogList().get(0));
  }

  /**
   * Tests with integer instead of title.
   */
  @Test
  void incorrectInputInvalidTypes() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode article = mapper.createObjectNode();
    article.put("title", 1);
    article.put("description", "It turned out that the process is hard.");
    article.put("publishedAt", "2020-10-04 11:50:17.601812");
    article.put("url", "http://example.com/");
    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);

    Logger logger = Logger.getLogger(NewsParser.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    NewsParser parser = new SimpleOrgJsonNewsParser();

    List<Article> articles = parser.getArticles(content, logger);

    assertEquals(0, articles.size());
    assertEquals(1, handler.getLogList().size());
    assertEquals("JSONException", handler.getLogList().get(0));
  }
}