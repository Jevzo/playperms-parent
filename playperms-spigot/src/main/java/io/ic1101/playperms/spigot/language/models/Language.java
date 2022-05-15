package io.ic1101.playperms.spigot.language.models;

import io.ic1101.playperms.library.utils.Document;
import lombok.Data;

@Data
public class Language {

  private final String prefix;
  private final String noPermission;
  private final String notEnoughArgs;
  private final String setSignPlayerNotFound;
  private final String setSignMaterialAir;
  private final String setSignSuccessful;
  private final String removeSignSuccessful;
  private final String errorMessage;

  public static Language fromDocument(Document document) {
    return new Language(
        document.getStringValue("prefix"),
        document.getStringValue("noPermission"),
        document.getStringValue("notEnoughArgs"),
        document.getStringValue("setSignPlayerNotFound"),
        document.getStringValue("setSignMaterialAir"),
        document.getStringValue("setSignSuccessful"),
        document.getStringValue("removeSignSuccessful"),
        document.getStringValue("errorMessage"));
  }

  public static Document toDocument(Language language) {
    return new Document()
        .appendString("prefix", language.getPrefix())
        .appendString("noPermission", language.getNoPermission())
        .appendString("notEnoughArgs", language.getNotEnoughArgs())
        .appendString("setSignPlayerNotFound", language.getSetSignPlayerNotFound())
        .appendString("setSignMaterialAir", language.getSetSignMaterialAir())
        .appendString("setSignSuccessful", language.getSetSignSuccessful())
        .appendString("removeSignSuccessful", language.getRemoveSignSuccessful())
        .appendString("errorMessage", language.getErrorMessage());
  }

  public static Language getEnDefault() {
    return new Language(
        "§8[§cPlayPerms§8]§7",
        "%prefix% You are not allowed to execute this command!",
        "%prefix% Command not known! Try /setSign <playerName>",
        "%prefix% Player was not found... Please try again.",
        "%prefix% Please look at a block.",
        "%prefix% The sign was created and will update on changes.",
        "%prefix% The sign was removed from the config files.",
        "%prefix% An error occurred while executing the command!");
  }

  public static Language getDeDefault() {
    return new Language(
        "§8[§cPlayPerms§8]§7",
        "%prefix% Du hast leider keine Berechtigung diesen Befehl auszuführen!",
        "%prefix% Befehl nicht gefunden! Probiere doch /setSign <playerName>",
        "%prefix% Der Spieler wurde nicht gefunden... Probiere es erneut.",
        "%prefix% Bitte schaue auf einen Block.",
        "%prefix% Das Schild wurde erstellt und bleibt aktuell bei veränderungen.",
        "%prefix% Das Schild aus der Konfiguration gelöscht.",
        "%prefix% Ein Fehler ist beim ausführen des Befehls aufgetreten!");
  }
}
