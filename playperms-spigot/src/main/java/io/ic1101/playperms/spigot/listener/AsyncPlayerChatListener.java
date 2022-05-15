package io.ic1101.playperms.spigot.listener;

import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.spigot.permission.PermissionManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class AsyncPlayerChatListener implements Listener {

  private final PermissionManager permissionManager;

  @EventHandler
  public void onAsyncPlayerChat(AsyncPlayerChatEvent asyncPlayerChatEvent) {
    Player player = asyncPlayerChatEvent.getPlayer();

    PermissionUser permissionUser =
        this.permissionManager.getCachedPermissionUser(player.getUniqueId());

    if (permissionUser == null) {
      return;
    }

    String chatPrefix =
        ChatColor.translateAlternateColorCodes(
            '&', permissionUser.getPermissionGroup().getChatPrefix());

    asyncPlayerChatEvent.setFormat(chatPrefix + "%s §8> %s");
  }
}
