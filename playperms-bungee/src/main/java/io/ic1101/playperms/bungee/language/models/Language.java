package io.ic1101.playperms.bungee.language.models;

import io.ic1101.playperms.library.utils.Document;
import lombok.Data;

@Data
public class Language {

  private final String prefix;
  private final String noPermission;
  private final String notEnoughArgs;
  private final String getPlayerGroupError;
  private final String getPlayerGroupSuccessful;
  private final String addPermissionError;
  private final String addPermissionSuccessful;
  private final String removePermissionError;
  private final String removePermissionSuccessful;
  private final String createGroupError;
  private final String createGroupSuccessful;
  private final String deleteGroupError;
  private final String deleteGroupSuccessful;
  private final String helpMessageAddGroupPermission;
  private final String helpMessageCreateGroup;
  private final String helpMessageDeleteGroup;
  private final String helpMessageGetPlayerGroup;
  private final String helpMessageRemoveGroupPermission;
  private final String helpMessageSetPlayerGroup;
  private final String helpMessageUpdateGroup;
  private final String incorrectDateFormat;
  private final String setPlayerGroupError;
  private final String setPlayerGroupSuccessful;
  private final String updateGroupError;
  private final String updateGroupSuccessful;

  public static Language fromDocument(Document document) {
    return new Language(
        document.getStringValue("prefix"),
        document.getStringValue("noPermission"),
        document.getStringValue("notEnoughArgs"),
        document.getStringValue("getPlayerGroupError"),
        document.getStringValue("getPlayerGroupSuccessful"),
        document.getStringValue("addPermissionError"),
        document.getStringValue("addPermissionSuccessful"),
        document.getStringValue("removePermissionError"),
        document.getStringValue("removePermissionSuccessful"),
        document.getStringValue("createGroupError"),
        document.getStringValue("createGroupSuccessful"),
        document.getStringValue("deleteGroupError"),
        document.getStringValue("deleteGroupSuccessful"),
        document.getStringValue("helpMessageAddGroupPermission"),
        document.getStringValue("helpMessageCreateGroup"),
        document.getStringValue("helpMessageDeleteGroup"),
        document.getStringValue("helpMessageGetPlayerGroup"),
        document.getStringValue("helpMessageRemoveGroupPermission"),
        document.getStringValue("helpMessageSetPlayerGroup"),
        document.getStringValue("helpMessageUpdateGroup"),
        document.getStringValue("incorrectDateFormat"),
        document.getStringValue("setPlayerGroupError"),
        document.getStringValue("setPlayerGroupSuccessful"),
        document.getStringValue("updateGroupError"),
        document.getStringValue("updateGroupSuccessful"));
  }

  public static Document toDocument(Language language) {
    return new Document()
        .appendString("prefix", language.getPrefix())
        .appendString("noPermission", language.getNoPermission())
        .appendString("notEnoughArgs", language.getNotEnoughArgs())
        .appendString("getPlayerGroupError", language.getGetPlayerGroupError())
        .appendString("getPlayerGroupSuccessful", language.getGetPlayerGroupSuccessful())
        .appendString("addPermissionError", language.getAddPermissionError())
        .appendString("addPermissionSuccessful", language.getAddPermissionSuccessful())
        .appendString("removePermissionError", language.getRemovePermissionError())
        .appendString("removePermissionSuccessful", language.getRemovePermissionSuccessful())
        .appendString("createGroupError", language.getCreateGroupError())
        .appendString("createGroupSuccessful", language.getCreateGroupSuccessful())
        .appendString("deleteGroupError", language.getDeleteGroupError())
        .appendString("deleteGroupSuccessful", language.getDeleteGroupSuccessful())
        .appendString("helpMessageAddGroupPermission", language.getHelpMessageAddGroupPermission())
        .appendString("helpMessageCreateGroup", language.getHelpMessageCreateGroup())
        .appendString("helpMessageDeleteGroup", language.getHelpMessageDeleteGroup())
        .appendString("helpMessageGetPlayerGroup", language.getHelpMessageGetPlayerGroup())
        .appendString(
            "helpMessageRemoveGroupPermission", language.getHelpMessageRemoveGroupPermission())
        .appendString("helpMessageSetPlayerGroup", language.getHelpMessageSetPlayerGroup())
        .appendString("helpMessageUpdateGroup", language.getHelpMessageUpdateGroup())
        .appendString("incorrectDateFormat", language.getIncorrectDateFormat())
        .appendString("setPlayerGroupError", language.getSetPlayerGroupError())
        .appendString("setPlayerGroupSuccessful", language.getSetPlayerGroupSuccessful())
        .appendString("updateGroupError", language.getUpdateGroupError())
        .appendString("updateGroupSuccessful", language.getUpdateGroupSuccessful());
  }

  public static Language getEnDefault() {
    return new Language(
        "§8[§cPlayPerms§8] §7",
        "%prefix% You are not allowed to execute this command!",
        "%prefix% Command not known! Try /playperms help",
        "%prefix% '%playerName%' does not exist in the database!",
        "%prefix% Your current rank is '%rank%' for %formatMillis%!",
        "%prefix% An error occurred while adding '%permission%' to '%group%'!",
        "%prefix% Successfully added '%permission%' to '%group%'!",
        "%prefix% An error occurred while removing '%permission%' from '%group%'!",
        "%prefix% Successfully removed '%permission%' from '%group%'!",
        "%prefix% Error while creating permission group '%group%'!",
        "%prefix% Successfully created permission group '%group%'!",
        "%prefix% Error while deleting permission group '%group%'!",
        "%prefix% Successfully deleted permission group '%group%'!",
        "Add a permission to a permission group.",
        "Create a new permission group.",
        "Delete a existing permission group.",
        "Get your own permission group or the permission group of a player.",
        "Remove a permission from a permission group.",
        "Set the permission group of a player.",
        "Update the meta data of a permission group.",
        "%prefix% Incorrect time format. (3d 30m 10s)",
        "%prefix% Error while setting the permission group to the player!",
        "%prefix% Successfully set the permission group to the player!",
        "%prefix% An error occurred while updating '%action%' of the group %group%!",
        "%prefix% Successfully updated '%action%' of the group %group%!");
  }

  public static Language getDeDefault() {
    return new Language(
        "§8[§cPlayPerms§8] §7",
        "%prefix% Du hast leider keine Berechtigung diesen Befehl auszuführen!",
        "%prefix% Befehl nicht gefunden! Probiere doch /playperms help",
        "%prefix% '%playerName%' existiert nicht in der Datenbank!",
        "%prefix% Dein derzeitiger Rang für %formatMillis% ist %rank%!",
        "%prefix% Beim hinzufügen der Berechtigung '%permission%' zu '%group%' trat ein Fehler auf!",
        "%prefix% Berechtigung '%permission%' zu '%group%' hinzugefügt!",
        "%prefix% Beim entfernen der Berechtigung '%permission%' von '%group%' trat ein Fehler auf!",
        "%prefix% Berechtigung '%permission%' von '%group%' entfernt!",
        "%prefix% Beim erstellen der Gruppe '%group%' trat ein Fehler auf!",
        "%prefix% Die Gruppe '%group%' wurde erfolgreich erstellt!",
        "%prefix% Beim löschen der Gruppe '%group%' trat ein Fehler auf!",
        "%prefix% Die Gruppe '%group%' wurde erfolgreich gelöscht!",
        "Füge eine Berechtigung zu einer Gruppe hinzu.",
        "Erstelle eine neue Gruppe.",
        "Lösche eine existierende Gruppe.",
        "Sieh dir deine Gruppe oder die Gruppe eines anderen Spielers an.",
        "Entferne eine Berechtigung von einer Gruppe.",
        "Setze die Gruppe eines Spielers.",
        "Setze die Metadaten einer Gruppe neu.",
        "%prefix% Falsches Format für die Zeit. (3d 30m 10s)",
        "%prefix% Beim setzen der Gruppe für den Spieler ist ein Fehler aufgetreten!",
        "%prefix% Die Gruppe des Spielers wurde erfolgreich gesetzt!",
        "%prefix% Beim neusetzen von '%action%' der Gruppe %group% trat ein Fehler auf!",
        "%prefix% '%action%' der Gruppe %group% wurde erfolgreich neugesetzt!");
  }
}
