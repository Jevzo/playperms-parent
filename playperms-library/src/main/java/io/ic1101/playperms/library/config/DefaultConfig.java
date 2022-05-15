package io.ic1101.playperms.library.config;

import io.ic1101.playperms.library.utils.Document;
import lombok.Data;

@Data
public class DefaultConfig {

  private final String language;

  private final DatabaseConfig databaseConfig;

  public static DefaultConfig fromDocument(Document document) {
    return new DefaultConfig(
        document.getStringValue("language"),
        DatabaseConfig.fromDocument(document.getDocument("databaseConfig")));
  }

  public static Document toDocument(DefaultConfig defaultConfig) {
    return new Document()
        .appendString("language", defaultConfig.getLanguage())
        .appendDocument(
            "databaseConfig", DatabaseConfig.toDocument(defaultConfig.getDatabaseConfig()));
  }

  public static DefaultConfig getDefault() {
    return new DefaultConfig("en", DatabaseConfig.getDefault());
  }
}
