package io.ic1101.playperms.spigot.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.permission.PermissionHelper;
import io.ic1101.playperms.spigot.permission.PermissionManager;
import io.ic1101.playperms.spigot.sign.SignManager;
import io.ic1101.playperms.spigot.tablist.PlayPermsTablist;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

/**
 * Used a spigotmc resource as reference here
 *
 * @see <a
 *     href="https://www.spigotmc.org/wiki/sending-a-custom-plugin-message-from-bungeecord/">Spigot
 *     MC Resource</a>
 */
@RequiredArgsConstructor
public class PlayPermsMessageListener implements PluginMessageListener {

  private final PermissionManager permissionManager;
  private final PermissionHelper permissionHelper;
  private final SignManager signManager;
  private final PlayPermsTablist playPermsTablist;

  @Override
  public void onPluginMessageReceived(String channel, Player sourcePlayer, byte[] bytes) {
    if (!channel.equalsIgnoreCase("playperms:channel")) {
      return;
    }

    ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput(bytes);
    String subChannel = byteArrayDataInput.readUTF();

    if (!subChannel.equalsIgnoreCase("playperms:channel:update")) {
      return;
    }

    UUID uuid = UUID.fromString(byteArrayDataInput.readUTF());

    Player player = Bukkit.getPlayer(uuid);

    if (player == null) {
      return;
    }

    this.permissionManager.removeFromCache(uuid);
    this.permissionManager.onLogin(player);

    this.playPermsTablist.setTablistForOthers(player);
    this.playPermsTablist.setTablistForMe(player);

    PermissionUser permissionUser = this.permissionHelper.getPermissionUser(uuid);

    if (permissionUser == null) {
      return;
    }

    Location location = this.signManager.getCachedSignLocation(uuid);

    if (location == null) {
      return;
    }

    this.signManager.replaceOldSign(location);
    this.signManager.setSignContent(location, permissionUser);
  }
}
