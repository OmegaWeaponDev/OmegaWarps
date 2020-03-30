package me.omegaweapon.omegawarps;

import me.omegaweapon.omegawarps.commands.MainCommand;
import me.omegaweapon.omegawarps.commands.warps.*;
import me.omegaweapon.omegawarps.events.PlayerListener;
import me.ou.library.Utilities;
import me.ou.library.configs.ConfigCreator;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OmegaWarps extends JavaPlugin {
  public static OmegaWarps instance;
  private static Economy econ = null;

  private static final ConfigCreator configFile = new ConfigCreator("config.yml");
  private static final ConfigCreator messagesFile = new ConfigCreator("messages.yml");
  private static final ConfigCreator warpsFile = new ConfigCreator("warps.yml");
  
  @Override
  public void onEnable() {
    instance = this;
    Utilities.setInstance(this);

    // Logs a message to console, saying that the plugin has enabled correctly.
    Utilities.logInfo(true, "OmegaWarps has been enabled!");

    // Setup the files
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

    // Register the commands and the events
    Utilities.registerCommands(new MainCommand(), new RemoveWarp(), new SetWarp(),
      new WarpCheck(), new Warp(), new WarpList());

    Utilities.registerEvent(new PlayerListener());

    // Setup Economy Support
    if(!setupEconomy() ) {
      Utilities.logSevere(true, String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    // The Updater
    new UpdateChecker(this, 74788).getVersion(version -> {
      if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
        Utilities.logInfo(true, "You are already running the latest version");
      } else {
        PluginDescriptionFile pdf = this.getDescription();
        Utilities.logWarning(true,
          "A new version of " + pdf.getName() + " is avaliable!",
          "Current Version: " + pdf.getVersion() + " > New Version: " + version,
          "Grab it here: https://spigotmc.org/resources/73013"
        );
      }
    });

  }
  
  @Override
  public void onDisable() {
    instance = null;
    super.onDisable();
  }
  
  public void onReload() {
    getConfigFile().reloadConfig();
    getMessagesFile().reloadConfig();
    getWarpsFile().reloadConfig();
  }
  
  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    econ = rsp.getProvider();
    return econ != null;
  }

  public static ConfigCreator getConfigFile() {
    return configFile;
  }

  public static ConfigCreator getMessagesFile() {
    return messagesFile;
  }

  public static ConfigCreator getWarpsFile() {
    return warpsFile;
  }
  
  public static Economy getEconomy() {
    return econ;
  }
  
  public static OmegaWarps getInstance() {
    return instance;
  }
}
