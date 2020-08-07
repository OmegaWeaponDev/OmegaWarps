package me.omegaweapondev.omegawarps.utils;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessageHandler {
  private static final FileConfiguration messagesConfig = OmegaWarps.getInstance().getMessagesFile().getConfig();

  public static String playerMessage(final String message, final String fallbackMessage) {
    if(messagesConfig.getString(message) == null) {
      Utilities.logWarning(true,
        "There was an error getting the " + message + " message from the messages.yml.",
        "I have set a fallback message to take it's place until the issue is fixed.",
        "To resolve this, please locate " + message + " in the messages.yml and fix the issue."
      );
      return fallbackMessage;
    }

    return messagesConfig.getString(message) + " ";
  }

  public static String consoleMessage(final String message, final String fallbackMessage) {
    if(messagesConfig.getString(message) == null) {
      Utilities.logWarning(true,
        "There was an error getting the " + message + " message from the messages.yml.",
        "I have set a fallback message to take it's place until the issue is fixed.",
        "To resolve this, please locate " + message + " in the messages.yml and fix the issue."
      );
      return fallbackMessage;
    }

    return messagesConfig.getString(message);
  }

  public static List<String> playerListMessage(final String message, final List<String> fallbackMessage) {
    if(messagesConfig.getStringList(message).size() == 0) {
      Utilities.logWarning(true,
        "There was an error getting the " + message + " message from the messages.yml",
        "I have set a fallback message to take it's place until the issue is fixed.",
        "To resolve this, please locate " + message + " in the messages.yml and fix the issue"
      );
      return Utilities.colourise(fallbackMessage);
    }
    return messagesConfig.getStringList(message);
  }
}
