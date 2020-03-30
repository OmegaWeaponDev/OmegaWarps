package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WarpCheck extends PlayerCommand {
  String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");
  
  public WarpCheck() {
    super("checkwarp");

    // Set the permission and permission message
    setPermission("omegawarps.checkwarp");
    setPermissionMessage(Utilities.colourise(prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission")));

    // Set the description message and the usage
    setDescription("View details about a particular warp that has been set.");
    setUsage("/checkwarp <warp>");
  }

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(strings.length == 0) {
      Utilities.message(player, prefix + " &b/warpcheck <warp name> - View the information about a specific warp");
    }

    if(strings.length == 1) {
      String warpName = strings[0].toLowerCase();

      if(OmegaWarps.getWarpsFile().getConfig().isSet(warpName)) {
        String warpCreator = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Set By");
        String warpOwner = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Set For");
        String timeSet = OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Time Set");

        if(warpOwner != null) {
          Utilities.message(player,
            prefix + "&bWarp Name: &c" + warpName,
            prefix + "&bSet By: &c" + warpCreator,
            prefix + "&bSet For: &c" + warpOwner,
            prefix + "&bTime Set: &c" + timeSet,
            prefix + "&bWorld: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.World"),
            prefix + "&bLocation X: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.X"),
            prefix + "&bLocation Y: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Y"),
            prefix + "&bLocation Z: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Z")
          );
        } else {
          Utilities.message(player,
            prefix + "&bWarp Name: &c" + warpName,
            prefix + "&bSet By: &c" + warpCreator,
            prefix + "&bTime Set: &c" + timeSet,
            prefix + "&bWorld: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.World"),
            prefix + "&bLocation X: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.X"),
            prefix + "&bLocation Y: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Y"),
            prefix + "&bLocation Z: &c" + OmegaWarps.getWarpsFile().getConfig().getString(warpName + ".Warp Location.Z")
          );
        }
      } else {
        Utilities.message(player, prefix + " &cSorry, that warp does not exist.");
      }
    }
  }
}
