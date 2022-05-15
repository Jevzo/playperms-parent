package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AddGroupPermissionCommand implements PlayPermsCommand {

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    if (!proxiedPlayer.hasPermission("playperms.groups.addPermission")) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
      return;
    }

    if (args.length != 2) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String groupName = args[0];
    String permissionToAdd = args[1];

    boolean successful = permissionManager.addPermission(groupName, permissionToAdd);

    if (!successful) {
      String rawMessage =
          languageManager
              .transform(language.getAddPermissionError())
              .getText()
              .replaceAll("%permission%", permissionToAdd)
              .replaceAll("%group%", groupName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
      return;
    }

    String rawMessage =
        languageManager
            .transform(language.getAddPermissionSuccessful())
            .getText()
            .replaceAll("%permission%", permissionToAdd)
            .replaceAll("%group%", groupName);

    proxiedPlayer.sendMessage(new TextComponent(rawMessage));
  }
}
