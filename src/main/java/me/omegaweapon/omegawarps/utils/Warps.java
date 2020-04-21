package me.omegaweapon.omegawarps.utils;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Warps {

  private static void getWarpLocation(final Player player, final String warpName) {
    if(OmegaWarps.getWarpsFile().getConfig().isSet(warpName)) {
      double warpLocationX = OmegaWarps.getWarpsFile().getConfig().getDouble(warpName + ".Warp Location.X");
      double warpLocationY = OmegaWarps.getWarpsFile().getConfig().getDouble(warpName + ".Warp Location.Y");
      double warpLocationZ = OmegaWarps.getWarpsFile().getConfig().getDouble(warpName + ".Warp Location.Z");

      String world = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.World");
      World warpLocationWorld = Bukkit.getServer().getWorld(world);

      Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ);

      player.teleport(warpLocation);
    }
  }

  public static void createWarp(final Player player, final String warpName, final Location warpLocation) {

    for(String currentWarps : OmegaWarps.getWarpsFile().getConfig().getKeys(false)) {
      if(currentWarps.equalsIgnoreCase(warpName)) {
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &cSorry, but that warp already exists");
        return;
      }
      return;
    }

    OmegaWarps.getWarpsFile().getConfig().createSection(warpName);
    OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set By", player.getName());
    OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.World", player.getWorld().getName());
    OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.X", warpLocation.getX());
    OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Y", warpLocation.getY());
    OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Z", warpLocation.getZ());
    try {
      OmegaWarps.getWarpsFile().saveConfig();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Setwarp_Message.Without_Owner").replace("%warpName%", warpName));
  }

  public static void createWarpOthers(final Player player, final Player target, final String warpName, final Location warpLocation, final Double warpCost) {

    for(String currentWarps : OmegaWarps.getWarpsFile().getConfig().getKeys(false)) {
      if(currentWarps.equalsIgnoreCase(warpName)) {
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &cSorry, but that warp already exists");
        return;
      }
      return;
    }

    if(!Utilities.checkPermission(target, "omegawarps.cost.bypass", true) && OmegaWarps.getConfigFile().getConfig().getBoolean("Warp_Cost.Enabled")) {
      double warpOwnerBalance = OmegaWarps.getEconomy().getBalance(target);

      if(warpOwnerBalance >= warpCost) {
        OmegaWarps.getWarpsFile().getConfig().createSection(warpName);
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set By", player.getName());
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set For", target.getName());
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.World", player.getWorld().getName());
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.X", warpLocation.getX());
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Y", warpLocation.getY());
        OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Z", warpLocation.getZ());
        try {
          OmegaWarps.getWarpsFile().saveConfig();
        } catch (Exception ex) {
          ex.printStackTrace();
        }

        OmegaWarps.getEconomy().withdrawPlayer(target, warpCost);
        Utilities.message(target, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Warp_Cost_Taken").replace("%warpCost%", warpCost.toString()));
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Setwarp_Message.With_Owner").replace("%warpName%", warpName).replace("%warpOwner%", target.getName()));
      } else {
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bThe player " + target.getName() + " does not have enough money to pay for the warp.");
      }
    } else {
      OmegaWarps.getWarpsFile().getConfig().createSection(warpName);
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set By", player.getName());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set For", target.getName());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.World", player.getWorld().getName());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.X", warpLocation.getX());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Y", warpLocation.getY());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Z", warpLocation.getZ());
      try {
        OmegaWarps.getWarpsFile().saveConfig();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Setwarp_Message.With_Owner").replace("%warpName%", warpName).replace("%warpOwner%", target.getName()));
    }
  }

  public static void beforeWarpEffects(final Player player) {
    player.spawnParticle(Particle.valueOf(OmegaWarps.getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
  }

  public static void postWarpEffects(final Player player, final String warpName) {
    new BukkitRunnable() {

      @Override
      public void run() {
        try {
          getWarpLocation(player, warpName);
          Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Warp_Message").replace("%warpName%", warpName));
          player.spawnParticle(Particle.valueOf(OmegaWarps.getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
        } catch (NullPointerException ex) {
          Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &cSorry, but that warp does not exist.");
        }
      }
    }.runTaskLater(OmegaWarps.getInstance(), 10);
  }
}
