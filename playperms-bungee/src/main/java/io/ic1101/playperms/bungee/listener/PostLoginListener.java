package io.ic1101.playperms.bungee.listener;

import io.ic1101.playperms.bungee.permission.PermissionManager;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@RequiredArgsConstructor
public class PostLoginListener implements Listener {

  private final PermissionManager permissionManager;

  @EventHandler
  public void onPostLogin(PostLoginEvent postLoginEvent) {
    ProxiedPlayer proxiedPlayer = postLoginEvent.getPlayer();

    this.permissionManager.onLogin(proxiedPlayer);
  }
}
