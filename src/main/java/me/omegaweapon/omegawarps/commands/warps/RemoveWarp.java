package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class RemoveWarp extends PlayerCommand {
  private final String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(Utilities.checkPermission(player, "omegawarps.delwarp", true)) {
      if(strings.length == 0) {
        Utilities.message(player, prefix + " &b/delwarp <warp name> - Removes a specific warp a player has set.");
      }

      if(strings.length == 1) {
        String warpName = strings[0].toLowerCase();

        if(!OmegaWarps.getWarpsFile().getConfig().isSet(warpName)) {
          Utilities.message(player, prefix + " &cSorry, that warp does not exist!");
        }

        if(OmegaWarps.getWarpsFile().getConfig().isSet(warpName)) {
          OmegaWarps.getWarpsFile().getConfig().set(warpName, null);
          try {
            OmegaWarps.getWarpsFile().saveConfig();
          } catch (Exception ex) {
            ex.printStackTrace();
          }

          Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("Remove_Warp_Message").replace("%warpName%", warpName));
        }
      }
    } else {
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
    }
  }
}
