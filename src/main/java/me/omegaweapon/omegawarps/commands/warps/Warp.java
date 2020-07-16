package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.utils.MessageHandler;
import me.omegaweapon.omegawarps.utils.Warps;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Warp extends GlobalCommand {

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;


      if(strings.length == 0) {
        Utilities.message(player,
          MessageHandler.pluginPrefix() + " &b Warp: &c/warp <warpname> &b- Warp to a specific place.",
          MessageHandler.pluginPrefix() + " &bWarp Others &c/warp <warpname> <playername> &b- Send another player to a warp location"
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
      }

      final Player target = Bukkit.getPlayer(strings[1]);
      final String warpName = strings[0];

      playerWarpOthers(sender, target, warpName);
    }
  }

  private void playerWarp(final Player player, final String warpName) {

    if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
      Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, that warp does not exist!");
      return;
    }

    if(!OmegaWarps.getInstance().getConfigFile().getConfig().getBoolean("Per_Warp_Permissions")) {

      if(!Utilities.checkPermissions(player, true, "omegawarps.warps", "omegawarps.*")) {
        Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      Warps.beforeWarpEffects(player);
      Warps.postWarpEffects(player, warpName);
      return;
    }

    if(!Utilities.checkPermissions(player, true, "omegawarps.warp." + warpName, "omegawarps.warp.*", "omegawarps.*")) {
      Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    Warps.beforeWarpEffects(player);
    Warps.postWarpEffects(player, warpName);
  }

  private void playerWarpOthers(final CommandSender sender, final Player target, final String warpName) {

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, that warp does not exist!");
        return;
      }

      if(!Utilities.checkPermissions(player, true, "omegawarps.warps.others", "omegawarps.*")) {
        Utilities.message(player, MessageHandler.playerMessage("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      if(target == null) {
        Utilities.message(player, MessageHandler.pluginPrefix() + " &cSorry, but that player is either offline or does not exist");
        return;
      }

      Warps.beforeWarpEffects(target);
      Warps.postWarpEffects(target, warpName);
      return;
    }

    if(sender instanceof ConsoleCommandSender) {

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.logInfo(true, "Sorry, that warp does not exist!");
        return;
      }

      if(target == null) {
        Utilities.logInfo(true, "Sorry, but that player is either offline or does not exist");
        return;
      }

      Warps.beforeWarpEffects(target);
      Warps.postWarpEffects(target, warpName);
    }
  }
}
