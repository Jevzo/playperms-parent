package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RemoveGroupPermissionCommand implements PlayPermsCommand {

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    if (!proxiedPlayer.hasPermission("playperms.groups.removePermission")) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
      return;
    }

    if (args.length != 2) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String groupName = args[0];
    String permissionToRemove = args[1];

    boolean successful = permissionManager.removePermission(groupName, permissionToRemove);

    if (!successful) {
      String rawMessage =
          languageManager
              .transform(language.getRemovePermissionError())
              .getText()
              .replaceAll("%permission%", permissionToRemove)
              .replaceAll("%group%", groupName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
      return;
    }

    String rawMessage =
        languageManager
            .transform(language.getRemovePermissionSuccessful())
            .getText()
            .replaceAll("%permission%", permissionToRemove)
            .replaceAll("%group%", groupName);

    proxiedPlayer.sendMessage(new TextComponent(rawMessage));
  }
}
