package io.ic1101.playperms.spigot.scoreboard;

import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.utils.TimeUtils;
import io.ic1101.playperms.spigot.PlayPermsSpigotPlugin;
import io.ic1101.playperms.spigot.permission.PermissionManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PlayPermsScoreboard {

  private final PermissionManager permissionManager;

  // Exactly for reasons like this I hate the google code style... The function is so much harder to
  // read because it formatted like shit.
  public void updateScoreboard() {
    Bukkit.getScheduler()
        .runTaskTimerAsynchronously(
            PlayPermsSpigotPlugin.getInstance(),
            () -> {
              for (Player player : Bukkit.getOnlinePlayers()) {
                PermissionUser permissionUser =
                    this.permissionManager.getCachedPermissionUser(player.getUniqueId());

                if (permissionUser == null) {
                  continue;
                }

                long currentEpoch = System.currentTimeMillis();
                long rankTimeMillis = permissionUser.getEndMillis() - currentEpoch;

                if (permissionUser.getEndMillis() == -1) {
                  rankTimeMillis = permissionUser.getEndMillis();
                }

                this.createTeam(
                    player,
                    "sidebarGroup",
                    "Group:",
                    "§a",
                    " §3" + permissionUser.getPermissionGroup().getName());
                this.createTeam(
                    player,
                    "sidebarUntil",
                    "Until:",
                    "§a",
                    " §3" + TimeUtils.parseTimeMillis(rankTimeMillis));
              }
            },
            20L,
            20L);
  }

  public void setDefaultScoreboard(Player player) {
    Map<String, Integer> entries = new HashMap<>();

    entries.put(" ", 3);
    entries.put("Group:", 2);
    entries.put("Until:", 1);

    this.setScoreboard(player, "§cPlayPerms", entries);
  }

  private Scoreboard getScoreboard(Player player) {
    if (player.hasMetadata("playPermsSidebar")) {
      return (Scoreboard) player.getMetadata("playPermsSidebar").get(0).value();
    }

    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    if (scoreboardManager == null) {
      return null;
    }

    Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
    this.setPlayerMetadata(player, "playPermsSidebar", scoreboard);

    return scoreboard;
  }

  private void setScoreboard(Player player, String title, Map<String, Integer> entries) {
    Scoreboard scoreboard = this.getScoreboard(player);

    if (scoreboard == null) {
      return;
    }

    Objective objective = scoreboard.getObjective(player.getName());

    if (objective != null) {
      objective.unregister();
    }

    objective = scoreboard.registerNewObjective(player.getName(), "dummy", title);
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    for (String key : entries.keySet()) {
      objective.getScore(key).setScore(entries.get(key));
    }

    player.setScoreboard(scoreboard);
  }

  private void createTeam(Player player, String name, String entry, String prefix, String suffix) {
    Scoreboard scoreboard = this.getScoreboard(player);

    if (scoreboard == null) {
      return;
    }

    Team team = scoreboard.getTeam(name);

    if (team == null) {
      team = scoreboard.registerNewTeam(name);
    }

    team.addEntry(entry);
    team.setPrefix(prefix);
    team.setSuffix(suffix);
  }

  private void setPlayerMetadata(Entity entity, String key, Object value) {
    this.removeMetadata(entity, key);

    entity.setMetadata(key, new FixedMetadataValue(PlayPermsSpigotPlugin.getInstance(), value));
  }

  private void removeMetadata(Entity entity, String key) {
    if (!entity.hasMetadata(key)) {
      return;
    }

    entity.removeMetadata(key, PlayPermsSpigotPlugin.getInstance());
  }
}
