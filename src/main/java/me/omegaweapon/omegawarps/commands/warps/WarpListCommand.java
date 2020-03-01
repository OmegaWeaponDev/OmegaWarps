package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class WarpListCommand extends Command {
  
  public WarpListCommand() {
    super("listwarps");
    
    setPermission("omegawarps.list");
    setPermissionMessage(Utilities.Colourize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
    setDescription("View a list all the warps that have been set.");
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      Set<String> warpNames = WarpFile.getWarpData().getKeys(false);
      player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &bThe current warps are:"));
      
      for(String warpName : warpNames) {
        player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " " + warpName));
      }
    }
    return true;
  }
}
