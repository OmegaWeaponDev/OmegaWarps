package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand extends Command {
  OmegaWarps plugin;
  
  public WarpCommand(OmegaWarps plugin) {
    super("warp");
    this.plugin = plugin;
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(player.hasPermission("omegawarps.warp") || player.isOp()) {
        
        if(args.length == 0) {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &b/warp <warp name> - Warp to a specific place."));
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
            
            player.teleport(warpLocation);
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.WARP_MESSAGE.replace("%warpName%", warpName)));
          } else {
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &cSorry, that warp does not exist."));
          }
        }
      } else {
        player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
      }
    }
    return true;
  }
}
