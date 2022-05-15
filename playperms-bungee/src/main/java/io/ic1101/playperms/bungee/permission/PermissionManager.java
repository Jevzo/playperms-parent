package io.ic1101.playperms.bungee.permission;

import io.ic1101.playperms.bungee.network.CustomMessageChannel;
import io.ic1101.playperms.library.cache.SynchronizedCache;
import io.ic1101.playperms.library.database.model.Permission;
import io.ic1101.playperms.library.database.model.PermissionGroup;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.permission.PermissionHelper;
import io.ic1101.playperms.library.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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

  public void onLogin(ProxiedPlayer proxiedPlayer) {
    PermissionUser permissionUser =
        this.permissionHelper.getPermissionUser(proxiedPlayer.getUniqueId());

    if (permissionUser == null) {
      permissionUser = this.createFirstJoinEntry(proxiedPlayer);
    }

    permissionUser = this.checkTimeOnGroup(permissionUser);

    this.permissionUserCache.add(proxiedPlayer.getUniqueId(), permissionUser);

    this.setPermissionsOnJoin(proxiedPlayer, permissionUser);
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

  public boolean addPermission(String groupName, String permissionToAdd) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    Permission permission = this.permissionHelper.getPermission(permissionToAdd);

    if (permission == null) {
      permission = new Permission(null, permissionToAdd);

      this.permissionHelper.getPermissionRepository().save(permission);
    }

    PermissionGroup updatedPermissionGroup =
        this.permissionHelper
            .getPermissionGroupRepository()
            .addPermission(permissionGroup, permission);

    return this.updateCacheAndPlayers(groupName, updatedPermissionGroup);
  }

  public boolean removePermission(String groupName, String permissionToRemove) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    Permission permission = this.permissionHelper.getPermission(permissionToRemove);

    if (permission == null) {
      return false;
    }

    if (!permissionGroup.getPermissions().contains(permission)) {
      return false;
    }

    PermissionGroup updatedPermissionGroup =
        this.permissionHelper
            .getPermissionGroupRepository()
            .removePermission(permissionGroup, permission);

    return this.updateCacheAndPlayers(groupName, updatedPermissionGroup);
  }

  public boolean addPlayerToGroup(UUID uuid, String groupName, String length) {
    PermissionUser permissionUser = this.permissionHelper.getPermissionUser(uuid);

    if (permissionUser == null) {
      return false;
    }

    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    PermissionUser updatedPermissionUser =
        this.permissionHelper
            .getPermissionUserRepository()
            .updateGroup(permissionUser, permissionGroup, TimeUtils.parseTimeString(length));

    CustomMessageChannel.forcePlayerUpdate(uuid.toString());

    UUID updatedUuid = UUID.fromString(updatedPermissionUser.getUuid());

    if (this.permissionUserCache.contains(updatedUuid)) {
      this.permissionUserCache.replace(updatedUuid, updatedPermissionUser);
    }

    return true;
  }

  public boolean createGroup(
      String groupName, String chatPrefix, String tabPrefix, int weight, boolean defaultGroup) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup != null) {
      return false;
    }

    permissionGroup =
        new PermissionGroup(
            null, groupName, chatPrefix, tabPrefix, weight, defaultGroup, List.of());

    this.permissionHelper.getPermissionGroupRepository().save(permissionGroup);

    return true;
  }

  public boolean deleteGroup(String groupName) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    PermissionGroup defaultGroup = this.permissionHelper.getDefaultGroup();

    List<PermissionUser> usersWithGroup = this.permissionHelper.getPlayersWithGroup(groupName);

    usersWithGroup.forEach(
        permissionUser -> {
          PermissionUser updatedPermissionUser =
              this.permissionHelper
                  .getPermissionUserRepository()
                  .updateGroup(permissionUser, defaultGroup);

          UUID updatedUuid = UUID.fromString(updatedPermissionUser.getUuid());

          if (this.permissionUserCache.contains(updatedUuid)) {
            this.permissionUserCache.replace(updatedUuid, updatedPermissionUser);
          }
        });

    CustomMessageChannel.forcePlayerUpdate(usersWithGroup);

    this.permissionHelper.getPermissionGroupRepository().delete("name", permissionGroup.getName());

    return true;
  }

  public boolean updateGroupName(String groupName, String value) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    this.permissionHelper.getPermissionGroupRepository().update("name", groupName, "name", value);
    permissionGroup.setName(value);

    return this.updateCacheAndPlayers(value, permissionGroup);
  }

  public boolean updateGroupChatPrefix(String groupName, String value) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    this.permissionHelper
        .getPermissionGroupRepository()
        .update("name", groupName, "chatPrefix", value);

    permissionGroup.setChatPrefix(value);

    return this.updateCacheAndPlayers(groupName, permissionGroup);
  }

  public boolean updateGroupTabPrefix(String groupName, String value) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    this.permissionHelper
        .getPermissionGroupRepository()
        .update("name", groupName, "tabPrefix", value);

    permissionGroup.setTabPrefix(value);

    return this.updateCacheAndPlayers(groupName, permissionGroup);
  }

  public boolean updateGroupWeight(String groupName, String value) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    try {
      int weight = Integer.parseInt(value);

      this.permissionHelper
          .getPermissionGroupRepository()
          .update("name", groupName, "weight", weight);

      permissionGroup.setWeight(weight);

      return this.updateCacheAndPlayers(groupName, permissionGroup);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public boolean updateGroupDefaultGroup(String groupName, String value) {
    PermissionGroup permissionGroup = this.permissionHelper.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return false;
    }

    this.permissionHelper
        .getPermissionGroupRepository()
        .update("name", groupName, "defaultGroup", Boolean.parseBoolean(value));

    permissionGroup.setDefaultGroup(Boolean.parseBoolean(value));

    return this.updateCacheAndPlayers(groupName, permissionGroup);
  }

  public PermissionUser checkTimeOnGroup(PermissionUser permissionUser) {
    if (permissionUser.getLength() == -1) {
      return permissionUser;
    }

    if (permissionUser.getEndMillis() > System.currentTimeMillis()) {
      return permissionUser;
    }

    PermissionGroup permissionGroup = this.permissionHelper.getDefaultGroup();

    this.addPlayerToGroup(
        UUID.fromString(permissionUser.getUuid()), permissionGroup.getName(), "-1");

    permissionUser.setPermissionGroup(permissionGroup);
    permissionUser.setLength(-1);
    permissionUser.setEndMillis(-1);

    return permissionUser;
  }

  private PermissionUser createFirstJoinEntry(ProxiedPlayer proxiedPlayer) {
    PermissionGroup permissionGroup = this.permissionHelper.getDefaultGroup();

    if (permissionGroup == null) {
      ProxyServer.getInstance().getLogger().severe("No default group was set! Creating it now!");

      permissionGroup =
          new PermissionGroup(
              null, "default", "&aSpieler &8| &7", "&aSpieler &8| &7", 999, true, List.of());

      this.permissionHelper.getPermissionGroupRepository().save(permissionGroup);
    }

    PermissionUser permissionUser =
        new PermissionUser(
            null,
            proxiedPlayer.getUniqueId().toString(),
            proxiedPlayer.getName(),
            -1,
            -1,
            permissionGroup);

    this.permissionHelper.getPermissionUserRepository().save(permissionUser);

    return permissionUser;
  }

  private void setPermissionsOnJoin(ProxiedPlayer proxiedPlayer, PermissionUser permissionUser) {
    PermissionGroup permissionGroup = permissionUser.getPermissionGroup();

    if (permissionGroup == null) {
      return;
    }

    permissionGroup
        .getPermissions()
        .forEach(permission -> this.setPermission(proxiedPlayer, permission.getPermission()));
  }

  private void setPermission(ProxiedPlayer proxiedPlayer, String permission) {
    proxiedPlayer.setPermission(permission, true);
  }

  private boolean updateCacheAndPlayers(String groupName, PermissionGroup updatedPermissionGroup) {
    List<PermissionUser> usersWithGroup = this.permissionHelper.getPlayersWithGroup(groupName);
    CustomMessageChannel.forcePlayerUpdate(usersWithGroup);

    usersWithGroup.forEach(
        permissionUser -> {
          UUID uuid = UUID.fromString(permissionUser.getUuid());

          if (this.permissionUserCache.contains(uuid)) {
            permissionUser.setPermissionGroup(updatedPermissionGroup);

            this.permissionUserCache.replace(uuid, permissionUser);
          }
        });

    return true;
  }
}
