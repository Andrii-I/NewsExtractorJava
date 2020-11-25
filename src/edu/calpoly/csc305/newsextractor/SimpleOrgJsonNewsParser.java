package edu.calpoly.csc305.newsextractor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *  Parser for the articles of the simple format.
 */
public class SimpleOrgJsonNewsParser implements NewsParser {

  /**
   * Creates a collection of Articles extracted from JSON string of simple format.
   *
   * @param json JSON String to be parsed
   * @param logger logger for logging
   * @return list of articles
   */
  public List<Article> getArticles(String json, Logger logger) {
    JSONTokener jt = new JSONTokener(json);
    JSONObject jo = new JSONObject(jt);
    List<Article> articleList = new LinkedList<>();

    try {
      articleList.add(new GeneralArticle(jo.getString("title"),
          jo.getString("description"),
          LocalDateTime.parse(jo.getString("publishedAt").replace(" ", "T")),
          new URL(jo.getString("url"))));
    } catch (Exception e) {
      logger.warning(e.getClass().getSimpleName());
    }

    return articleList;
  }
}
