package io.ic1101.playperms.bungee;

import io.ic1101.playperms.bungee.commands.PlayPermsCommandManager;
import io.ic1101.playperms.bungee.commands.PlayPermsCreateAdminCommand;
import io.ic1101.playperms.bungee.commands.PlayPermsParentCommand;
import io.ic1101.playperms.bungee.commands.impl.*;
import io.ic1101.playperms.bungee.config.ConfigManager;
import io.ic1101.playperms.bungee.language.LanguageManager;
import io.ic1101.playperms.bungee.listener.PermissionCheckListener;
import io.ic1101.playperms.bungee.listener.PostLoginListener;
import io.ic1101.playperms.bungee.permission.PermissionManager;
import io.ic1101.playperms.bungee.tasks.GroupTimeCheckTask;
import io.ic1101.playperms.library.config.DatabaseConfig;
import io.ic1101.playperms.library.database.PermissionDatabaseFactory;
import io.ic1101.playperms.library.database.repository.impl.PermissionGroupRepository;
import io.ic1101.playperms.library.database.repository.impl.PermissionRepository;
import io.ic1101.playperms.library.database.repository.impl.PermissionUserRepository;
import io.ic1101.playperms.library.permission.PermissionHelper;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Getter
public class PlayPermsBungeePlugin extends Plugin {

  @Getter private static PlayPermsBungeePlugin instance;

  private LanguageManager languageManager;
  private ConfigManager configManager;
  private PermissionDatabaseFactory permissionDatabaseFactory;
  private PermissionUserRepository permissionUserRepository;
  private PermissionGroupRepository permissionGroupRepository;
  private PermissionRepository permissionRepository;
  private PermissionHelper permissionHelper;
  private PermissionManager permissionManager;
  private PlayPermsCommandManager playPermsCommandManager;

  @Override
  public void onEnable() {
    this.initializeClasses();

    this.configManager.onStart();

    this.connectDatabase();
    this.registerListener();
    this.registerCommands();

    this.scheduledGroupTimeCheck();

    ProxyServer.getInstance().registerChannel("playperms:channel");
  }

  @Override
  public void onDisable() {
    this.permissionDatabaseFactory.getSessionFactory().close();

    ProxyServer.getInstance().unregisterChannel("playperms:channel");
  }

  private void initializeClasses() {
    instance = this;

    this.languageManager = new LanguageManager();
    this.configManager = new ConfigManager(languageManager);
    this.permissionDatabaseFactory = new PermissionDatabaseFactory();
    this.permissionUserRepository = new PermissionUserRepository(permissionDatabaseFactory);
    this.permissionGroupRepository = new PermissionGroupRepository(permissionDatabaseFactory);
    this.permissionRepository = new PermissionRepository(permissionDatabaseFactory);

    this.permissionHelper =
        new PermissionHelper(
            permissionGroupRepository, permissionRepository, permissionUserRepository);
    this.permissionManager = new PermissionManager(permissionHelper);

    this.playPermsCommandManager = new PlayPermsCommandManager();
  }

  private void connectDatabase() {
    DatabaseConfig databaseConfig = this.configManager.getConfig().getDatabaseConfig();
    this.permissionDatabaseFactory.createSessionFactory(databaseConfig);
  }

  private void registerListener() {
    PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

    pluginManager.registerListener(this, new PostLoginListener(permissionManager));
    pluginManager.registerListener(this, new PermissionCheckListener(permissionManager));
  }

  private void registerCommands() {
    playPermsCommandManager.registerCommand("createGroup", new CreateGroupCommand());
    playPermsCommandManager.registerCommand("deleteGroup", new DeleteGroupCommand());
    playPermsCommandManager.registerCommand("getPlayerGroup", new GetPlayerGroupCommand());
    playPermsCommandManager.registerCommand("setPlayerGroup", new SetPlayerGroupCommand());
    playPermsCommandManager.registerCommand("updateGroup", new UpdateGroupCommand());
    playPermsCommandManager.registerCommand("addGroupPermission", new AddGroupPermissionCommand());
    playPermsCommandManager.registerCommand(
        "removeGroupPermission", new RemoveGroupPermissionCommand());
    playPermsCommandManager.registerCommand("help", new HelpCommand());

    PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

    pluginManager.registerCommand(
        this, new PlayPermsParentCommand(languageManager, playPermsCommandManager));
    pluginManager.registerCommand(this, new PlayPermsCreateAdminCommand(permissionManager));
  }

  private void scheduledGroupTimeCheck() {
    Timer timer = new Timer("playperms-timer");

    timer.scheduleAtFixedRate(
        new GroupTimeCheckTask(permissionManager),
        TimeUnit.MINUTES.toMillis(1),
        TimeUnit.MINUTES.toMillis(1));
  }
}
