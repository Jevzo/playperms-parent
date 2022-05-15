package io.ic1101.playperms.spigot.listener;

import io.ic1101.playperms.spigot.permission.CustomPermissibleBase;
import io.ic1101.playperms.spigot.permission.PermissionManager;
import io.ic1101.playperms.spigot.scoreboard.PlayPermsScoreboard;
import io.ic1101.playperms.spigot.tablist.PlayPermsTablist;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

  private final PermissionManager permissionManager;
  private final PlayPermsScoreboard playPermsScoreboard;
  private final PlayPermsTablist playPermsTablist;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
    playerJoinEvent.setJoinMessage(null);

    Player player = playerJoinEvent.getPlayer();

    this.permissionManager.onLogin(player);
    this.updatePermissionBase(player);

    this.playPermsScoreboard.setDefaultScoreboard(player);

    this.playPermsTablist.setTablistForOthers(player);
    this.playPermsTablist.setTablistForMe(player);
  }

  private void updatePermissionBase(Player player) {
    String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);

    try {
      Field field =
          Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftHumanEntity")
              .getDeclaredField("perm");

      field.setAccessible(true);

      Object permissionBase = field.get(player);

      if (!(permissionBase instanceof CustomPermissibleBase)) {
        field.set(player, new CustomPermissibleBase(player, permissionManager));
      }
    } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
      Bukkit.getLogger()
          .severe("Error while overriding PermissionBase for " + player.getUniqueId());
    }
  }
}
