package io.ic1101.playperms.spigot.sign.models;

import io.ic1101.playperms.library.utils.Document;
import lombok.Data;

@Data
public class RankSign {

  private final String uuid;
  private final String world;
  private final int x;
  private final int y;
  private final int z;

  public static Document toDocument(RankSign rankSign) {
    return new Document()
        .appendString("uuid", rankSign.getUuid())
        .appendString("world", rankSign.getWorld())
        .appendInt("x", rankSign.getX())
        .appendInt("y", rankSign.getY())
        .appendInt("z", rankSign.getZ());
  }

  public static RankSign fromDocument(Document document) {
    return new RankSign(
        document.getStringValue("uuid"),
        document.getStringValue("world"),
        document.getIntValue("x"),
        document.getIntValue("y"),
        document.getIntValue("z"));
  }
}
