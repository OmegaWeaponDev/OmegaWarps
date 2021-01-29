package me.omegaweapondev.omegawarps.utils;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageHandler {
  private final OmegaWarps plugin;
  private final FileConfiguration messagesConfig;
  private final String configName;

  public MessageHandler(final OmegaWarps plugin, final FileConfiguration messagesConfig) {
    this.plugin = plugin;
    this.messagesConfig = messagesConfig;
    this.configName = plugin.getMessagesFile().getFileName();
  }

  public String string(final String message, final String fallbackMessage) {
    if(messagesConfig.getString(message) == null) {
      getErrorMessage(message);
      return getPrefix() + fallbackMessage;
    }
    return getPrefix() + messagesConfig.getString(message);
  }

  public String console(final String message, final String fallbackMessage) {
    if(messagesConfig.getString(message) == null) {
      getErrorMessage(message);
      return fallbackMessage;
    }
    return messagesConfig.getString(message);
  }

  public String getPrefix() {
    if(messagesConfig.getString("Prefix") == null) {
      getErrorMessage("Prefix");
      return "&7&l[&aOmegaWarps&7&l]" + " ";
    }
    return messagesConfig.getString("Prefix") + " ";
  }

  private void getErrorMessage(final String message) {
    Utilities.logInfo(true,
      "There was an error getting the " + message + " message from the " + configName + ".",
      "I have set a fallback message to take it's place until the issue is fixed.",
      "To resolve this, please locate " + message + " in the " + configName + " and fix the issue."
    );
  }
}
