package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class WarpList extends PlayerCommand {
  private final OmegaWarps plugin;
  private final MessageHandler messageHandler;

  public WarpList(final OmegaWarps plugin) {
    this.plugin = plugin;
    messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
  }

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true,"omegawarps.listwarps", "omegawarps.admin")) {
      Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
      return;
    }

    if(plugin.getWarpsFile().getConfig().getKeys(false).size() == 0) {
      Utilities.message(player, messageHandler.getPrefix() + "#ff4a4aThere are no warps currently set.");
      return;
    }

    Utilities.message(player, messageHandler.getPrefix() + "#14abc9The current warps are:");

    for(String warpName : plugin.getWarpsFile().getConfig().getKeys(false)) {
      Utilities.message(player, "#ff4a4a" + warpName);
    }
  }
}
