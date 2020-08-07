package me.omegaweapondev.omegawarps.commands;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MainCommand extends GlobalCommand {

  @Override
  protected void execute(final CommandSender sender, final String[] strings) {

    if(strings.length != 1) {
      invalidArgsCommand(sender);
      return;
    }

    switch (strings[0]) {
      case "version":
        versionCommand(sender);
        break;
      case "help":
        helpCommand(sender);
        break;
      case "reload":
        reloadCommand(sender);
        break;
      default:
        invalidArgsCommand(sender);
        break;
    }
  }

  private void invalidArgsCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      Utilities.message(player,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + " &bOmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&c/omegawarps help &bto display all the commands"
      );
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      Utilities.logInfo(true,
        "OmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev",
        "&c/omegawarps help &bto display all the commands"
      );
    }
  }

  private void reloadCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.reload", "omegawarps.*")) {
        Utilities.message(player, MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + MessageHandler.playerMessage("No_Permission", "&cSorry, but you do not have permission to use this command."));
        return;
      }

      OmegaWarps.getInstance().onReload();
      Utilities.message(player, MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + MessageHandler.playerMessage("Reload_Message", "&bOmegaWarps has successfully been reloaded"));
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      OmegaWarps.getInstance().onReload();
      Utilities.logInfo(true, MessageHandler.consoleMessage("Reload_Message", "OmegaWarps has successfully been reloaded"));
    }
  }

  private void versionCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      Utilities.message(player, MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "OmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev");
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      Utilities.logInfo(true, "OmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev");
    }
  }

  private void helpCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      Utilities.message(player,
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + " &bOmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bReload Command: &c/omegawarps reload",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bVersion Command: &c/omegawarps version",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bSetWarp command: &c/setwarp <player> <warp> &b& &c/setwarp <warp>",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWarpList command: &c/listwarps",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bRemoveWarp command: &c/delwarp <warp>",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWarpCheck command: &c/checkwarp <warp>",
        MessageHandler.playerMessage("Prefix", "&7&l[&aOmegaWarps&7&l]") + "&bWarp command: &c/warp <warp>"
      );
    } else {
      Utilities.logInfo(true,
        " OmegaWarps v" + OmegaWarps.getInstance().getDescription().getVersion() + " By OmegaWeaponDev",
        "Reload Command: /omegawarps reload",
        "Version Command: /omegawarps version",
        "SetWarp command: /setwarp <player> <warp> & /setwarp <warp>",
        "WarpList command: /listwarps",
        "RemoveWarp command: /delwarp <warp>",
        "WarpCheck command: /checkwarp <warp>",
        "Warp command: /warp <warp>"
      );
    }
  }

}
