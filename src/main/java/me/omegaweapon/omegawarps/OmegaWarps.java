package me.omegaweapon.omegawarps;

import me.omegaweapon.omegawarps.commands.MainCommand;
import me.omegaweapon.omegawarps.commands.warps.*;
import me.omegaweapon.omegawarps.events.PlayerListener;
import me.ou.library.Utilities;
import me.ou.library.configs.ConfigCreator;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OmegaWarps extends JavaPlugin {
  public static OmegaWarps instance;
  private Economy econ = null;

  private final ConfigCreator configFile = new ConfigCreator("config.yml");
  private final ConfigCreator messagesFile = new ConfigCreator("messages.yml");
  private final ConfigCreator warpsFile = new ConfigCreator("warps.yml");
  
  @Override
  public void onEnable() {
    initialSetup();
    setupConfigs();
    setupCommands();
    setupEvents();
    setupEconomy();
    spigotUpdater();
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

  private void initialSetup() {

    // Setup the instance for plugin and OU Library
    instance = this;
    Utilities.setInstance(this);

    // Make sure vault is installed
    if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
      Utilities.logWarning(true,
        "OmegaWarps require vault to be installed if you want to use the Warp Cost feature",
        "To install vault, please go to https://www.spigotmc.org/resources/vault.34315/ and download it.",
        "Once vault is installed, restart the server and OmegaFormatter will work."
      );
    }

    // Setup bStats
    final int bstatsPluginId = 7492;
    Metrics metrics = new Metrics(getInstance(), bstatsPluginId);

    // Logs a message to console, saying that the plugin has enabled correctly.
    Utilities.logInfo(true,
      " _____ _    _",
      "|  _  | |  | |",
      "| | | | |  | |  OmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev",
      "| | | | |/\\| |  Take full control of the warping done on your server!",
      "\\ \\_/ |  /\\  /  Currently supporting Spigot 1.13 - 1.16.1",
      " \\___/ \\/  \\/",
      ""
    );
  }

  private void setupConfigs() {
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
  }

  private void setupCommands() {
    // Register the commands
    Utilities.logInfo(true, "Registering Commands...");

    Utilities.setCommand().put("omegawarps", new MainCommand());
    Utilities.setCommand().put("setwarp", new SetWarp());
    Utilities.setCommand().put("delwarp", new RemoveWarp());
    Utilities.setCommand().put("warp", new Warp());
    Utilities.setCommand().put("checkwarp", new WarpCheck());
    Utilities.setCommand().put("listwarps", new WarpList());
    Utilities.setCommand().put("clearwarps", new ClearWarps());

    Utilities.registerCommands();
    Utilities.logInfo(true, "Commands Registered: " + Utilities.setCommand().size());
  }

  private void setupEvents() {
    // Register the events
    Utilities.registerEvent(new PlayerListener());
  }

  private void spigotUpdater() {
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

  public ConfigCreator getConfigFile() {
    return configFile;
  }

  public ConfigCreator getMessagesFile() {
    return messagesFile;
  }

  public ConfigCreator getWarpsFile() {
    return warpsFile;
  }
  
  public Economy getEconomy() {
    return econ;
  }
  
  public static OmegaWarps getInstance() {
    return instance;
  }
}
