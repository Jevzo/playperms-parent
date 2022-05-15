package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DeleteGroupCommand implements PlayPermsCommand {

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    if (!proxiedPlayer.hasPermission("playperms.groups.delete")) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
      return;
    }

    if (args.length != 1) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String groupName = args[0];

    boolean successful = permissionManager.deleteGroup(groupName);

    if (!successful) {
      String rawMessage =
          languageManager
              .transform(language.getDeleteGroupError())
              .getText()
              .replace("%group%", groupName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
      return;
    }

    String rawMessage =
        languageManager
            .transform(language.getDeleteGroupSuccessful())
            .getText()
            .replace("%group%", groupName);

    proxiedPlayer.sendMessage(new TextComponent(rawMessage));
  }
}
