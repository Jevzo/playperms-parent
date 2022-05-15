package io.ic1101.playperms.bungee.listener;

import io.ic1101.playperms.bungee.permission.PermissionManager;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@RequiredArgsConstructor
public class PermissionCheckListener implements Listener {

  private final PermissionManager permissionManager;

  @EventHandler
  public void onPermissionCheck(PermissionCheckEvent permissionCheckEvent) {
    ProxiedPlayer proxiedPlayer = (ProxiedPlayer) permissionCheckEvent.getSender();

    boolean hasPermission =
        this.permissionManager.hasPermission(
            proxiedPlayer.getUniqueId(), permissionCheckEvent.getPermission());

    permissionCheckEvent.setHasPermission(hasPermission);
  }
}
