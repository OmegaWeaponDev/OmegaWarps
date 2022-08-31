package me.omegaweapondev.omegawarps.utils;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageHandler {
  private final OmegaWarps plugin;
  private final FileConfiguration messagesFile;
  private final String configName;

  public MessageHandler(final OmegaWarps plugin, final FileConfiguration messagesFile) {
    this.plugin = plugin;
    this.messagesFile = messagesFile;

    this.configName = plugin.getSettingsHandler().getMessagesFile().getFileName();
  }

  public String string(final String message, final String fallbackMessage) {
    if(messagesFile.getString(message) == null) {
      getErrorMessage(message);
      return getPrefix() + fallbackMessage;
    }
    return getPrefix() + messagesFile.getString(message);
  }

  public String console(final String message, final String fallbackMessage) {
    if(messagesFile.getString(message) == null) {
      getErrorMessage(message);
      return fallbackMessage;
    }
    return messagesFile.getString(message);
  }

  public ConfigurationSection configSection(final String message) {
    return messagesFile.getConfigurationSection(message);
  }

  public boolean getConfigBoolean(final String booleanValue) {
    return messagesFile.getBoolean(booleanValue);
  }

  public String getPrefix() {
    if(messagesFile.getString("Prefix") == null) {
      getErrorMessage("Prefix");
      return "#808080&l[#560900OmegaWarps#808080&l]" + " ";
    }
    return messagesFile.getString("Prefix") + " ";
  }

  private void getErrorMessage(final String message) {
    Utilities.logInfo(true,
      "There was an error getting the " + message + " message from the " + configName + ".",
      "I have set a fallback message to take it's place until the issue is fixed.",
      "To resolve this, please locate " + message + " in the " + configName + " and fix the issue."
    );
  }
}