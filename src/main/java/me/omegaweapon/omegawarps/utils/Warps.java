package me.omegaweapon.omegawarps.utils;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Warps {
  private static final FileConfiguration warpsConfigFile = OmegaWarps.getInstance().getWarpsFile().getConfig();

  private static void getWarpLocation(final Player player, final String warpName) {

    if(!warpsConfigFile.isSet(warpName)) {
      return;
    }

    double warpLocationX = warpsConfigFile.getDouble(warpName + ".Warp Location.X");
    double warpLocationY = warpsConfigFile.getDouble(warpName + ".Warp Location.Y");
    double warpLocationZ = warpsConfigFile.getDouble(warpName + ".Warp Location.Z");

    String world = warpsConfigFile.getString(warpName + ".Warp Location.World");
    World warpLocationWorld = Bukkit.getServer().getWorld(world);

    if(warpLocationWorld == null) {
      return;
    }

    Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ);

    player.teleport(warpLocation);
  }

  public static void createWarp(final Player player, final String warpName, final Location warpLocation) {

    if(warpsConfigFile.isSet(warpName)) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, but that warp already exists");
      return;
    }

    warpsConfigFile.createSection(warpName);
    warpsConfigFile.set(warpName + ".Set By", player.getName());
    warpsConfigFile.set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    warpsConfigFile.set(warpName + ".Warp Location.World", player.getWorld().getName());
    warpsConfigFile.set(warpName + ".Warp Location.X", warpLocation.getX());
    warpsConfigFile.set(warpName + ".Warp Location.Y", warpLocation.getY());
    warpsConfigFile.set(warpName + ".Warp Location.Z", warpLocation.getZ());

    OmegaWarps.getInstance().getWarpsFile().saveConfig();

    Utilities.message(player, MessageHandler.playerMessage("Setwarp_Message.Without_Owner", "&bYou have created the warp %warpName%").replace("%warpName%", warpName));
  }

  public static void createWarpOthers(final Player player, final Player target, final String warpName, final Location warpLocation, final Double warpCost) {

    if(warpsConfigFile.isSet(warpName)) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, but that warp already exists");
      return;
    }

    if(!OmegaWarps.getInstance().getConfigFile().getConfig().getBoolean("Warp_Cost.Enabled") || !Bukkit.getPluginManager().isPluginEnabled("Vault")) {

      warpsConfigFile.createSection(warpName);
      warpsConfigFile.set(warpName + ".Set By", player.getName());
      warpsConfigFile.set(warpName + ".Set For", target.getName());
      warpsConfigFile.set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      warpsConfigFile.set(warpName + ".Warp Location.World", player.getWorld().getName());
      warpsConfigFile.set(warpName + ".Warp Location.X", warpLocation.getX());
      warpsConfigFile.set(warpName + ".Warp Location.Y", warpLocation.getY());
      warpsConfigFile.set(warpName + ".Warp Location.Z", warpLocation.getZ());

      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      Utilities.message(player, MessageHandler.playerMessage("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", warpName).replace("%warpOwner%", target.getName()));
      return;
    }

    if(!Utilities.checkPermissions(target, true,"omegawarps.cost.bypass", "omegawarps.*")) {
      double warpOwnerBalance = OmegaWarps.getInstance().getEconomy().getBalance(target);

      if(warpOwnerBalance < warpCost) {
        Utilities.message(player, MessageHandler.pluginPrefix() + " &bThe player " + target.getName() + " does not have enough money to pay for the warp.");
        return;
      }

      warpsConfigFile.createSection(warpName);
      warpsConfigFile.set(warpName + ".Set By", player.getName());
      warpsConfigFile.set(warpName + ".Set For", target.getName());
      warpsConfigFile.set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      warpsConfigFile.set(warpName + ".Warp Location.World", player.getWorld().getName());
      warpsConfigFile.set(warpName + ".Warp Location.X", warpLocation.getX());
      warpsConfigFile.set(warpName + ".Warp Location.Y", warpLocation.getY());
      warpsConfigFile.set(warpName + ".Warp Location.Z", warpLocation.getZ());
      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      OmegaWarps.getInstance().getEconomy().withdrawPlayer(target, warpCost);

      Utilities.message(target, MessageHandler.playerMessage("Warp_Cost_Taken", "&bThe amount of price &c$%warpCost% &bhas been taken from your account for the warp.").replace("%warpCost%", warpCost.toString()));
      Utilities.message(player, MessageHandler.playerMessage("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", warpName).replace("%warpOwner%", target.getName()));
    }
  }

  public static void beforeWarpEffects(final Player player) {
    player.spawnParticle(
      Particle.valueOf(
        OmegaWarps.getInstance().getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")),
      player.getLocation().getX(),
      player.getLocation().getY(),
      player.getLocation().getZ(), 50
    );
  }

  public static void postWarpEffects(final Player player, final String warpName) {

    if(!warpsConfigFile.isSet(warpName)) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, but that warp does not exist.");
      return;
    }

    new BukkitRunnable() {

      @Override
      public void run() {
        getWarpLocation(player, warpName);
        Utilities.message(player, MessageHandler.playerMessage("Warp_Message", "&cYou have warped to %warpName%").replace("%warpName%", warpName));
        player.spawnParticle(Particle.valueOf(OmegaWarps.getInstance().getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
      }
    }.runTaskLater(OmegaWarps.getInstance(), 10);
  }
}
