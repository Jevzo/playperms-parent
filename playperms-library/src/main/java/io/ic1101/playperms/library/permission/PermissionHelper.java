package io.ic1101.playperms.library.permission;

import io.ic1101.playperms.library.database.model.Permission;
import io.ic1101.playperms.library.database.model.PermissionGroup;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.database.repository.impl.PermissionGroupRepository;
import io.ic1101.playperms.library.database.repository.impl.PermissionRepository;
import io.ic1101.playperms.library.database.repository.impl.PermissionUserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PermissionHelper {

  private final PermissionGroupRepository permissionGroupRepository;
  private final PermissionRepository permissionRepository;
  private final PermissionUserRepository permissionUserRepository;

  public PermissionUser getPermissionUser(UUID uuid) {
    return this.permissionUserRepository.findFirstBy("uuid", uuid.toString());
  }

  public PermissionGroup getPermissionGroup(String name) {
    return this.permissionGroupRepository.findFirstBy("name", name);
  }

  public Permission getPermission(String permission) {
    return this.permissionRepository.findFirstBy("permission", permission);
  }

  public PermissionGroup getDefaultGroup() {
    return this.permissionGroupRepository.findFirstBy("defaultGroup", true);
  }

  public List<PermissionUser> getPlayersWithGroup(String groupName) {
    PermissionGroup permissionGroup = this.getPermissionGroup(groupName);

    if (permissionGroup == null) {
      return null;
    }

    return this.permissionUserRepository.findByGroup(permissionGroup.getId());
  }
}
