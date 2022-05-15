package io.ic1101.playperms.bungee.commands.impl;

import io.ic1101.playperms.bungee.commands.PlayPermsCommand;
import io.ic1101.playperms.library.utils.UuidFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetPlayerGroupCommand implements PlayPermsCommand {

  private final Pattern pattern = Pattern.compile("\\d+[YMwdhms]");

  @Override
  public void execute(ProxiedPlayer proxiedPlayer, String[] args) {
    if (!proxiedPlayer.hasPermission("playperms.player.setGroup")) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNoPermission()));
      return;
    }

    if (args.length < 3) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String playerName = args[0];
    String groupName = args[1];
    String length = args[2];

    if (!length.equals("-1")) {
      StringBuilder lengthBuilder = new StringBuilder();

      for (int i = 2; i < args.length; i++) {
        Matcher matcher = pattern.matcher(args[i]);

        if (!matcher.matches()) {
          proxiedPlayer.sendMessage(languageManager.transform(language.getIncorrectDateFormat()));
          return;
        }

        lengthBuilder.append(args[i]).append(" ");
      }

      length = lengthBuilder.toString().trim();
    }

    ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(playerName);
    UUID targetUuid;

    if (targetPlayer == null) {
      targetUuid = UuidFetcher.getUuid(playerName);
    } else {
      targetUuid = targetPlayer.getUniqueId();
    }

    boolean successful = permissionManager.addPlayerToGroup(targetUuid, groupName, length);

    if (!successful) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getSetPlayerGroupError()));
      return;
    }

    proxiedPlayer.sendMessage(languageManager.transform(language.getSetPlayerGroupSuccessful()));
  }
}
