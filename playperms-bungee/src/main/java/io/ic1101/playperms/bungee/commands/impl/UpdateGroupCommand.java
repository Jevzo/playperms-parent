package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UpdateGroupCommand implements PlayPermsCommand {

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    if (!proxiedPlayer.hasPermission("playperms.groups.update")) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
      return;
    }

    if (args.length < 3) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String executor = args[0];
    String groupName = args[1];

    StringBuilder valueBuilder = new StringBuilder();

    for (int i = 2; i < args.length; i++) {
      valueBuilder.append(args[i]).append(" ");
    }

    String value = valueBuilder.toString().trim();

    boolean successful;

    switch (executor) {
      case "name" -> successful = permissionManager.updateGroupName(groupName, value);
      case "chatPrefix" -> successful = permissionManager.updateGroupChatPrefix(groupName, value);
      case "tabPrefix" -> successful = permissionManager.updateGroupTabPrefix(groupName, value);
      case "weight" -> successful = permissionManager.updateGroupWeight(groupName, value);
      case "defaultGroup" -> successful = permissionManager.updateGroupDefaultGroup(groupName, value);
      default -> {
        proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
        return;
      }
    }

    if (!successful) {
      String rawMessage =
          languageManager
              .transform(language.getUpdateGroupError())
              .getText()
              .replaceAll("%action%", executor)
              .replaceAll("%group%", groupName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
      return;
    }

    String rawMessage =
        languageManager
            .transform(language.getUpdateGroupSuccessful())
            .getText()
            .replaceAll("%action%", executor)
            .replaceAll("%group%", groupName);

    proxiedPlayer.sendMessage(new TextComponent(rawMessage));
  }
}
