package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Warp extends PlayerCommand {
  private static final String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(strings.length == 0) {
      Utilities.message(player,
        prefix + " &b Warp: &c/warp <warpname> &b- Warp to a specific place.",
        prefix + " &bWarp Others &c/warp <warpname> <playername> &b- Send another player to a warp location"
      );
    }

    if(strings.length == 1) {
      String warpName = strings[0].toLowerCase();

      if(OmegaWarps.getConfigFile().getConfig().getBoolean("Per_Warp_Permissions")) {
        if(player.hasPermission("omegawarps.warp." + warpName) || Utilities.checkPermission(player, "omegawarps.warp.*", true)) {
          Warps.beforeWarpEffects(player);
          Warps.postWarpEffects(player, warpName);
        } else {
          Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
        }
      } else {
        if(Utilities.checkPermission(player, "omegawarps.warps", true)) {
          Warps.beforeWarpEffects(player);
          Warps.postWarpEffects(player, warpName);
        } else {
          Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
        }
      }
    }

    if(strings.length == 2) {
      if(Utilities.checkPermission(player, "omegawarps.warps.others", true)) {
        Player target = Bukkit.getPlayer(strings[1]);
        String warpName = strings[0];

        if(target != null) {
          Warps.beforeWarpEffects(target);
          Warps.postWarpEffects(target, warpName);
        } else {
          Utilities.message(player, prefix + " &cSorry, but that player is either offline or does not exist");
        }
      }
    }
  }
}
