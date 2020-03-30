package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Warp extends PlayerCommand {
  
  public Warp() {
    super("warp");

    // Set description message
    setDescription("Main warp command to allow players to warp around the server.");

    // Set the usage for the command
    setUsage("/warp <warpName>");
  }

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(strings.length == 0) {
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &b /warp <warpname> - Warp to a specific place.");
    }

    if(strings.length == 1) {
      String warpName = strings[0].toLowerCase();

      if(OmegaWarps.getWarpsFile().getConfig().isSet(warpName)) {
        double warpLocationX = OmegaWarps.getWarpsFile().getConfig().getDouble(warpName + ".Warp Location.X");
        double warpLocationY = OmegaWarps.getWarpsFile().getConfig().getDouble(warpName + ".Warp Location.Y");
        double warpLocationZ = OmegaWarps.getWarpsFile().getConfig().getDouble(warpName + ".Warp Location.Z");

        String world = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.World");
        World warpLocationWorld = Bukkit.getServer().getWorld(world);

        Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ);

        if(OmegaWarps.getConfigFile().getConfig().getBoolean("Per_Warp_Permissions")) {
          if(player.hasPermission("omegawarps.warp." + warpName) || player.hasPermission("omegawarps.warp.*") || player.isOp()) {
            player.spawnParticle(Particle.valueOf(OmegaWarps.getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);

            new BukkitRunnable() {

              @Override
              public void run() {
                player.teleport(warpLocation);
                Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Warp_Message").replace("%warpName%", warpName));
                player.spawnParticle(Particle.valueOf(OmegaWarps.getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
              }
            }.runTaskLater(OmegaWarps.getInstance(), 10);
          } else {
            Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
          }
        } else {
          if(Utilities.checkPermission(player, "omegawarps.warps", true)) {
            player.spawnParticle(Particle.valueOf(OmegaWarps.getConfigFile().getConfig().getString("Warp_Particle_Effects.Before_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);

            new BukkitRunnable() {

              @Override
              public void run() {
                player.teleport(warpLocation);
                Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Warp_Message").replace("%warpName%", warpName));
                player.spawnParticle(Particle.valueOf(OmegaWarps.getConfigFile().getConfig().getString("Warp_Particle_Effects.After_Warp")), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
              }
            }.runTaskLater(OmegaWarps.getInstance(), 10);
          } else {
            Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
          }
        }
      } else {
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &cSorry, but that warp does not exist.");
      }
    }
  }
}
