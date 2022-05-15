package io.ic1101.playperms.bungee.config;

import io.ic1101.playperms.bungee.language.models.Language;
import io.ic1101.playperms.bungee.language.LanguageManager;
import io.ic1101.playperms.library.config.DefaultConfig;
import io.ic1101.playperms.library.utils.Document;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ProxyServer;

import java.io.File;

@RequiredArgsConstructor
public class ConfigManager {

  private final LanguageManager languageManager;

  @Getter private DefaultConfig config;

  public void onStart() {
    File playPermsFolder = new File("plugins/PlayPerms");
    File langFolder = new File("plugins/PlayPerms/lang");

    if (!playPermsFolder.exists() || !langFolder.exists()) {
      playPermsFolder.mkdirs();
      langFolder.mkdirs();
    }

    File configFile = new File("plugins/PlayPerms/config.json");
    File langDeFile = new File("plugins/PlayPerms/lang/de.json");
    File langEnFile = new File("plugins/PlayPerms/lang/en.json");

    if (!configFile.exists()) {
      this.createDefaultConfig(configFile);
    }

    if (!langDeFile.exists()) {
      this.createLangDeFile(langDeFile);
    }

    if (!langEnFile.exists()) {
      this.createLangEnFile(langEnFile);
    }

    this.loadConfig(configFile);
    this.loadLanguage();
  }

  private void loadConfig(File configFile) {
    Document defaultConfigDocument = Document.read(configFile);

    if (defaultConfigDocument == null) {
      ProxyServer.getInstance().getLogger().severe("Config could not be loaded!");
      return;
    }

    this.config = DefaultConfig.fromDocument(defaultConfigDocument);
  }

  private void loadLanguage() {
    String languageKey;

    if (this.config == null) {
      ProxyServer.getInstance()
          .getLogger()
          .severe("Config was not loaded, language can not be defined! Using \"en\" as language!");

      languageKey = "en";
    } else {
      languageKey = this.config.getLanguage();
    }

    File langFile = new File("plugins/PlayPerms/lang/" + languageKey + ".json");
    Document languageDocument = Document.read(langFile);

    if (languageDocument == null) {
      ProxyServer.getInstance()
          .getLogger()
          .severe("Something went wrong while loading the language!");

      return;
    }

    this.languageManager.setSelectedLanguage(Language.fromDocument(languageDocument));
  }

  private void createDefaultConfig(File configFile) {
    DefaultConfig defaultConfig = DefaultConfig.getDefault();
    Document defaultConfigDocument = DefaultConfig.toDocument(defaultConfig);

    defaultConfigDocument.write(configFile);
  }

  private void createLangDeFile(File langDeFile) {
    Language deLanguage = Language.getDeDefault();
    Document deLanguageDocument = Language.toDocument(deLanguage);

    deLanguageDocument.write(langDeFile);
  }

  private void createLangEnFile(File langEnFile) {
    Language enLanguage = Language.getEnDefault();
    Document enLanguageDocument = Language.toDocument(enLanguage);

    enLanguageDocument.write(langEnFile);
  }
}
