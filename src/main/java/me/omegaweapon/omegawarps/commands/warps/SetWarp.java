package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetWarp extends PlayerCommand {
  private final String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(Utilities.checkPermission(player, "omegawarps.setwarp", true)) {
      if(strings.length == 0) {
        Utilities.message(player, prefix + " &b/setwarp <player name> <warp name> - Create a warp for the given player.");
        Utilities.message(player, prefix + " &b/setwarp <warp name> - Create a warp with no owner set.");
      }

      if(strings.length == 1) {
        Warps.createWarp(player, strings[0].toLowerCase(), player.getLocation());
      }

      if(Utilities.checkPermission(player, "omegawarps.setwarp.others", true)) {
        if(strings.length == 2) {
          String warpOwnerName = strings[0];
          Player warpOwner = Bukkit.getPlayer(warpOwnerName);

          if(warpOwner != null) {
            Warps.createWarpOthers(player, Bukkit.getPlayer(strings[0]), strings[1].toLowerCase(), player.getLocation(), OmegaWarps.getConfigFile().getConfig().getDouble("Warp_Cost.Cost"));
          } else {
            Utilities.message(player, prefix + " &cSorry, that player does not exist or they are offline.");
          }
        }
      }
    }
  }
}
