package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.utils.TimeUtils;
import io.ic1101.playperms.library.utils.UuidFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class GetPlayerGroupCommand implements PlayPermsCommand {

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    PermissionUser permissionUser;
    String playerName;

    if (args.length == 1) {
      if (!proxiedPlayer.hasPermission("playperms.player.get")) {
        proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
        return;
      }

      playerName = args[0];

      ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(playerName);

      if (targetPlayer != null) {
        UUID uuid = targetPlayer.getUniqueId();
        permissionUser = permissionManager.getCachedPermissionUser(uuid);
      } else {
        UUID uuid = UuidFetcher.getUuid(playerName);

        if (uuid == null) {
          return;
        }

        permissionUser = permissionHelper.getPermissionUser(uuid);
      }
    } else {
      playerName = proxiedPlayer.getName();

      UUID uuid = proxiedPlayer.getUniqueId();
      permissionUser = permissionManager.getCachedPermissionUser(uuid);
    }

    if (permissionUser == null) {
      String rawMessage =
          languageManager
              .transform(language.getGetPlayerGroupError())
              .getText()
              .replaceAll("%playerName%", playerName);

      proxiedPlayer.sendMessage(new TextComponent(rawMessage));
      return;
    }

    long currentEpoch = System.currentTimeMillis();
    long rankTimeMillis = permissionUser.getEndMillis() - currentEpoch;

    if (permissionUser.getEndMillis() == -1) {
      rankTimeMillis = permissionUser.getEndMillis();
    }

    String rawMessage =
        languageManager
            .transform(language.getGetPlayerGroupSuccessful())
            .getText()
            .replaceAll("%rank%", permissionUser.getPermissionGroup().getName())
            .replaceAll("%formatMillis%", TimeUtils.parseTimeMillis(rankTimeMillis));

    proxiedPlayer.sendMessage(new TextComponent(rawMessage));
  }
}
