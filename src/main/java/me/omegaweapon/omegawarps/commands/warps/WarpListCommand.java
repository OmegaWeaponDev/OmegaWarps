package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class WarpListCommand extends Command {
  OmegaWarps plugin;
  
  public WarpListCommand(OmegaWarps plugin) {
    super("listwarps");
    this.plugin = plugin;
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(player.hasPermission("omegawarps.list") || player.isOp()) {
        Set<String> warpNames = WarpFile.getWarpData().getKeys(false);
        player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bThe current warps are:"));
        
        for(String warpName : warpNames) {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + warpName));
        }
      } else {
        player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
      }
    }
    return true;
  }
}
