package io.ic1101.playperms.spigot.permission;

import io.ic1101.playperms.library.cache.SynchronizedCache;
import io.ic1101.playperms.library.database.model.Permission;
import io.ic1101.playperms.library.database.model.PermissionGroup;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.permission.PermissionHelper;
import io.ic1101.playperms.spigot.PlayPermsSpigotPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PermissionManager {

  private final PermissionHelper permissionHelper;

  private final SynchronizedCache<UUID, PermissionUser> permissionUserCache =
      new SynchronizedCache<>();

  public PermissionUser getCachedPermissionUser(UUID uuid) {
    return this.permissionUserCache.get(uuid);
  }

  public void removeFromCache(UUID uuid) {
    this.permissionUserCache.remove(uuid);
  }

  public void onLogin(Player player) {
    PermissionUser permissionUser = this.permissionHelper.getPermissionUser(player.getUniqueId());

    if (permissionUser == null) {
      player.kickPlayer("§4An error occurred, please rejoin!");
      return;
    }

    this.permissionUserCache.add(player.getUniqueId(), permissionUser);

    this.setPermissionsOnJoin(player, permissionUser);
  }

  public boolean hasPermission(UUID uuid, String permission) {
    PermissionUser permissionUser = this.getCachedPermissionUser(uuid);

    if (permissionUser == null) {
      return false;
    }

    List<Permission> permissions = permissionUser.getPermissionGroup().getPermissions();

    if (permissions.stream().anyMatch(value -> value.getPermission().equals("*"))) {
      return true;
    }

    return permissions.stream().anyMatch(value -> value.getPermission().equals(permission));
  }

  private void setPermissionsOnJoin(Player player, PermissionUser permissionUser) {
    PermissionGroup permissionGroup = permissionUser.getPermissionGroup();

    if (permissionGroup == null) {
      return;
    }

    permissionGroup
        .getPermissions()
        .forEach(permission -> this.setPermission(player, permission.getPermission()));
  }

  private void setPermission(Player player, String permission) {
    PermissionAttachment permissionAttachment =
        player.addAttachment(PlayPermsSpigotPlugin.getInstance());

    permissionAttachment.setPermission(permission, true);
  }
}
