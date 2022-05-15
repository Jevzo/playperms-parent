package io.ic1101.playperms.spigot.language;

import io.ic1101.playperms.spigot.language.models.Language;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

public class LanguageManager {

  @Getter @Setter private Language selectedLanguage;

  public String transform(String message) {
    return ChatColor.translateAlternateColorCodes(
        '&', message.replaceAll("%prefix%", selectedLanguage.getPrefix()));
  }
}
