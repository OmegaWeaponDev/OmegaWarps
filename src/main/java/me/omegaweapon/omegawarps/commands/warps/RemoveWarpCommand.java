package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWarpCommand extends Command {
  OmegaWarps plugin;
  
  public RemoveWarpCommand(OmegaWarps plugin) {
    super("delwarp");
    this.plugin = plugin;
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(args.length == 0) {
        if(player.hasPermission("omegawarps.remove")) {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &b/delwarp <warp name> - Removes a specific warp a player has set."));
        } else {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
        }
      }
      
      if(args.length == 1) {
        if(player.hasPermission("omegawarps.remove") || player.isOp()) {
          String warpName = args[0];
          Object deletedWarp = WarpFile.getWarpData().get(warpName);
          
          if(deletedWarp == null) {
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &cSorry, that warp does not exist."));
          } else {
            WarpFile.getWarpData().set(warpName, null);
            WarpFile.saveWarpData();
            
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bYou have successfully deleted the warp " + warpName));
          }
        } else {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
        }
      }
    }
    
    return true;
  }
}
