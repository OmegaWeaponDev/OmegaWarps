package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WarpList extends PlayerCommand {
  private final MessageHandler messagesFile = new MessageHandler(OmegaWarps.getInstance().getMessagesFile().getConfig());

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true,"omegawarps.listwarps", "omegawarps.admin")) {
      Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(OmegaWarps.getInstance().getWarpsFile().getConfig().getKeys(false).size() == 0) {
      Utilities.message(player, messagesFile.getPrefix() + "&cThere are no warps currently set.");
      return;
    }

    Utilities.message(player, messagesFile.getPrefix() + "&bThe current warps are:");

    for(String warpName : OmegaWarps.getInstance().getWarpsFile().getConfig().getKeys(false)) {
      Utilities.message(player, "&c" + warpName);
    }
  }
}
