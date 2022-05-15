package io.ic1101.playperms.spigot.tablist;

import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.spigot.permission.PermissionManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@RequiredArgsConstructor
public class PlayPermsTablist {

  private final PermissionManager permissionManager;

  public void setTablistForMe(Player player) {
    Scoreboard scoreboard = player.getScoreboard();

    Bukkit.getOnlinePlayers()
        .forEach(
            onlinePlayer -> {
              PermissionUser permissionUser =
                  this.permissionManager.getCachedPermissionUser(onlinePlayer.getUniqueId());

              Team team = this.getOrCreateTeam(scoreboard, permissionUser);
              team.addEntry(onlinePlayer.getName());
            });
  }

  public void setTablistForOthers(Player player) {
    PermissionUser permissionUser =
        this.permissionManager.getCachedPermissionUser(player.getUniqueId());

    if (permissionUser == null) {
      return;
    }

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.getUniqueId() == player.getUniqueId()) {
        continue;
      }

      Scoreboard scoreboard = onlinePlayer.getScoreboard();
      Team team = this.getOrCreateTeam(scoreboard, permissionUser);
      team.addEntry(player.getName());
    }
  }

  public Team getOrCreateTeam(Scoreboard scoreboard, PermissionUser permissionUser) {
    String teamName =
        "00"
            + permissionUser.getPermissionGroup().getWeight()
            + permissionUser.getPermissionGroup().getName();

    Team team = scoreboard.getTeam(teamName);

    if (team == null) {
      team = scoreboard.registerNewTeam(teamName);
    }

    String teamPrefix =
            ChatColor.translateAlternateColorCodes(
                    '&', permissionUser.getPermissionGroup().getTabPrefix());

    team.setPrefix(teamPrefix);

    return team;
  }
}
