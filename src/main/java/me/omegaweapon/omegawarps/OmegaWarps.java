package me.omegaweapon.omegawarps;

import me.omegaweapon.omegawarps.commands.CommandUtil;
import me.omegaweapon.omegawarps.commands.OmegaWarpsCommand;
import me.omegaweapon.omegawarps.commands.warps.*;
import me.omegaweapon.omegawarps.events.PlayerListener;
import me.omegaweapon.omegawarps.settings.ConfigFile;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class OmegaWarps extends JavaPlugin {
  public static OmegaWarps instance;
  private static Economy econ = null;
  
  @Override
  public void onEnable() {
    instance = this;
    
    Logger logger = this.getLogger();
    logger.info("OmegaWarps has successfully been enabled!");
    
    if(!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
  
    ConfigFile.init();
    MessagesFile.init();
    WarpFile.setupWarpData();
    setupEconomy();
  
    CommandUtil.registerCommand(new OmegaWarpsCommand(this));
    CommandUtil.registerCommand(new SetWarpCommand(this));
    CommandUtil.registerCommand(new RemoveWarpCommand(this));
    CommandUtil.registerCommand(new WarpCommand(this));
    //CommandUtil.registerCommand(new WarpListCommand(this));
    //CommandUtil.registerCommand(new WarpCheckCommand(this));
  
    Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
  
//    // Update Checker
//    new OmegaUpdater(73535) {
//
//      @Override
//      public void onUpdateAvailable() {
//        logger.info("A new update has been released!");
//        logger.info("Your current version is: " + getDescription().getVersion());
//        logger.info("The latest version is: " + OmegaUpdater.getLatestVersion());
//        logger.info("You can update here: https://www.spigotmc.org/resources/omegawarps." + OmegaUpdater.getProjectId());
//      }
//    }.runTaskAsynchronously(this);
  }
  
  @Override
  public void onDisable() {
    instance = null;
    super.onDisable();
  }
  
  public void onReload() {
    ConfigFile.init();
    MessagesFile.init();
    WarpFile.reloadWarpData();
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
  
  public static Economy getEconomy() {
    return econ;
  }
  
  public static OmegaWarps getInstance() {
    return instance;
  }
}
