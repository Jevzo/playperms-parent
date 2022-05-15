package io.ic1101.playperms.bungee.commands;

import io.ic1101.playperms.bungee.permission.PermissionManager;
import io.ic1101.playperms.library.utils.UuidFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class PlayPermsCreateAdminCommand extends Command {

  private final PermissionManager permissionManager;

  public PlayPermsCreateAdminCommand(PermissionManager permissionManager) {
    super("create");

    this.permissionManager = permissionManager;
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    if (commandSender instanceof ProxiedPlayer) {
      commandSender.sendMessage(new TextComponent("This command is only for administrators!"));
      return;
    }

    if (args.length != 1) {
      commandSender.sendMessage(new TextComponent("Please use \"create <playerName>\""));
      return;
    }

    this.permissionManager.createGroup("Admin", "&4Admin &8| &7", "&4Admin &8| &7", 11, false);
    this.permissionManager.addPermission("Admin", "*");

    UUID uuid = UuidFetcher.getUuid(args[0]);

    if (uuid == null) {
      commandSender.sendMessage(
          new TextComponent("Error while executing command (uuid fetching went wrong)"));

      return;
    }

    boolean successful = this.permissionManager.addPlayerToGroup(uuid, "Admin", "-1");

    if (!successful) {
      commandSender.sendMessage(new TextComponent("Could not add the player to the group!"));
      return;
    }

    commandSender.sendMessage(
        new TextComponent("Created default admin group and added it to player"));
  }
}
