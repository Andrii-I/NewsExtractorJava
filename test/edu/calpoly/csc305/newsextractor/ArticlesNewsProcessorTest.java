package edu.calpoly.csc305.newsextractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests news articles processor.
 */
class ArticlesNewsProcessorTest {

  /**
   * Tests with correct source/input.
   */
  @Test
  void correctSource() throws IOException {
    Logger logger = Logger.getLogger(ConfigParserDriver.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);


    ObjectMapper mapper = new ObjectMapper();
    ObjectNode article = mapper.createObjectNode();
    article.put("title", "It turned out that it's hard");
    article.put("description", "It turned out that the process is pretty hard.");
    article.put("publishedAt", "2020-10-04 11:50:17.601812");
    article.put("url", "http://somewebsite.com/");
    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);
    NewsSource sourceMock = Mockito.mock(NewsSource.class);
    Mockito.when(sourceMock.getString()).thenReturn(content);

    NewsParser parserMock = Mockito.mock(NewsParser.class);
    GeneralArticle generalArticle = new GeneralArticle("It turned out that it's hard",
        "It turned out that the process is pretty hard.",
        LocalDateTime.parse("2020-10-04 11:50:17.601812".replace(" ", "T")),
        new URL("http://somewebsite.com/"));
    List<Article> articleList = new LinkedList<>();
    articleList.add(generalArticle);
    Mockito.when(parserMock.getArticles(content, logger)).thenReturn(articleList);

    NewsProcessor processor = new ArticlesNewsProcessor(sourceMock, parserMock, logger);
    List<Article> output = processor.getArticles();

    assertEquals(1, output.size());
    assertEquals(0, handler.getLogList().size());

    assertEquals("It turned out that it's hard", output.get(0).getTitle());
    assertEquals("It turned out that the process is pretty hard.",
        output.get(0).getDescription());
    assertEquals("2020-10-04T11:50:17.601812", output.get(0).getPublishedAt().toString());
    assertEquals("http://somewebsite.com/", output.get(0).getUrl().toString());
  }

  /**
   * Tests with incorrect source.
   */
  @Test
  void incorrectSource() throws IOException {
    Logger logger = Logger.getLogger(ConfigParserDriver.class.getName() + ".loggerTest");
    logger.setUseParentHandlers(false);
    CustomHandler handler = new CustomHandler();
    logger.addHandler(handler);


    ObjectMapper mapper = new ObjectMapper();
    ObjectNode article = mapper.createObjectNode();
    article.put("title", "It turned out that it's hard");
    article.put("description", "It turned out that the process is pretty hard.");
    article.put("publishedAt", "2020-10-04 11:50:17.601812");
    article.put("url", "http://somewebsite.com/");
    String content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);
    NewsSource sourceMock = Mockito.mock(NewsSource.class);
    Mockito.when(sourceMock.getString()).thenThrow(MalformedURLException.class);

    NewsParser parserMock = Mockito.mock(NewsParser.class);
    GeneralArticle generalArticle = new GeneralArticle("It turned out that it's hard",
        "It turned out that the process is pretty hard.",
        LocalDateTime.parse("2020-10-04 11:50:17.601812".replace(" ", "T")),
        new URL("http://somewebsite.com/"));
    List<Article> articleList = new LinkedList<>();
    articleList.add(generalArticle);
    Mockito.when(parserMock.getArticles(content, logger)).thenReturn(articleList);

    NewsProcessor processor = new ArticlesNewsProcessor(sourceMock, parserMock, logger);
    List<Article> output = processor.getArticles();

    assertEquals(0, output.size());
    assertEquals(1, handler.getLogList().size());
    assertEquals("MalformedURLException", handler.getLogList().get(0));
  }
}