package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.ConfigFile;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WarpCommand extends Command {
  
  public WarpCommand() {
    super("warp");
    
    setPermission("omegaweaps.warp");
    setPermissionMessage(Utilities.Colourize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
    setDescription("Main warp command to allow players to warp around the server.");
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      if(args.length == 0) {
        player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &b/warp <warp name> - Warp to a specific place."));
      }
      
      if(args.length == 1) {
        String warpName = args[0].toLowerCase();
        
        if(WarpFile.getWarpData().isSet(warpName)) {
          double warpLocationX = WarpFile.getWarpData().getDouble(warpName + ".Warp Location.X");
          double warpLocationY = WarpFile.getWarpData().getDouble(warpName + ".Warp Location.Y");
          double warpLocationZ = WarpFile.getWarpData().getDouble(warpName + ".Warp Location.Z");
          String world = WarpFile.getWarpData().getString(warpName + ".Warp Location.World");
          World warpLocationWorld = Bukkit.getServer().getWorld(world);
          
          Location warpLocation = new Location(warpLocationWorld, warpLocationX, warpLocationY, warpLocationZ);
          
          player.spawnParticle(Particle.valueOf(ConfigFile.BEFORE_WARP_PARTICLES), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
          
          new BukkitRunnable() {
            @Override
            public void run() {
              player.teleport(warpLocation);
              player.spawnParticle(Particle.valueOf(ConfigFile.POST_WARP_PARTICLES), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 50);
            }
          }.runTaskLater(OmegaWarps.getInstance(), 10);
          
          player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " " + MessagesFile.WARP_MESSAGE.replace("%warpName%", warpName)));
        } else {
          player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &cSorry, that warp does not exist."));
        }
      }
    }
    return true;
  }
}
