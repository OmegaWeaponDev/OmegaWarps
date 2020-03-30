package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RemoveWarp extends PlayerCommand {
  String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");

  public RemoveWarp() {
    super("delwarp");

    // Set the permission and permission message
    setPermission("omegawarps.delwarp");
    setPermissionMessage(Utilities.colourise(prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission")));

    // Set the command description
    setDescription("Remove warps that players have set.");

    // Set the usage message
    setUsage("/delwarp <warp name>");

    // Set some aliases for the command
    setAliases(Arrays.asList("removewarp", "deletewarp"));
  }

  @Override
  protected void onCommand(final Player player, final String[] strings) {

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
        OmegaWarps.getWarpsFile().saveConfig();

        Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("Remove_Warp_Message").replace("%warpName%", warpName));
      }
    }
  }
}
