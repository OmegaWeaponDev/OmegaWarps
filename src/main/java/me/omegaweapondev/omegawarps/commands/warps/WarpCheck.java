package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WarpCheck extends PlayerCommand {
  private static final FileConfiguration warpConfigFile = OmegaWarps.getInstance().getWarpsFile().getConfig();

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.checkwarp", "omegawarps.*")) {
      Utilities.message(player, MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&b/checkwarp <warp name> - View the information about a specific warp");
      return;
    }

    String warpName = strings[0];

    if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
      Utilities.message(player, MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&cSorry, that warp does not exist.");
      return;
    }

    String warpCreator = warpConfigFile.getString(warpName + ".Set By");
    String warpOwner = warpConfigFile.getString(warpName + ".Set For");
    String timeSet = warpConfigFile.getString(warpName + ".Time Set");

    if(warpOwner != null) {
      Utilities.message(player,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWarp Name: &c" + warpName,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bSet By: &c" + warpCreator,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bSet For: &c" + warpOwner,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bTime Set: &c" + timeSet,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWorld: &c" + warpConfigFile.getString(warpName + ".Warp Location.World"),
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bLocation X: &c" + warpConfigFile.getString(warpName + ".Warp Location.X"),
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bLocation Y: &c" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bLocation Z: &c" + warpConfigFile.getString(warpName + ".Warp Location.Z")
      );
      return;
    }

    Utilities.message(player,
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWarp Name: &c" + warpName,
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bSet By: &c" + warpCreator,
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bTime Set: &c" + timeSet,
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWorld: &c" + warpConfigFile.getString(warpName + ".Warp Location.World"),
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bLocation X: &c" + warpConfigFile.getString(warpName + ".Warp Location.X"),
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bLocation Y: &c" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
      MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bLocation Z: &c" + warpConfigFile.getString(warpName + ".Warp Location.Z")
    );
  }

}
