package me.omegaweapon.omegawarps.commands;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OmegaWarpsCommand extends Command {
  OmegaWarps plugin;
  
  public OmegaWarpsCommand(OmegaWarps plugin) {
    super("omegawarps");
    this.plugin = plugin;
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(args.length == 0) {
        if(player.hasPermission("omegawarps.reload") || player.isOp()) {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " /omegawarps reload - Reload the OmegaWarps Plugin."));
        } else {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
        }
        
      }
      
      if(args.length == 1) {
        if(player.hasPermission("omegawarps.reload") || player.isOp()) {
          if(args[0].equalsIgnoreCase("reload")) {
            plugin.onReload();
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bOmegaWarps has been successfully reloaded!"));
          } else {
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
          }
        }
      }
    }
    return true;
  }
}
