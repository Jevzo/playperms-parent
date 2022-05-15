package io.ic1101.playperms.bungee.commands;

import io.ic1101.playperms.bungee.PlayPermsBungeePlugin;
import io.ic1101.playperms.bungee.language.models.Language;
import io.ic1101.playperms.bungee.language.LanguageManager;
import io.ic1101.playperms.bungee.permission.PermissionManager;
import io.ic1101.playperms.library.permission.PermissionHelper;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface PlayPermsCommand {

  PermissionHelper permissionHelper = PlayPermsBungeePlugin.getInstance().getPermissionHelper();
  PermissionManager permissionManager = PlayPermsBungeePlugin.getInstance().getPermissionManager();
  LanguageManager languageManager = PlayPermsBungeePlugin.getInstance().getLanguageManager();
  Language language =
      PlayPermsBungeePlugin.getInstance().getLanguageManager().getSelectedLanguage();

  void execute(ProxiedPlayer proxiedPlayer, String[] args);
}
