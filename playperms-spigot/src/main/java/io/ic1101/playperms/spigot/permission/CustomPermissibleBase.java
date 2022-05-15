package io.ic1101.playperms.spigot.permission;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;

public class CustomPermissibleBase extends PermissibleBase {

  private final Player player;
  private final PermissionManager permissionManager;

  public CustomPermissibleBase(Player player, PermissionManager permissionManager) {
    super(player);

    this.player = player;
    this.permissionManager = permissionManager;
  }

  @Override
  public boolean hasPermission(Permission permission) {
    return this.hasPermission(permission.getName());
  }

  @Override
  public boolean hasPermission(String permission) {
    return this.permissionManager.hasPermission(player.getUniqueId(), permission);
  }
}
