package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCheckCommand extends Command {
  OmegaWarps plugin;
  
  public WarpCheckCommand(OmegaWarps plugin) {
    super("checkwarp");
    this.plugin = plugin;
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(player.hasPermission("omegawarps.check") || player.isOp()) {
        
        if(args.length == 0) {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &b/warpcheck <warp name> - View the information about a specific warp"));
        }
        
        if(args.length == 1) {
          String warpName = args[0].toLowerCase();
          if(WarpFile.getWarpData().isSet(warpName)) {
            String warpCreator = WarpFile.getWarpData().getString(warpName + ".Set By");
            if(WarpFile.getWarpData().isSet(warpName + ".Set For")) {
              String warpOwner = WarpFile.getWarpData().getString(warpName + ".Set For");
              
              player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bThe warp &c" + warpName + "&b was set by &c" + warpCreator + "&b for the player &c" + warpOwner));
            } else {
              player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bThe warp &c" + warpName + "&b was set by &c" + warpCreator));
            }
          }
        }
        
      } else {
        player.hasPermission(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
      }
    }
    return true;
  }
}
