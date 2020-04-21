package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WarpList extends PlayerCommand {
  private final String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(Utilities.checkPermission(player, "omegawarps.listwarps", true)) {
      Utilities.message(player, prefix + " &bThe current warps are:");

      for(String warpName : OmegaWarps.getWarpsFile().getConfig().getKeys(false)) {
        Utilities.message(player, prefix + " &c" + warpName);
      }
    }
  }
}
