package io.ic1101.playperms.library.config;

import io.ic1101.playperms.library.utils.Document;
import lombok.Data;

@Data
public class DatabaseConfig {

  private final String host;
  private final int port;
  private final String database;
  private final String username;
  private final String password;

  private final boolean showSql;
  private final boolean formatSql;
  private final String hbmDdlAuto;

  public static DatabaseConfig fromDocument(Document document) {
    return new DatabaseConfig(
        document.getStringValue("host"),
        document.getIntValue("port"),
        document.getStringValue("database"),
        document.getStringValue("username"),
        document.getStringValue("password"),
        document.getBooleanValue("showSql"),
        document.getBooleanValue("formatSql"),
        document.getStringValue("hbmDdlAuto"));
  }

  public static Document toDocument(DatabaseConfig databaseConfig) {
    return new Document()
        .appendString("host", databaseConfig.getHost())
        .appendInt("port", databaseConfig.getPort())
        .appendString("database", databaseConfig.getDatabase())
        .appendString("username", databaseConfig.getUsername())
        .appendString("password", databaseConfig.getPassword())
        .appendBoolean("showSql", databaseConfig.isShowSql())
        .appendBoolean("formatSql", databaseConfig.isFormatSql())
        .appendString("hbmDdlAuto", databaseConfig.getHbmDdlAuto());
  }

  public static DatabaseConfig getDefault() {
    return new DatabaseConfig(
        "localhost", 3306, "playperms", "root", "SecurePassword", true, true, "update");
  }
}
