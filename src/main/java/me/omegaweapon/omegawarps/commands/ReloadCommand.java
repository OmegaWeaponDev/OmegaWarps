package me.omegaweapon.omegawarps.commands;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends Command {
  
  public ReloadCommand() {
    super("omegawarps");
    
    setPermission("omegawarps.reload");
    setPermissionMessage(Utilities.Colourize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
    setDescription("Reload command for the OmegaWarps plugin.");
    
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
      
      if(args.length == 0) {
        player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " /omegawarps reload - Reload the OmegaWarps Plugin."));
      }
      
      if(args.length == 1) {
        if(args[0].equalsIgnoreCase("reload")) {
          OmegaWarps.getInstance().onReload();
          player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + " &bOmegaWarps has been successfully reloaded!"));
        }
      }
    }
    return true;
  }
}
