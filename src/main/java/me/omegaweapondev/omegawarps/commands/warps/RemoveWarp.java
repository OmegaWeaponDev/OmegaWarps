package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RemoveWarp extends GlobalCommand implements TabCompleter {
  private final OmegaWarps plugin;
  private final MessageHandler messageHandler;

  public RemoveWarp(final OmegaWarps plugin) {
    this.plugin = plugin;
    messageHandler = new MessageHandler(plugin, plugin.getSettingsHandler().getMessagesFile().getConfig());
  }

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.delwarp", "omegawarps.admin")) {
        Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
        return;
      }

      if(strings.length != 1) {
        Utilities.message(player, messageHandler.getPrefix() + "#14abc9/delwarp <warp name> - Removes a specific warp a player has set.");
        return;
      }

      String warpName = strings[0];

      if(!plugin.getSettingsHandler().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.message(player, messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist."));
        return;
      }

      plugin.getSettingsHandler().getWarpsFile().getConfig().set(warpName, null);
      plugin.getSettingsHandler().getWarpsFile().saveConfig();

      Utilities.message(player, messageHandler.string("Remove_Warp_Message", "#14abc9You have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      if(strings.length != 1) {
        Utilities.logInfo(true, "/delwarp <warp name> - Removes a specific warp a player has set.");
        return;
      }

      String warpName = strings[0];

      if(!plugin.getSettingsHandler().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.logInfo(true, "Sorry, that warp does not exist!");
        return;
      }

      plugin.getSettingsHandler().getWarpsFile().getConfig().set(warpName, null);
      plugin.getSettingsHandler().getWarpsFile().saveConfig();

      Utilities.logInfo(true, messageHandler.string("Remove_Warp_Message", "#14abc9You have successfully deleted the warp %warpName%").replace("%warpName%", warpName));
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    return Collections.emptyList();
  }
}
