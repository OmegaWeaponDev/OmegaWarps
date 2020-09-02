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
  private final MessageHandler messagesFile = new MessageHandler(OmegaWarps.getInstance().getMessagesFile().getConfig());

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(sender instanceof Player) {
      Player player = (Player) sender;


      if(strings.length == 0) {
        Utilities.message(player,
          messagesFile.getPrefix() + "&bWarp: &c/warp <warpname> &b- Warp to a specific place.",
          messagesFile.getPrefix() + "&bWarp Others &c/warp <warpname> <playername> &b- Send another player to a warp location"
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
    final Warps warpHandler = new Warps(player, warpName);

    if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
      Utilities.message(player, messagesFile.string("Invalid_Warp_Name", "&cSorry, that warp does not exist!"));
      return;
    }

    if(!OmegaWarps.getInstance().getConfigFile().getConfig().getBoolean("Per_Warp_Permissions")) {

      if(!Utilities.checkPermissions(player, true, "omegawarps.warps", "omegawarps.admin")) {
        Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      warpHandler.beforeWarpEffects();
      warpHandler.postWarpEffects();
      return;
    }

    if(!Utilities.checkPermissions(player, true, "omegawarps.warp." + warpName.toLowerCase(), "omegawarps.warp.all", "omegawarps.admin")) {
      Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
      return;
    }

    warpHandler.beforeWarpEffects();
    warpHandler.postWarpEffects();
  }

  private void playerWarpOthers(final CommandSender sender, final Player target, final String warpName) {
    final Warps warpHandler = new Warps(target, warpName);

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.message(player, messagesFile.string("Invalid_Warp_Name", "&cSorry, that warp does not exist!"));
        return;
      }

      if(!Utilities.checkPermissions(player, true, "omegawarps.warps.others", "omegawarps.*")) {
        Utilities.message(player, messagesFile.string("No_Permission", "&cSorry, you do not have permission to do that."));
        return;
      }

      if(target == null) {
        Utilities.message(player, messagesFile.string("Invalid_Player", "&cSorry, but no player with that name was found."));
        return;
      }

      warpHandler.beforeWarpEffects();
      warpHandler.postWarpEffects();
      return;
    }

    if(sender instanceof ConsoleCommandSender) {

      if(!OmegaWarps.getInstance().getWarpsFile().getConfig().isSet(warpName)) {
        Utilities.logInfo(true, messagesFile.console("Invalid_Warp_Name", "Sorry, that warp does not exist!"));
        return;
      }

      if(target == null) {
        Utilities.logInfo(true, messagesFile.console("Invalid_Player", "Sorry, but no player with that name was found."));
        return;
      }

      warpHandler.beforeWarpEffects();
      warpHandler.postWarpEffects();
    }
  }
}
