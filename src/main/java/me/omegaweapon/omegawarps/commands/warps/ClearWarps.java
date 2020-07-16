package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ClearWarps extends GlobalCommand {

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.clearwarps", "omegawarps.*")) {
        Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      for(String warpName : OmegaWarps.getInstance().getWarpsFile().getConfig().getKeys(false)) {
        OmegaWarps.getInstance().getWarpsFile().getConfig().set(warpName, null);
      }

      OmegaWarps.getInstance().getWarpsFile().saveConfig();
      Utilities.message(player, MessageHandler.playerMessage("Clear_Warps_Message", "&cYou have deleted all the warps!"));
    }

    if(sender instanceof ConsoleCommandSender) {
      for(String warpName : OmegaWarps.getInstance().getWarpsFile().getConfig().getKeys(false)) {
        OmegaWarps.getInstance().getWarpsFile().getConfig().set(warpName, null);
      }

      OmegaWarps.getInstance().getWarpsFile().saveConfig();
      Utilities.logInfo(true, "All warps have successfully been deleted.");
    }
  }
}
