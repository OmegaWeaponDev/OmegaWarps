package me.omegaweapon.omegawarps.commands;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class MainCommand extends PlayerCommand {

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(strings.length == 0) {
      Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bRunning version: &c" + OmegaWarps.getInstance().getDescription().getVersion());
    }

    if (strings.length == 1) {
      if(strings[0].equalsIgnoreCase("help")) {
        Utilities.message(player,
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bReload Command: &c/omegawarps reload",
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bVersion Command: &c/omegawarps version",
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bSetWarp command: &c/setwarp <player> <warp> &b& &c/setwarp <warp>",
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bWarpList command: &c/listwarps",
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bRemoveWarp command: &c/delwarp <warp>",
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bWarpCheck command: &c/checkwarp <warp>",
          OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bWarp command: &c/warp <warp>"
        );
      }

      if(strings[0].equalsIgnoreCase("version")) {
        Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " &bRunning version: &c" + OmegaWarps.getInstance().getDescription().getVersion());
      }

      if(strings[0].equalsIgnoreCase("reload")) {
        if(Utilities.checkPermission(player, "omegawarps.reload", true)) {
          OmegaWarps.getInstance().onReload();
          Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("Reload_Message"));
        } else {
          Utilities.message(player, OmegaWarps.getMessagesFile().getConfig().getString("Prefix") + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
        }
      }
    }
  }
}
