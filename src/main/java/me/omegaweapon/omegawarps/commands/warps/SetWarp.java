package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.MessageHandler;
import me.omegaweapon.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetWarp extends PlayerCommand {

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.setwarp", "omegawarps.*")) {
      Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &b/setwarp <player name> <warp name> - Create a warp for the given player.");
      Utilities.message(player, MessageHandler.pluginPrefix() + " &b/setwarp <warp name> - Create a warp with no owner set.");
      return;
    }

    if(strings.length == 1) {
      Warps.createWarp(player, strings[0].toLowerCase(), player.getLocation());
      return;
    }

    if(!Utilities.checkPermission(player, true,"omegawarps.setwarp.others")) {
      Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 2) {
      String warpOwnerName = strings[0];
      Player warpOwner = Bukkit.getPlayer(warpOwnerName);

      if(warpOwner == null) {
        Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, that player does not exist or they are offline.");
        return;
      }

      Warps.createWarpOthers(player, Bukkit.getPlayer(strings[0]), strings[1].toLowerCase(), player.getLocation(), OmegaWarps.getInstance().getConfigFile().getConfig().getDouble("Warp_Cost.Cost"));
    }
  }
}
