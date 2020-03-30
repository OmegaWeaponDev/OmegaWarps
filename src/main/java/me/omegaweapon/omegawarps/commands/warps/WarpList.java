package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class WarpList extends PlayerCommand {
  String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");
  
  public WarpList() {
    super("listwarps");

    // Set the permission and permission message
    setPermission("omegawarps.listwarps");
    setPermissionMessage(Utilities.colourise(prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission")));

    // Set the description message and the usage
    setDescription("List all the available warps that has been set.");
    setUsage("/listwarps");

    // Set the aliases for the command
    setAliases(Arrays.asList("warps", "warpslist"));
  }

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    Utilities.message(player, prefix + " &bThe current warps are:");

    for(String warpName : OmegaWarps.getWarpsFile().getConfig().getKeys(false)) {
      Utilities.message(player, prefix + " &c" + warpName);
    }
  }
}
