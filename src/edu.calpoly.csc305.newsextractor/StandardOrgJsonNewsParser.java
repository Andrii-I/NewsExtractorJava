package edu.calpoly.csc305.newsextractor;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Parser for the articles of the standard format.
 */
public class StandardOrgJsonNewsParser implements NewsParser {
  private final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
      .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
      .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
      .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
      .optionalStart().appendOffset("+HH", "Z").optionalEnd()
      .toFormatter();


  /**
   * Creates a collection of Articles extracted from JSON string of standard format.
   *
   * @param json JSON String to be parsed
   * @param logger logger for logging
   * @return List of articles
   */
  public List<Article> getArticles(String json, Logger logger) {
    JSONTokener jt = new JSONTokener(json);
    JSONObject jo = new JSONObject(jt);
    JSONArray articleJsonArray = jo.getJSONArray("articles");

    return extractGeneralArticles(logger, articleJsonArray, dateFormatter);
  }

  private static List<Article>  extractGeneralArticles(Logger logger,
                                                              JSONArray articleJsonArray,
                                                              DateTimeFormatter dateFormatter) {
    List<Article> articleList = new LinkedList<>();
    for (int i = 0; i < articleJsonArray.length(); i++) {
      try {
        articleList.add(new GeneralArticle(articleJsonArray.getJSONObject(i).getString("title"),
            articleJsonArray.getJSONObject(i).getString("description"),
            LocalDateTime.parse(articleJsonArray.getJSONObject(i).getString("publishedAt"),
              dateFormatter),
            new URL(articleJsonArray.getJSONObject(i).getString("url"))));
      } catch (Exception e) {
        logger.warning(e.getClass().getSimpleName());
      }
    }
    return articleList;
  }

}
