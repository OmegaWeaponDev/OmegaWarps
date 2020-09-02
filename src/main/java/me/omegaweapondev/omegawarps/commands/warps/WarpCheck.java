package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WarpCheck extends PlayerCommand {
  private final FileConfiguration warpConfigFile = OmegaWarps.getInstance().getWarpsFile().getConfig();
  private final MessageHandler messagesFile = new MessageHandler(OmegaWarps.getInstance().getMessagesFile().getConfig());

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.checkwarp", "omegawarps.admin")) {
      Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, messagesFile.getPrefix() + "&b/checkwarp <warp name> - View the information about a specific warp");
      return;
    }

    String warpName = strings[0];

    if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
      Utilities.message(player, messagesFile.string("Invalid_Warp_Name", "&cSorry, that warp does not exist."));
      return;
    }

    String warpCreator = warpConfigFile.getString(warpName + ".Set By");
    String warpOwner = warpConfigFile.getString(warpName + ".Set For");
    String timeSet = warpConfigFile.getString(warpName + ".Time Set");

    if(warpOwner != null) {
      Utilities.message(player,
        messagesFile.getPrefix()  + "&bWarp Name: &c" + warpName,
        messagesFile.getPrefix() + "&bSet By: &c" + warpCreator,
        messagesFile.getPrefix() + "&bSet For: &c" + warpOwner,
        messagesFile.getPrefix() + "&bTime Set: &c" + timeSet,
        messagesFile.getPrefix() + "&bWorld: &c" + warpConfigFile.getString(warpName + ".Warp Location.World"),
        messagesFile.getPrefix() + "&bLocation X: &c" + warpConfigFile.getString(warpName + ".Warp Location.X"),
        messagesFile.getPrefix() + "&bLocation Y: &c" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
        messagesFile.getPrefix() + "&bLocation Z: &c" + warpConfigFile.getString(warpName + ".Warp Location.Z")
      );
      return;
    }

    Utilities.message(player,
      messagesFile.getPrefix() + "&bWarp Name: &c" + warpName,
      messagesFile.getPrefix() + "&bSet By: &c" + warpCreator,
      messagesFile.getPrefix() + "&bTime Set: &c" + timeSet,
      messagesFile.getPrefix() + "&bWorld: &c" + warpConfigFile.getString(warpName + ".Warp Location.World"),
      messagesFile.getPrefix() + "&bLocation X: &c" + warpConfigFile.getString(warpName + ".Warp Location.X"),
      messagesFile.getPrefix() + "&bLocation Y: &c" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
      messagesFile.getPrefix() + "&bLocation Z: &c" + warpConfigFile.getString(warpName + ".Warp Location.Z")
    );
  }

}
