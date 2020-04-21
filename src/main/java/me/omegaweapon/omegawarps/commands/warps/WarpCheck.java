package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WarpCheck extends PlayerCommand {

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(Utilities.checkPermission(player, "omegawarps.checkwarp", true)) {
      if(strings.length == 0) {
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &b/checkwarp <warp name> - View the information about a specific warp");
      }

      if(strings.length == 1) {
        String warpName = strings[0].toLowerCase();

        if(OmegaWarps.getWarpsFile().getConfig().isSet(warpName)) {
          String warpCreator = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Set By");
          String warpOwner = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Set For");
          String timeSet = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Time Set");

          if(warpOwner != null) {
            Utilities.message(player,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bWarp Name: &c" + warpName,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bSet By: &c" + warpCreator,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bSet For: &c" + warpOwner,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bTime Set: &c" + timeSet,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bWorld: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.World"),
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bLocation X: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.X"),
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bLocation Y: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Y"),
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bLocation Z: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Z")
            );
          } else {
            Utilities.message(player,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bWarp Name: &c" + warpName,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bSet By: &c" + warpCreator,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bTime Set: &c" + timeSet,
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bWorld: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.World"),
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bLocation X: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.X"),
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bLocation Y: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Y"),
              OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + "&bLocation Z: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Z")
            );
          }
        } else {
          Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &cSorry, that warp does not exist.");
        }
      }
    } else {
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
    }
  }
}
