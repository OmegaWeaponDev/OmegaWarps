package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class WarpCheck extends PlayerCommand implements TabCompleter {
  private final OmegaWarps plugin;
  private final FileConfiguration warpConfigFile;
  private final MessageHandler messageHandler;
  
  public WarpCheck(final OmegaWarps plugin) {
    this.plugin = plugin;
    warpConfigFile = plugin.getSettingsHandler().getWarpsFile().getConfig();
    messageHandler = new MessageHandler(plugin, plugin.getSettingsHandler().getMessagesFile().getConfig());
  }

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.checkwarp", "omegawarps.admin")) {
      Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, messageHandler.getPrefix() + "#14abc9/checkwarp <warp name> - View the information about a specific warp");
      return;
    }

    String warpName = strings[0];

    if(!warpConfigFile.isSet(warpName)) {
      Utilities.message(player, messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist."));
      return;
    }

    String warpCreator = warpConfigFile.getString(warpName + ".Set By");
    String warpOwner = warpConfigFile.getString(warpName + ".Set For");
    String timeSet = warpConfigFile.getString(warpName + ".Time Set");

    if(warpOwner != null) {
      Utilities.message(player,
        messageHandler.getPrefix()  + "#14abc9Warp Name: #ff4a4a" + warpName,
        messageHandler.getPrefix() + "#14abc9Set By: #ff4a4a" + warpCreator,
        messageHandler.getPrefix() + "#14abc9Set For: #ff4a4a" + warpOwner,
        messageHandler.getPrefix() + "#14abc9Time Set: #ff4a4a" + timeSet,
        messageHandler.getPrefix() + "#14abc9World: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.World"),
        messageHandler.getPrefix() + "#14abc9Location X: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.X"),
        messageHandler.getPrefix() + "#14abc9Location Y: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
        messageHandler.getPrefix() + "#14abc9Location Z: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.Z")
      );
      return;
    }

    Utilities.message(player,
      messageHandler.getPrefix() + "#14abc9Warp Name: #ff4a4a" + warpName,
      messageHandler.getPrefix() + "#14abc9Set By: #ff4a4a" + warpCreator,
      messageHandler.getPrefix() + "#14abc9Time Set: #ff4a4a" + timeSet,
      messageHandler.getPrefix() + "#14abc9World: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.World"),
      messageHandler.getPrefix() + "#14abc9Location X: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.X"),
      messageHandler.getPrefix() + "#14abc9Location Y: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.Y"),
      messageHandler.getPrefix() + "#14abc9Location Z: #ff4a4a" + warpConfigFile.getString(warpName + ".Warp Location.Z")
    );
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    return Collections.emptyList();
  }
}
