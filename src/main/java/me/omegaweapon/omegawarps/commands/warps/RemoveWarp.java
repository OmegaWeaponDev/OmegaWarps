package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RemoveWarp extends GlobalCommand {

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.delwarp", "omegawarps.*")) {
        Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      if(strings.length != 1) {
        Utilities.message(player, MessageHandler.pluginPrefix() + " &b/delwarp <warp name> - Removes a specific warp a player has set.");
        return;
      }

      String warpName = strings[0].toLowerCase();

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, that warp does not exist!");
        return;
      }

      OmegaWarps.getInstance().getWarpsFile().getConfig().set(warpName, null);
      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      Utilities.message(player, MessageHandler.playerMessage("Remove_Warp_Message", "&bYou have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      if(strings.length != 1) {
        Utilities.logInfo(true, "/delwarp <warp name> - Removes a specific warp a player has set.");
        return;
      }

      String warpName = strings[0].toLowerCase();

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.logInfo(true, "Sorry, that warp does not exist!");
        return;
      }

      OmegaWarps.getInstance().getWarpsFile().getConfig().set(warpName, null);
      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      Utilities.logInfo(true, MessageHandler.playerMessage("Remove_Warp_Message", "&bYou have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
    }





















  }
}
