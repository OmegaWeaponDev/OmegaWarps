package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWarpCommand extends Command {
  
  public RemoveWarpCommand() {
    super("delwarp");
    
    setPermission("omegawarps.remove");
    setPermissionMessage(Utilities.Colourize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
    setDescription("Remove warps that players have set.");
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(args.length == 0) {
        player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &b/delwarp <warp name> - Removes a specific warp a player has set."));
      }
      
      if(args.length == 1) {
        String warpName = args[0];
        Object deletedWarp = WarpFile.getWarpData().get(warpName);
        
        if(deletedWarp == null) {
          player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &cSorry, that warp does not exist."));
        } else {
          WarpFile.getWarpData().set(warpName, null);
          WarpFile.saveWarpData();
          
          player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &bYou have successfully deleted the warp " + warpName));
        }
      }
    }
    
    return true;
  }
}
