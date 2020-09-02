package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.omegaweapondev.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetWarp extends PlayerCommand {
  private final MessageHandler messagesFile = new MessageHandler(OmegaWarps.getInstance().getMessagesFile().getConfig());

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.setwarp", "omegawarps.admin")) {
      Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, messagesFile.getPrefix() + "&b/setwarp <player name> <warp name> - Create a warp for the given player.");
      Utilities.message(player, messagesFile.getPrefix() + "&b/setwarp <warp name> - Create a warp with no owner set.");
      return;
    }

    final Warps warpHandler = new Warps(player, strings[0]);

    if(strings.length == 1) {
      warpHandler.createWarp(player.getLocation());
      return;
    }

    if(!Utilities.checkPermissions(player, true,"omegawarps.setwarp.others", "omegawarps.admin")) {
      Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 2) {
      String warpOwnerName = strings[0];
      Player warpOwner = Bukkit.getPlayer(warpOwnerName);

      if(warpOwner == null) {
        Utilities.message(player, messagesFile.string("Invalid_Player", "&cSorry, but no player with that name was found."));
        return;
      }

      warpHandler.createWarpOthers(Bukkit.getPlayer(strings[0]), player.getLocation(), OmegaWarps.getInstance().getConfigFile().getConfig().getDouble("Warp_Cost.Cost"));
    }
  }
}
