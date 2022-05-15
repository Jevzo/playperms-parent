package io.ic1101.playperms.bungee.network;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.ic1101.playperms.library.database.model.PermissionUser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Used a spigotmc resource as reference here
 *
 * @see <a
 *     href="https://www.spigotmc.org/wiki/sending-a-custom-plugin-message-from-bungeecord/">Spigot
 *     MC Resource</a>
 */
public class CustomMessageChannel {

  public static void forcePlayerUpdate(String targetUuid) {
    Collection<ProxiedPlayer> onlinePlayers = ProxyServer.getInstance().getPlayers();

    if (onlinePlayers == null || onlinePlayers.isEmpty()) {
      return;
    }

    ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
    byteArrayDataOutput.writeUTF("playperms:channel:update");
    byteArrayDataOutput.writeUTF(targetUuid);

    ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(UUID.fromString(targetUuid));

    if (proxiedPlayer == null) {
      return;
    }

    proxiedPlayer
        .getServer()
        .getInfo()
        .sendData("playperms:channel", byteArrayDataOutput.toByteArray());
  }

  public static void forcePlayerUpdate(List<PermissionUser> permissionUsers) {
    for (PermissionUser permissionUser : permissionUsers) {
      String uuid = permissionUser.getUuid();

      ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));

      if (targetPlayer == null || !targetPlayer.isConnected()) {
        continue;
      }

      forcePlayerUpdate(uuid);
    }
  }
}
