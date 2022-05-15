package io.ic1101.playperms.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
    playerQuitEvent.setQuitMessage(null);
  }
}
