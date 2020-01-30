package me.omegaweapon.omegawarps.settings;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WarpFile {
  private static FileConfiguration warpData;
  private static File warpFile;
  
  public static void setupWarpData() {
    warpFile = new File(Bukkit.getPluginManager().getPlugin("OmegaWarps").getDataFolder(), "warps.yml");
    
    if(!warpFile.exists()) {
      try {
        warpFile.createNewFile();
      } catch (IOException ignored) {
      }
    } else {
      reloadWarpData();
    }
    warpData = YamlConfiguration.loadConfiguration(warpFile);
    warpData.options().header(
      " -------------------------------------------------------------------------------------------\n" +
        " \n" +
        " Welcome to OmegaWarps Warp file.\n" +
        " \n" +
        " This file stores all the warps that are created on the server.\n" +
        " It will include the player who created it, the location & who it was created for.\n" +
        " \n" +
        " -------------------------------------------------------------------------------------------"
    );
  }
  
  public static FileConfiguration getWarpData() {
    return warpData;
  }
  
  public static void saveWarpData() {
    try {
      warpData.save(warpFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void reloadWarpData() {
    warpData = YamlConfiguration.loadConfiguration(warpFile);
  }
}
