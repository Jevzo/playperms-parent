package io.ic1101.playperms.bungee.language;

import io.ic1101.playperms.bungee.language.models.Language;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LanguageManager {

  @Getter @Setter private Language selectedLanguage;

  public TextComponent transform(String message) {
    return new TextComponent(
        ChatColor.translateAlternateColorCodes(
            '&', message.replaceAll("%prefix%", selectedLanguage.getPrefix())));
  }

  public void buildHelpMessage(ProxiedPlayer proxiedPlayer) {
    proxiedPlayer.sendMessage(new TextComponent("§7§b------------------------------------"));
    proxiedPlayer.sendMessage(new TextComponent(""));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms createGroup <groupName> <chatPrefix> <tabPrefix> <weight> <defaultGroup> §7| " + this.getSelectedLanguage().getHelpMessageCreateGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms deleteGroup <groupName> §7| " + this.getSelectedLanguage().getHelpMessageDeleteGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms getPlayerGroup §7| " + this.getSelectedLanguage().getHelpMessageGetPlayerGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms getPlayerGroup <playerName> §7| " + this.getSelectedLanguage().getHelpMessageGetPlayerGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms setPlayerGroup <playerName> <groupName> <length...> §7| " + this.getSelectedLanguage().getHelpMessageSetPlayerGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms updateGroup name <groupName> <value> §7| " + this.getSelectedLanguage().getHelpMessageUpdateGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms updateGroup chatPrefix <groupName> <value> §7| " + this.getSelectedLanguage().getHelpMessageUpdateGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms updateGroup tabPrefix <groupName> <value> §7| " + this.getSelectedLanguage().getHelpMessageUpdateGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms updateGroup weight <groupName> <value> §7| " + this.getSelectedLanguage().getHelpMessageUpdateGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms updateGroup defaultGroup <groupName> <value> §7| " + this.getSelectedLanguage().getHelpMessageUpdateGroup()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms addGroupPermission <groupName> <permissionToAdd> §7| " + this.getSelectedLanguage().getHelpMessageAddGroupPermission()));
    proxiedPlayer.sendMessage(new TextComponent("§a§b/playperms removeGroupPermission <groupName> <permissionToRemove> §7| " + this.getSelectedLanguage().getHelpMessageRemoveGroupPermission()));
    proxiedPlayer.sendMessage(new TextComponent(""));
    proxiedPlayer.sendMessage(new TextComponent("§7§b------------------------------------"));
  }
}
