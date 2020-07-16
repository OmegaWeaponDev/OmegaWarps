package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WarpList extends PlayerCommand {

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true,"omegawarps.listwarps", "omegawarps.*")) {
      Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(OmegaWarps.getInstance().getWarpsFile().getConfig().getKeys(false).size() == 0) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &cThere are no warps currently set.");
      return;
    }

    Utilities.message(player, MessageHandler.pluginPrefix() + " &bThe current warps are:");

    for(String warpName : OmegaWarps.getInstance().getWarpsFile().getConfig().getKeys(false)) {
      Utilities.message(player, OmegaWarps.getInstance().getMessagesFile().getConfig().getString("Prefix") + " &c" + warpName);
    }
  }
}
