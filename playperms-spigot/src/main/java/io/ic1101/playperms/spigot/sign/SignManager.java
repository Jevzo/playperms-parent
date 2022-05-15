package io.ic1101.playperms.spigot.sign;

import com.google.gson.JsonArray;
import io.ic1101.playperms.library.cache.SynchronizedCache;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.permission.PermissionHelper;
import io.ic1101.playperms.library.utils.Document;
import io.ic1101.playperms.library.utils.TimeUtils;
import io.ic1101.playperms.spigot.sign.models.RankSign;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SignManager {

  private final PermissionHelper permissionHelper;
  private final SynchronizedCache<UUID, RankSign> signCache = new SynchronizedCache<>();

  public void onServerStart() {
    this.loadSigns();

    this.signCache
        .getCache()
        .forEach(
            (uuid, rankSign) -> {
              PermissionUser permissionUser = this.permissionHelper.getPermissionUser(uuid);

              if (permissionUser == null) {
                return;
              }

              Location location = this.getCachedSignLocation(uuid);

              if (location == null) {
                return;
              }

              this.replaceOldSign(location);
              this.setSignContent(location, permissionUser);
            });
  }

  public void createNewSign(RankSign rankSign) {
    Location location =
        new Location(
            Bukkit.getWorld(rankSign.getWorld()),
            rankSign.getX(),
            rankSign.getY(),
            rankSign.getZ());

    UUID uuid = UUID.fromString(rankSign.getUuid());

    PermissionUser permissionUser = this.permissionHelper.getPermissionUser(uuid);

    if (permissionUser == null) {
      return;
    }

    this.signCache.add(uuid, rankSign);

    this.replaceOldSign(location);
    this.setSignContent(location, permissionUser);
    this.addSignToConfigFile(rankSign);
  }

  public void removeSign(Location location) {
    Optional<RankSign> rankSignOptional =
        this.signCache.getCacheValues().stream()
            .filter(
                rankSign ->
                    (rankSign.getX() == location.getX())
                        && (rankSign.getY() == location.getY())
                        && (rankSign.getZ() == location.getZ()))
            .findFirst();

    if (rankSignOptional.isEmpty()) {
      return;
    }

    RankSign rankSign = rankSignOptional.get();

    if (location.getWorld() == null) {
      return;
    }

    location.getWorld().getBlockAt(location).setType(Material.AIR);
    this.removeSignFromConfigFile(rankSign);

    this.signCache.remove(UUID.fromString(rankSign.getUuid()));
  }

  public void replaceOldSign(Location location) {
    if (location.getWorld() == null) {
      return;
    }

    location.getWorld().getBlockAt(location).setType(Material.ACACIA_WALL_SIGN);
  }

  public void setSignContent(Location location, PermissionUser permissionUser) {
    long currentEpoch = System.currentTimeMillis();
    long rankTimeMillis = permissionUser.getEndMillis() - currentEpoch;

    if (permissionUser.getEndMillis() == -1) {
      rankTimeMillis = permissionUser.getEndMillis();
    }

    if (location.getWorld() == null) {
      return;
    }

    Sign sign = (Sign) location.getWorld().getBlockAt(location).getState();

    sign.setLine(0, permissionUser.getName());
    sign.setLine(1, permissionUser.getPermissionGroup().getName());
    sign.setLine(2, TimeUtils.parseTimeMillis(rankTimeMillis));
    sign.setLine(3, String.valueOf(permissionUser.getPermissionGroup().getWeight()));

    sign.update();
  }

  public Location getCachedSignLocation(UUID uuid) {
    RankSign rankSign = this.signCache.get(uuid);

    if (rankSign == null) {
      return null;
    }

    return new Location(
        Bukkit.getWorld(rankSign.getWorld()), rankSign.getX(), rankSign.getY(), rankSign.getZ());
  }

  private void addSignToConfigFile(RankSign rankSign) {
    File file = new File("plugins/PlayPerms/signs/signs.json");

    Document document;
    if (!file.exists()) {
      document = new Document();
    } else {
      document = Document.read(file);

      if (document == null) {
        return;
      }
    }

    Document signDocument = RankSign.toDocument(rankSign);
    JsonArray jsonArray = document.getJsonElementValue("signs").getAsJsonArray();

    jsonArray.add(signDocument.getAsJsonObject());

    document = new Document().appendJsonElement("signs", jsonArray);
    document.write(file);
  }

  private void removeSignFromConfigFile(RankSign rankSign) {
    File file = new File("plugins/PlayPerms/signs/signs.json");

    Document document = Document.read(file);

    if (document == null) {
      return;
    }

    Document signDocument = RankSign.toDocument(rankSign);
    JsonArray jsonArray = document.getJsonElementValue("signs").getAsJsonArray();

    jsonArray.remove(signDocument.getAsJsonObject());

    document = new Document().appendJsonElement("signs", jsonArray);
    document.write(file);
  }

  private void loadSigns() {
    File file = new File("plugins/PlayPerms/signs/signs.json");

    if (!file.exists()) {
      return;
    }

    Document document = Document.read(file);

    if (document == null) {
      return;
    }

    document
        .getJsonElementValue("signs")
        .getAsJsonArray()
        .forEach(
            jsonElement -> {
              Document rankSignDocument = new Document(jsonElement.getAsJsonObject());
              RankSign rankSign = RankSign.fromDocument(rankSignDocument);

              this.signCache.add(UUID.fromString(rankSign.getUuid()), rankSign);
            });
  }
}
