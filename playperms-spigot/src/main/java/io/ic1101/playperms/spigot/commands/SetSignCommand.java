package io.ic1101.playperms.spigot.commands;

import io.ic1101.playperms.library.utils.UuidFetcher;
import io.ic1101.playperms.spigot.language.LanguageManager;
import io.ic1101.playperms.spigot.language.models.Language;
import io.ic1101.playperms.spigot.sign.SignManager;
import io.ic1101.playperms.spigot.sign.models.RankSign;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class SetSignCommand implements CommandExecutor {

  private final LanguageManager languageManager;
  private final Language language;
  private final SignManager signManager;

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
    if (!(commandSender instanceof Player)) {
      commandSender.sendMessage("Only players can use this command!");
      return true;
    }

    Player player = (Player) commandSender;

    if (!player.hasPermission("playperms.sign.add")) {
      player.sendMessage(languageManager.transform(language.getNoPermission()));
      return true;
    }

    if (args.length != 1) {
      player.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return true;
    }

    String playerName = args[0];

    Player targetPlayer = Bukkit.getPlayer(playerName);

    UUID uuid;
    if (targetPlayer == null) {
      uuid = UuidFetcher.getUuid(playerName);
    } else {
      uuid = targetPlayer.getUniqueId();
    }

    if (uuid == null) {
      player.sendMessage(languageManager.transform(language.getSetSignPlayerNotFound()));
      return true;
    }

    Block block = player.getTargetBlock(null, 200);

    if (block.getType() == Material.AIR) {
      player.sendMessage(languageManager.transform(language.getSetSignMaterialAir()));
      return true;
    }

    RankSign rankSign =
        new RankSign(
            uuid.toString(), block.getWorld().getName(), block.getX(), block.getY(), block.getZ());

    this.signManager.createNewSign(rankSign);

    player.sendMessage(languageManager.transform(language.getSetSignSuccessful()));
    return true;
  }
}
