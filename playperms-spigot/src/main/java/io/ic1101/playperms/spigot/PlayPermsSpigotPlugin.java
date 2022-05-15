package io.ic1101.playperms.spigot;

import io.ic1101.playperms.library.config.DatabaseConfig;
import io.ic1101.playperms.library.database.PermissionDatabaseFactory;
import io.ic1101.playperms.library.database.repository.impl.PermissionGroupRepository;
import io.ic1101.playperms.library.database.repository.impl.PermissionRepository;
import io.ic1101.playperms.library.database.repository.impl.PermissionUserRepository;
import io.ic1101.playperms.library.permission.PermissionHelper;
import io.ic1101.playperms.spigot.commands.RemoveSignCommand;
import io.ic1101.playperms.spigot.commands.SetSignCommand;
import io.ic1101.playperms.spigot.config.ConfigManager;
import io.ic1101.playperms.spigot.language.LanguageManager;
import io.ic1101.playperms.spigot.listener.AsyncPlayerChatListener;
import io.ic1101.playperms.spigot.listener.PlayerJoinListener;
import io.ic1101.playperms.spigot.listener.PlayerQuitListener;
import io.ic1101.playperms.spigot.network.PlayPermsMessageListener;
import io.ic1101.playperms.spigot.permission.PermissionManager;
import io.ic1101.playperms.spigot.scoreboard.PlayPermsScoreboard;
import io.ic1101.playperms.spigot.sign.SignManager;
import io.ic1101.playperms.spigot.tablist.PlayPermsTablist;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PlayPermsSpigotPlugin extends JavaPlugin {

  @Getter private static PlayPermsSpigotPlugin instance;

  private LanguageManager languageManager;
  private ConfigManager configManager;
  private PermissionDatabaseFactory permissionDatabaseFactory;
  private PermissionRepository permissionRepository;
  private PermissionGroupRepository permissionGroupRepository;
  private PermissionUserRepository permissionUserRepository;
  private PermissionHelper permissionHelper;
  private SignManager signManager;
  private PermissionManager permissionManager;
  private PlayPermsScoreboard playPermsScoreboard;
  private PlayPermsTablist playPermsTablist;

  @Override
  public void onEnable() {
    this.initializeClasses();

    this.configManager.onStart();

    this.connectDatabase();
    this.registerListener();
    this.registerCommands();

    this.signManager.onServerStart();
    this.playPermsScoreboard.updateScoreboard();

    Bukkit.getServer()
        .getMessenger()
        .registerIncomingPluginChannel(
            this,
            "playperms:channel",
            new PlayPermsMessageListener(
                permissionManager, permissionHelper, signManager, playPermsTablist));
  }

  @Override
  public void onDisable() {
    this.permissionDatabaseFactory.getSessionFactory().close();

    Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(this, "playperms:channel");
  }

  private void initializeClasses() {
    instance = this;

    this.languageManager = new LanguageManager();
    this.configManager = new ConfigManager(languageManager);
    this.permissionDatabaseFactory = new PermissionDatabaseFactory();
    this.permissionRepository = new PermissionRepository(permissionDatabaseFactory);
    this.permissionGroupRepository = new PermissionGroupRepository(permissionDatabaseFactory);
    this.permissionUserRepository = new PermissionUserRepository(permissionDatabaseFactory);

    this.permissionHelper =
        new PermissionHelper(
            permissionGroupRepository, permissionRepository, permissionUserRepository);

    this.signManager = new SignManager(permissionHelper);
    this.permissionManager = new PermissionManager(permissionHelper);
    this.playPermsScoreboard = new PlayPermsScoreboard(permissionManager);
    this.playPermsTablist = new PlayPermsTablist(permissionManager);
  }

  private void connectDatabase() {
    DatabaseConfig databaseConfig = this.configManager.getConfig().getDatabaseConfig();
    this.permissionDatabaseFactory.createSessionFactory(databaseConfig);
  }

  private void registerListener() {
    PluginManager pluginManager = Bukkit.getServer().getPluginManager();

    pluginManager.registerEvents(
        new PlayerJoinListener(permissionManager, playPermsScoreboard, playPermsTablist), this);
    pluginManager.registerEvents(new PlayerQuitListener(), this);
    pluginManager.registerEvents(new AsyncPlayerChatListener(permissionManager), this);
  }

  private void registerCommands() {
    this.getCommand("setSign")
        .setExecutor(
            new SetSignCommand(
                languageManager, languageManager.getSelectedLanguage(), signManager));
    this.getCommand("removesign")
        .setExecutor(
            new RemoveSignCommand(
                languageManager, languageManager.getSelectedLanguage(), signManager));
  }
}
