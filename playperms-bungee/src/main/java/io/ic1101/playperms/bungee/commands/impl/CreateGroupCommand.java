package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CreateGroupCommand implements PlayPermsCommand {

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    if (!proxiedPlayer.hasPermission("playperms.groups.create")) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
      return;
    }

    if (args.length != 5) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String groupName = args[0];

    try {
      String chatPrefix = args[1];
      String tabPrefix = args[2];

      int weight = Integer.parseInt(args[3]);
      boolean defaultGroup = Boolean.parseBoolean(args[4]);

      boolean successful =
          permissionManager.createGroup(groupName, chatPrefix, tabPrefix, weight, defaultGroup);

      if (!successful) {
        String rawMessage =
            languageManager
                .transform(language.getCreateGroupError())
                .getText()
                .replace("%group%", groupName);

        proxiedPlayer.sendMessage(new TextComponent(rawMessage));
        return;
      }

      String rawMessage =
          languageManager
              .transform(language.getCreateGroupSuccessful())
              .getText()
              .replace("%group%", groupName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
    } catch (NumberFormatException e) {
      String rawMessage =
          languageManager
              .transform(language.getCreateGroupError())
              .getText()
              .replace("%group%", groupName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
    }
  }
}
