package edu.calpoly.csc305.newsextractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests NewsApiParser.
 */
class StandardOrgJsonParserTest {

  /**
   * Tests with correct mocked Json string as an input.
   */
  @Test
  void correctInput() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode article1 = mapper.createObjectNode();
    article1.put("title", "Java into Json is not as easy.");
    article1.put("description", "It turned out that the process is hard.");
    article1.put("publishedAt", "2020-09-21T19:58:27Z");
    article1.put("url", "http://example.com/");

    ObjectNode article2 = mapper.createObjectNode();
    article2.put("title", "Is grass the only ethical thing to eat?");
    article2.put("description", "Is the way that agriculture is set up, makes grass the only?");
    article2.put("publishedAt", "2020-10-21T19:58:27Z");
    article2.put("url", "http://example2.com/");

    ArrayNode articlesArray = mapper.createArrayNode();
    articlesArray.add(article1);
    articlesArray.add(article2);

    ObjectNode articleCollection = mapper.createObjectNode();
    articleCollection.putPOJO("articles", articlesArray);
    articleCollection.put("status", "ok");
    articleCollection.put("totalResults", 2);

    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(articleCollection);

    Logger logger = Logger.getLogger(NewsParser.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    NewsParser parser = new StandardOrgJsonNewsParser();

    List<Article> articles = parser.getArticles(content, logger);

    assertEquals("Java into Json is not as easy.", articles.get(0).getTitle());
    assertEquals("It turned out that the process is hard.",
        articles.get(0).getDescription());
    assertEquals("2020-09-21T19:58:27", articles.get(0).getPublishedAt().toString());
    assertEquals("http://example.com/", articles.get(0).getUrl().toString());

    assertEquals("Is grass the only ethical thing to eat?", articles.get(1).getTitle());
    assertEquals("Is the way that agriculture is set up, makes grass the only?",
        articles.get(1).getDescription());
    assertEquals("2020-10-21T19:58:27", articles.get(1).getPublishedAt().toString());
    assertEquals("http://example2.com/", articles.get(1).getUrl().toString());

    assertEquals(0, handler.getLogList().size());
  }


  /**
   * Tests json with missing fields.
   */
  @Test
  void incorrectInputMissingFields() throws IOException {
    ObjectMapper mapper = new ObjectMapper();



    ObjectNode article1 = mapper.createObjectNode();
    article1.put("description", "It turned out that the process is hard.");
    article1.put("publishedAt", "2020-09-21T19:58:27Z");
    article1.put("url", "http://example.com/");

    ObjectNode article2 = mapper.createObjectNode();
    article2.put("title", "Is grass the only ethical thing to eat?");
    article2.put("description", "Is the way that agriculture is set up, makes grass the only?");
    article2.put("url", "http://example2.com/");

    ObjectNode article3 = mapper.createObjectNode();
    article3.put("title", "Is grass the only ethical thing to eat?");
    article3.put("description", "Is the way that agriculture is set up, makes grass the only?");
    article3.put("publishedAt", "2020-10-21T19:58:27Z");

    ArrayNode articlesArray = mapper.createArrayNode();
    articlesArray.add(article1);
    articlesArray.add(article2);
    articlesArray.add(article3);

    ObjectNode articleCollection = mapper.createObjectNode();
    articleCollection.putPOJO("articles", articlesArray);
    articleCollection.put("status", "ok");
    articleCollection.put("totalResults", 3);

    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(articleCollection);

    Logger logger = Logger.getLogger(NewsParser.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    NewsParser parser = new StandardOrgJsonNewsParser();

    List<Article> articles = parser.getArticles(content, logger);

    assertEquals(0, articles.size());
    assertEquals(3, handler.getLogList().size());
    assertEquals("JSONException", handler.getLogList().get(0));
    assertEquals("JSONException", handler.getLogList().get(1));
    assertEquals("JSONException", handler.getLogList().get(2));
  }

  /**
   * Tests with integer instead of title in the 1st article, with random string instead of properly
   * formatted publishedAt date-time string.
   */
  @Test
  void incorrectInputInvalidTypes() throws IOException {
    ObjectMapper mapper = new ObjectMapper();



    ObjectNode article1 = mapper.createObjectNode();
    article1.put("title", 1);
    article1.put("description", "It turned out that the process is hard.");
    article1.put("publishedAt", "2020-09-21T19:58:27Z");
    article1.put("url", "http://example.com/");

    ObjectNode article2 = mapper.createObjectNode();
    article2.put("title", "Is grass the only ethical thing to eat?");
    article2.put("description", "Is the way that agriculture is set up, makes grass the only?");
    article2.put("publishedAt", "karamba");
    article2.put("url", "http://example2.com/");


    article2.put("title", "Is grass the only ethical thing to eat?");
    article2.put("description", (byte[]) null);
    article2.put("publishedAt", "2020-09-21T19:58:27Z");
    article2.put("url", "http://example3.com/");

    ArrayNode articlesArray = mapper.createArrayNode();
    articlesArray.add(article1);
    articlesArray.add(article2);
    ObjectNode article3 = mapper.createObjectNode();
    articlesArray.add(article3);

    ObjectNode articleCollection = mapper.createObjectNode();
    articleCollection.putPOJO("articles", articlesArray);
    articleCollection.put("status", "ok");
    articleCollection.put("totalResults", 3);

    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(articleCollection);

    Logger logger = Logger.getLogger(NewsParser.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);
    NewsParser parser = new StandardOrgJsonNewsParser();

    List<Article> articles = parser.getArticles(content, logger);

    assertEquals(0, articles.size());
    assertEquals(3, handler.getLogList().size());
    assertEquals("JSONException", handler.getLogList().get(0));
    assertEquals("JSONException", handler.getLogList().get(1));
    assertEquals("JSONException", handler.getLogList().get(2));
  }
}