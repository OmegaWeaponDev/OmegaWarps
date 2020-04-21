package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class ClearWarps extends PlayerCommand {

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(Utilities.checkPermission(player, "omegawarps.clearwarps", true)) {
      for(String warpName : OmegaWarps.getWarpsFile().getConfig().getKeys(false)) {
        OmegaWarps.getWarpsFile().getConfig().set(warpName, null);
      }
      try {
        OmegaWarps.getWarpsFile().saveConfig();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Clear_Warps_Message"));
    } else {
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
    }
  }
}
