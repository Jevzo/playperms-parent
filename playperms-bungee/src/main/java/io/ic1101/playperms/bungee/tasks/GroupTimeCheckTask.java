package io.ic1101.playperms.bungee.tasks;

import io.ic1101.playperms.bungee.permission.PermissionManager;
import io.ic1101.playperms.library.database.model.PermissionUser;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.TimerTask;

@RequiredArgsConstructor
public class GroupTimeCheckTask extends TimerTask {

  private final PermissionManager permissionManager;

  @Override
  public void run() {
    Collection<ProxiedPlayer> onlinePlayers = ProxyServer.getInstance().getPlayers();

    for (ProxiedPlayer onlinePlayer : onlinePlayers) {
      PermissionUser permissionUser =
          this.permissionManager.getCachedPermissionUser(onlinePlayer.getUniqueId());

      if (permissionUser == null) {
        continue;
      }

      this.permissionManager.checkTimeOnGroup(permissionUser);
    }
  }
}
