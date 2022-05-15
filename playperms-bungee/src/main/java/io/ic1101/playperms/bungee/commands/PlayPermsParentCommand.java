package io.ic1101.playperms.bungee.commands;

import io.ic1101.playperms.bungee.language.models.Language;
import io.ic1101.playperms.bungee.language.LanguageManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class PlayPermsParentCommand extends Command {

  private final LanguageManager languageManager;
  private final PlayPermsCommandManager playPermsCommandManager;
  private final Language language;

  public PlayPermsParentCommand(
      LanguageManager languageManager, PlayPermsCommandManager playPermsCommandManager) {
    super("playperms");

    this.languageManager = languageManager;
    this.playPermsCommandManager = playPermsCommandManager;

    this.language = languageManager.getSelectedLanguage();
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    if (!(commandSender instanceof ProxiedPlayer)) {
      commandSender.sendMessage(new TextComponent("Only players can use this command!"));
      return;
    }

    ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

    if (Arrays.asList(args).isEmpty()) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    String executor = args[0];

    PlayPermsCommand playPermsCommand = playPermsCommandManager.getCommand(executor);

    if (playPermsCommand == null) {
      proxiedPlayer.sendMessage(languageManager.transform(language.getNotEnoughArgs()));
      return;
    }

    playPermsCommand.execute(proxiedPlayer, Arrays.copyOfRange(args, 1, args.length));
  }
}
