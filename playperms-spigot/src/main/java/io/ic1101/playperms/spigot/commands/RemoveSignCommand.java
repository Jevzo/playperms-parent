package io.ic1101.playperms.spigot.commands;

import io.ic1101.playperms.spigot.language.LanguageManager;
import io.ic1101.playperms.spigot.language.models.Language;
import io.ic1101.playperms.spigot.sign.SignManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class RemoveSignCommand implements CommandExecutor {

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

    if (!player.hasPermission("playperms.sign.remove")) {
      player.sendMessage(languageManager.transform(language.getNoPermission()));
      return true;
    }

    Block block = player.getTargetBlock(null, 200);

    this.signManager.removeSign(block.getLocation());

    player.sendMessage(languageManager.transform(language.getRemoveSignSuccessful()));
    return true;
  }
}
