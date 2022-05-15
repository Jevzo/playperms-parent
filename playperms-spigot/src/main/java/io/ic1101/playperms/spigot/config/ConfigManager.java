package io.ic1101.playperms.spigot.config;

import io.ic1101.playperms.library.config.DefaultConfig;
import io.ic1101.playperms.library.utils.Document;
import io.ic1101.playperms.spigot.language.LanguageManager;
import io.ic1101.playperms.spigot.language.models.Language;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.io.File;

@RequiredArgsConstructor
public class ConfigManager {

  private final LanguageManager languageManager;

  @Getter private DefaultConfig config;

  public void onStart() {
    File playPermsFolder = new File("plugins/PlayPerms");
    File langFolder = new File("plugins/PlayPerms/lang");
    File signsFolder = new File("plugins/PlayPerms/signs");

    if (!playPermsFolder.exists() || !langFolder.exists() || !signsFolder.exists()) {
      playPermsFolder.mkdirs();
      langFolder.mkdirs();
      signsFolder.mkdirs();
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
      Bukkit.getLogger().severe("Config could not be loaded!");
      return;
    }

    this.config = DefaultConfig.fromDocument(defaultConfigDocument);
  }

  private void loadLanguage() {
    String languageKey;

    if (this.config == null) {
      Bukkit.getLogger()
          .severe("Config was not loaded, language can not be defined! Using \"en\" as language!");

      languageKey = "en";
    } else {
      languageKey = this.config.getLanguage();
    }

    File langFile = new File("plugins/PlayPerms/lang/" + languageKey + ".json");
    Document languageDocument = Document.read(langFile);

    if (languageDocument == null) {
      Bukkit.getLogger().severe("Something went wrong while loading the language!");
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
