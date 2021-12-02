package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.omegaweapondev.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.builders.TabCompleteBuilder;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetWarp extends PlayerCommand implements TabCompleter {
  private final OmegaWarps plugin;
  private final MessageHandler messageHandler;
  
  public SetWarp(final OmegaWarps plugin) {
    this.plugin = plugin;
    messageHandler = new MessageHandler(plugin, plugin.getSettingsHandler().getMessagesFile().getConfig());
  }

  @Override
  protected void execute(final Player player, final String[] strings) {

    if(!Utilities.checkPermissions(player, true, "omegawarps.setwarp", "omegawarps.admin")) {
      Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 0) {
      Utilities.message(player, messageHandler.getPrefix() + "#14abc9/setwarp <player name> <warp name> - Create a warp for the given player.");
      Utilities.message(player, messageHandler.getPrefix() + "#14abc9/setwarp <warp name> - Create a warp with no owner set.");
      return;
    }

    Warps warpHandler = new Warps(plugin, player, strings[0]);

    if(strings.length == 1) {
      warpHandler.createWarp(player.getLocation());
      return;
    }

    if(!Utilities.checkPermissions(player, true,"omegawarps.setwarp.others", "omegawarps.admin")) {
      Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
      return;
    }

    if(strings.length == 2) {
      String warpOwnerName = strings[0];
      Player warpOwner = Bukkit.getPlayer(warpOwnerName);
      warpHandler = new Warps(plugin, player, strings[1]);

      if(warpOwner == null) {
        Utilities.message(player, messageHandler.string("Invalid_Player", "#ff4a4aSorry, but no player with that name was found."));
        return;
      }

      warpHandler.createWarpOthers(Bukkit.getPlayer(strings[0]), player.getLocation(), plugin.getSettingsHandler().getConfigFile().getConfig().getDouble("Warp_Cost.Cost"));
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    if(strings.length <= 1) {
      List<String> players = new ArrayList<>();
      for(Player player : Bukkit.getOnlinePlayers()) {
        players.add(player.getName());
      }

      return new TabCompleteBuilder(commandSender).addCommand(players).build(strings[0]);
    }

    return Collections.emptyList();
  }
}
