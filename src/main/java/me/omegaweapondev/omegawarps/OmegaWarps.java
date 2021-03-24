package me.omegaweapondev.omegawarps;

import me.omegaweapondev.omegawarps.commands.DebugCommand;
import me.omegaweapondev.omegawarps.commands.MainCommand;
import me.omegaweapondev.omegawarps.commands.warps.*;
import me.omegaweapondev.omegawarps.events.PlayerListener;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.omegaweapondev.omegawarps.utils.SettingsHandler;
import me.ou.library.SpigotUpdater;
import me.ou.library.Utilities;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OmegaWarps extends JavaPlugin {
  private OmegaWarps plugin;
  private Economy econ = null;
  private SettingsHandler settingsHandler;
  private MessageHandler messageHandler;
  
  @Override
  public void onEnable() {
    plugin = this;
    settingsHandler = new SettingsHandler(plugin);
    messageHandler = new MessageHandler(plugin, settingsHandler.getMessagesFile().getConfig());

    initialSetup();
    settingsHandler.setupConfigs();
    settingsHandler.configUpdater();
    setupCommands();
    setupEvents();
    setupEconomy();
    spigotUpdater();
  }
  
  @Override
  public void onDisable() {
    super.onDisable();
  }
  
  public void onReload() {
    getSettingsHandler().reloadFiles();
  }

  private void initialSetup() {
    Utilities.setInstance(this);

    // Make sure vault is installed
    if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
      Utilities.logWarning(true,
        "OmegaWarps require vault to be installed if you want to use the Warp Cost feature",
        "To install vault, please go to https://www.spigotmc.org/resources/vault.34315/ and download it.",
        "Once vault is installed, restart the server and OmegaWarps will work."
      );
    }

    // Setup bStats
    final int bstatsPluginId = 7492;
    Metrics metrics = new Metrics(plugin, bstatsPluginId);

    // Logs a message to console, saying that the plugin has enabled correctly.
    Utilities.logInfo(true,
      " _____ _    _",
      "|  _  | |  | |",
      "| | | | |  | |  OmegaWarps v" + plugin.getDescription().getVersion() + " By OmegaWeaponDev",
      "| | | | |/\\| |  Take full control of the warping done on your server!",
      "\\ \\_/ |  /\\  /  Currently supporting Spigot 1.13 - 1.16.5",
      " \\___/ \\/  \\/",
      ""
    );
  }

  private void setupCommands() {
    // Register the commands
    Utilities.logInfo(true, "Registering Commands...");

    Utilities.setCommand().put("omegawarps", new MainCommand(plugin));
    Utilities.setCommand().put("setwarp", new SetWarp(plugin));
    Utilities.setCommand().put("delwarp", new RemoveWarp(plugin));
    Utilities.setCommand().put("warp", new Warp(plugin));
    Utilities.setCommand().put("checkwarp", new WarpCheck(plugin));
    Utilities.setCommand().put("listwarps", new WarpList(plugin));
    Utilities.setCommand().put("clearwarps", new ClearWarps(plugin));
    Utilities.setCommand().put("omegawarpsdebug", new DebugCommand(plugin));

    Utilities.registerCommands();
    Utilities.logInfo(true, "Commands Registered: " + Utilities.setCommand().size());
  }

  private void setupEvents() {
    // Register the events
    Utilities.registerEvent(new PlayerListener(plugin));
  }

  private void spigotUpdater() {
    // The Updater
    new SpigotUpdater(plugin, 78327).getVersion(version -> {
      int spigotVersion = Integer.parseInt(version.replace(".", ""));
      int pluginVersion = Integer.parseInt(plugin.getDescription().getVersion().replace(".", ""));

      if(pluginVersion >= spigotVersion) {
        Utilities.logInfo(true, "You are already running the latest version");
        return;
      }

      PluginDescriptionFile pdf = plugin.getDescription();
      Utilities.logWarning(true,
        "A new version of " + pdf.getName() + " is avaliable!",
        "Current Version: " + pdf.getVersion() + " > New Version: " + version,
        "Grab it here: https://www.spigotmc.org/resources/omegawarps.74788/"
      );
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
  
  public Economy getEconomy() {
    return econ;
  }

  public SettingsHandler getSettingsHandler() {
    return settingsHandler;
  }

  public MessageHandler getMessageHandler() {
    return messageHandler;
  }
}
