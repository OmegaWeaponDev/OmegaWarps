package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RemoveWarp extends GlobalCommand {
  private final MessageHandler messagesFile = new MessageHandler(OmegaWarps.getInstance().getMessagesFile().getConfig());

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.delwarp", "omegawarps.admin")) {
        Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      if(strings.length != 1) {
        Utilities.message(player, messagesFile.getPrefix() + "&b/delwarp <warp name> - Removes a specific warp a player has set.");
        return;
      }

      String warpName = strings[0];

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.message(player, messagesFile.string("Invalid_Warp_Name", "&cSorry, that warp does not exist."));
        return;
      }

      OmegaWarps.getInstance().getWarpsFile().getConfig().set(warpName, null);
      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      Utilities.message(player, messagesFile.string("Remove_Warp_Message", "&bYou have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      if(strings.length != 1) {
        Utilities.logInfo(true, "/delwarp <warp name> - Removes a specific warp a player has set.");
        return;
      }

      String warpName = strings[0];

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.logInfo(true, "Sorry, that warp does not exist!");
        return;
      }

      OmegaWarps.getInstance().getWarpsFile().getConfig().set(warpName, null);
      OmegaWarps.getInstance().getWarpsFile().saveConfig();

      Utilities.logInfo(true, messagesFile.string("Remove_Warp_Message", "&bYou have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
    }





















  }
}
