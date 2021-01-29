package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ClearWarps extends GlobalCommand {
  private final OmegaWarps plugin;
  private final MessageHandler messageHandler;

  public ClearWarps(final OmegaWarps plugin) {
    this.plugin = plugin;
    messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
  }

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.clearwarps", "omegawarps.admin")) {
        Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
        return;
      }

      for(String warpName : plugin.getWarpsFile().getConfig().getKeys(false)) {
        plugin.getWarpsFile().getConfig().set(warpName, null);
      }

      plugin.getWarpsFile().saveConfig();
      Utilities.message(player, messageHandler.string("Clear_Warps_Message", "#ff4a4aYou have deleted all the warps!"));
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      for(String warpName : plugin.getWarpsFile().getConfig().getKeys(false)) {
        plugin.getWarpsFile().getConfig().set(warpName, null);
      }

      plugin.getWarpsFile().saveConfig();
      Utilities.logInfo(true, "All warps have successfully been deleted.");
    }
  }
}
