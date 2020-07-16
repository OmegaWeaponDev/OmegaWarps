package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WarpCheck extends PlayerCommand {
  private static final FileConfiguration warpConfigFile = OmegaWarps.getInstance().getWarpsFile().getConfig();

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.checkwarp", "omegawarps.*")) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " " + MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &b/checkwarp <warp name> - View the information about a specific warp");
      return;
    }

    String warpName = strings[0].toLowerCase();

    if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, that warp does not exist.");
      return;
    }

    String warpCreator = warpConfigFile.getString(warpName + ".Set By");
    String warpOwner = warpConfigFile.getString(warpName + ".Set For");
    String timeSet = warpConfigFile.getString(warpName + ".Time Set");

    if(warpOwner != null) {
      Utilities.message(player,
        MessageHandler.pluginPrefix() + "&bWarp Name: &c" + warpName,
        MessageHandler.pluginPrefix() + "&bSet By: &c" + warpCreator,
        MessageHandler.pluginPrefix() + "&bSet For: &c" + warpOwner,
        MessageHandler.pluginPrefix() + "&bTime Set: &c" + timeSet,
        MessageHandler.pluginPrefix() + "&bWorld: &c" + warpConfigFile.getString(warpName + ".Warp Location.World"),
        MessageHandler.pluginPrefix() + "&bLocation X: &c" + warpConfigFile.getString(warpName + ".Warp Location.X"),
        MessageHandler.pluginPrefix() + "&bLocation Y: &c" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
        MessageHandler.pluginPrefix() + "&bLocation Z: &c" + warpConfigFile.getString(warpName + ".Warp Location.Z")
      );
      return;
    }

    Utilities.message(player,
      MessageHandler.pluginPrefix() + "&bWarp Name: &c" + warpName,
      MessageHandler.pluginPrefix() + "&bSet By: &c" + warpCreator,
      MessageHandler.pluginPrefix() + "&bTime Set: &c" + timeSet,
      MessageHandler.pluginPrefix() + "&bWorld: &c" + warpConfigFile.getString(warpName + ".Warp Location.World"),
      MessageHandler.pluginPrefix() + "&bLocation X: &c" + warpConfigFile.getString(warpName + ".Warp Location.X"),
      MessageHandler.pluginPrefix() + "&bLocation Y: &c" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
      MessageHandler.pluginPrefix() + "&bLocation Z: &c" + warpConfigFile.getString(warpName + ".Warp Location.Z")
    );
  }

}
