package me.omegaweapondev.omegawarps.utils;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.configs.ConfigCreator;
import me.ou.library.configs.ConfigUpdater;

import java.io.IOException;
import java.util.Arrays;

public class SettingsHandler {
  private final OmegaWarps plugin;
  private final ConfigCreator configFile;
  private final ConfigCreator messagesFile;
  private final ConfigCreator warpsFile;

  public SettingsHandler(final OmegaWarps plugin) {
    this.plugin = plugin;
    configFile = new ConfigCreator("config.yml");
    messagesFile = new ConfigCreator("messages.yml");
    warpsFile = new ConfigCreator("warps.yml");
  }

  public void setupConfigs() {
    getConfigFile().createConfig();
    getMessagesFile().createConfig();
    getWarpsFile().createConfig();

    getWarpsFile().getConfig().options().header(
      " -------------------------------------------------------------------------------------------\n" +
        " \n" +
        " Welcome to OmegaWarps warp file.\n" +
        " \n" +
        " This file stores all the warps that are created on the server.\n" +
        " It will include the player who created it, the location & who it was created for.\n" +
        " \n" +
        " -------------------------------------------------------------------------------------------"
    );

  }

  public void configUpdater() {
    Utilities.logInfo(true, "Attempting to update the config files....");

    try {
      if(getConfigFile().getConfig().getDouble("Config_Version") != 1.2) {
        getConfigFile().getConfig().set("Config_Version", 1.2);
        getConfigFile().saveConfig();
        ConfigUpdater.update(plugin, "config.yml", getConfigFile().getFile(), Arrays.asList("none"));
        Utilities.logInfo(true, "The config.yml has successfully been updated!");
      }

      if(getMessagesFile().getConfig().getDouble("Config_Version") != 1.2) {
        getMessagesFile().getConfig().set("Config_Version", 1.2);
        getMessagesFile().saveConfig();
        ConfigUpdater.update(plugin, "messages.yml", getMessagesFile().getFile(), Arrays.asList("none"));
        Utilities.logInfo(true, "The messages.yml has successfully been updated!");
      }
      plugin.onReload();
    } catch(IOException ex) {
      ex.printStackTrace();
    }
  }

  public void reloadFiles() {
    getWarpsFile().reloadConfig();
    getMessagesFile().reloadConfig();
    getConfigFile().reloadConfig();
  }

  public ConfigCreator getConfigFile() {
    return configFile;
  }

  public ConfigCreator getMessagesFile() {
    return messagesFile;
  }

  public ConfigCreator getWarpsFile() {
    return warpsFile;
  }
}
