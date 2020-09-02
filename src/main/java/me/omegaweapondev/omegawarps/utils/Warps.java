package me.omegaweapondev.omegawarps.utils;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Warps {
  private final MessageHandler messagesFile = new MessageHandler(OmegaWarps.getInstance().getMessagesFile().getConfig());
  private final FileConfiguration warpsFile = OmegaWarps.getInstance().getWarpsFile().getConfig();

  private final Player player;
  private final String warpName;

  public Warps(final Player player, final String warpName) {
    this.player = player;
    this.warpName = warpName;
  }

  public void getWarpLocation() {

    if(!warpsFile.isSet(warpName)) {
      return;
    }

    double warpLocationX = warpsFile.getDouble(warpName + ".Warp Location.X");
    double warpLocationY = warpsFile.getDouble(warpName + ".Warp Location.Y");
    double warpLocationZ = warpsFile.getDouble(warpName + ".Warp Location.Z");
    float warpLocationYaw = (float) warpsFile.getDouble(warpName + ".Warp Location.Yaw");
    float warpLocationPitch = (float) warpsFile.getDouble(warpName + ".Warp Location.Pitch");

    String world = warpsFile.getString(warpName + ".Warp Location.World");
    World warpLocationWorld = Bukkit.getServer().getWorld(world);

    if(warpLocationWorld == null) {
      return;
    }

    Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ, warpLocationYaw, warpLocationPitch);
    player.teleport(warpLocation);
  }

  public void createWarp(final Location warpLocation) {

    if(warpsFile.isSet(warpName)) {
      Utilities.message(player, messagesFile.string("Warp_Already_Exists", "&cSorry, but that warp already exists"));
      return;
    }

    warpsFile.createSection(warpName);
    warpsFile.set(warpName + ".Set By", player.getName());
    warpsFile.set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    warpsFile.set(warpName + ".Warp Location.World", player.getWorld().getName());
    warpsFile.set(warpName + ".Warp Location.X", warpLocation.getX());
    warpsFile.set(warpName + ".Warp Location.Y", warpLocation.getY());
    warpsFile.set(warpName + ".Warp Location.Z", warpLocation.getZ());
    warpsFile.set(warpName + ".Warp Location.Yaw", warpLocation.getYaw());
    warpsFile.set(warpName + ".Warp Location.Pitch", warpLocation.getPitch());

    OmegaWarps.getInstance().getWarpsFile().saveConfig();

    Utilities.message(player, messagesFile.string("Setwarp_Message.Without_Owner", "&bYou have created the warp %warpName%.").replace("%warpName%", warpName));
  }

  public void createWarpOthers(final Player target, final Location warpLocation, final Double warpCost) {

    if(warpsFile.isSet(warpName)) {
      Utilities.message(player, messagesFile.string("Warp_Already_Exists", "&cSorry, but that warp already exists"));
      return;
    }

    if(!OmegaWarps.getInstance().getConfigFile().getConfig().getBoolean("Warp_Cost.Enabled") || !Bukkit.getPluginManager().isPluginEnabled("Vault")) {

      warpsFile.createSection(warpName);
      warpsFile.set(warpName + ".Set By", player.getName());
      warpsFile.set(warpName + ".Set For", target.getName());
      warpsFile.set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      warpsFile.set(warpName + ".Warp Location.World", player.getWorld().getName());
      warpsFile.set(warpName + ".Warp Location.X", warpLocation.getX());
      warpsFile.set(warpName + ".Warp Location.Y", warpLocation.getY());
      warpsFile.set(warpName + ".Warp Location.Z", warpLocation.getZ());
      warpsFile.set(warpName + ".Warp Location.Yaw", warpLocation.getYaw());
      warpsFile.set(warpName + ".Warp Location.Pitch", warpLocation.getPitch());

      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      Utilities.message(player, messagesFile.string("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", warpName).replace("%warpOwner%", target.getName()));
      return;
    }

    if(!Utilities.checkPermissions(target, true,"omegawarps.cost.bypass", "omegawarps.admin")) {
      double warpOwnerBalance = OmegaWarps.getInstance().getEconomy().getBalance(target);

      if(warpOwnerBalance < warpCost) {
        Utilities.message(player, messagesFile.string("Payment_Failed", "&bThe player %player% does not have enough money to pay for the warp.").replace("%player%", target.getName()));
        return;
      }

      warpsFile.createSection(warpName);
      warpsFile.set(warpName + ".Set By", player.getName());
      warpsFile.set(warpName + ".Set For", target.getName());
      warpsFile.set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      warpsFile.set(warpName + ".Warp Location.World", player.getWorld().getName());
      warpsFile.set(warpName + ".Warp Location.X", warpLocation.getX());
      warpsFile.set(warpName + ".Warp Location.Y", warpLocation.getY());
      warpsFile.set(warpName + ".Warp Location.Z", warpLocation.getZ());
      warpsFile.set(warpName + ".Warp Location.Yaw", warpLocation.getYaw());
      warpsFile.set(warpName + ".Warp Location.Pitch", warpLocation.getPitch());
      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      OmegaWarps.getInstance().getEconomy().withdrawPlayer(target, warpCost);

      Utilities.message(target, messagesFile.string("Warp_Cost_Taken", "&bThe amount of price &c$%warpCost% &bhas been taken from your account for the warp.").replace("%warpCost%", warpCost.toString()));
      Utilities.message(player, messagesFile.string("Setwarp_Message.With_Owner", "&bYou have created the warp %warpName% for %warpOwner%!").replace("%warpName%", warpName).replace("%warpOwner%", target.getName()));
    }
  }

  public void beforeWarpEffects() {
    player.playSound(player.getLocation(), Sound.valueOf(OmegaWarps.getInstance().getConfigFile().getConfig().getString("Sound_Effects.Before_Warp_Sound")), 1, 1);
    player.spawnParticle(
      Particle.valueOf(
        OmegaWarps.getInstance().getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")),
      player.getLocation().getX(),
      player.getLocation().getY(),
      player.getLocation().getZ(), 50
    );
  }

  public void postWarpEffects() {

    if(!warpsFile.isSet(warpName)) {
      Utilities.message(player, messagesFile.string("Invalid_Warp_Name", "&cSorry, that warp does not exist."));
      return;
    }

    new BukkitRunnable() {

      @Override
      public void run() {
        getWarpLocation();
        Utilities.message(player, messagesFile.string("Warp_Message", "&cYou have warped to %warpName%").replace("%warpName%", warpName).replace("%player%", player.getDisplayName()));
        player.spawnParticle(Particle.valueOf(OmegaWarps.getInstance().getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
        player.playSound(player.getLocation(), Sound.valueOf(OmegaWarps.getInstance().getConfigFile().getConfig().getString("Sound_Effects.After_Warp_Sound")), 1, 1);
      }
    }.runTaskLater(OmegaWarps.getInstance(), 10);
  }
}
