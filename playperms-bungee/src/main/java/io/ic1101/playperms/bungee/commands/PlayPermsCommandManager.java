package io.ic1101.playperms.bungee.commands;

import java.util.HashMap;
import java.util.Map;

public class PlayPermsCommandManager {

  private final Map<String, PlayPermsCommand> commands = new HashMap<>();

  public PlayPermsCommand getCommand(String executor) {
    return this.commands.get(executor);
  }

  public void registerCommand(String executor, PlayPermsCommand playPermsCommand) {
    this.commands.put(executor, playPermsCommand);
  }
}
