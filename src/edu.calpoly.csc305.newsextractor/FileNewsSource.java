package edu.calpoly.csc305.newsextractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File-based news source.
 */
public class FileNewsSource implements NewsSource {
  private final Path path;


  /**
   * Creates an instance of FileNewsSource.
   *
   * @param path path to the source file relative to the project root.
   */
  public FileNewsSource(String path) {
    this.path = Paths.get(path);
  }

  /**
   * Extracts json from the source and returns it as a String.
   *
   * @return json String
   */
  @Override
  public String getString() throws IOException {
    return new String(Files.readAllBytes(path));
  }
}
