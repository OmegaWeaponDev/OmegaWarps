package me.omegaweapondev.omegawarps.commands.warps;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.omegaweapondev.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Warp extends GlobalCommand {
  private final OmegaWarps plugin;
  private final MessageHandler messageHandler;
  
  public Warp(final OmegaWarps plugin) {
    this.plugin = plugin;
    messageHandler = new MessageHandler(plugin, plugin.getMessagesFile().getConfig());
  }

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;


      if(strings.length == 0) {
        Utilities.message(player,
          messageHandler.getPrefix() + "#14abc9Warp: #ff4a4a/warp <warpname> #14abc9- Warp to a specific place.",
          messageHandler.getPrefix() + "#14abc9Warp Others #ff4a4a/warp <warpname> <playername> #14abc9- Send another player to a warp location"
        );
        return;
      }

      if(strings.length == 1) {
        String warpName = strings[0];

        playerWarp(player, warpName);
        return;
      }

      if(strings.length == 2) {
        final Player target = Bukkit.getPlayer(strings[1]);
        final String warpName = strings[0];

        playerWarpOthers(player, target, warpName);
        return;
      }
    }

    if(sender instanceof ConsoleCommandSender) {

      if(strings.length != 2) {
        Utilities.logInfo(true, "Warp Others /warp <warpname> <playername> - Send another player to a warp location");
        return;
      }

      final Player target = Bukkit.getPlayer(strings[1]);
      final String warpName = strings[0];

      playerWarpOthers(sender, target, warpName);
    }
  }

  private void playerWarp(final Player player, final String warpName) {
    final Warps warpHandler = new Warps(plugin, player, warpName);

    if(!plugin.getWarpsFile().getConfig().isSet(warpName)) {
      Utilities.message(player, messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist!"));
      return;
    }

    if(!plugin.getConfigFile().getConfig().getBoolean("Per_Warp_Permissions")) {

      if(!Utilities.checkPermissions(player, true, "omegawarps.warps", "omegawarps.admin")) {
        Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
        return;
      }

      warpHandler.beforeWarpEffects();
      warpHandler.postWarpEffects();
      return;
    }

    if(!Utilities.checkPermissions(player, true, "omegawarps.warp." + warpName.toLowerCase(), "omegawarps.warp.all", "omegawarps.admin")) {
      Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
      return;
    }

    warpHandler.beforeWarpEffects();
    warpHandler.postWarpEffects();
  }

  private void playerWarpOthers(final CommandSender sender, final Player target, final String warpName) {
    final Warps warpHandler = new Warps(plugin, target, warpName);

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!plugin.getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.message(player, messageHandler.string("Invalid_Warp_Name", "#ff4a4aSorry, that warp does not exist!"));
        return;
      }

      if(!Utilities.checkPermissions(player, true, "omegawarps.warps.others", "omegawarps.*")) {
        Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, you do not have permission to do that."));
        return;
      }

      if(target == null) {
        Utilities.message(player, messageHandler.string("Invalid_Player", "#ff4a4aSorry, but no player with that name was found."));
        return;
      }

      warpHandler.beforeWarpEffects();
      warpHandler.postWarpEffects();
      return;
    }

    if(sender instanceof ConsoleCommandSender) {

      if(!plugin.getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.logInfo(true, messageHandler.console("Invalid_Warp_Name", "Sorry, that warp does not exist!"));
        return;
      }

      if(target == null) {
        Utilities.logInfo(true, messageHandler.console("Invalid_Player", "Sorry, but no player with that name was found."));
        return;
      }

      warpHandler.beforeWarpEffects();
      warpHandler.postWarpEffects();
    }
  }
}
