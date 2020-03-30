package me.omegaweapon.omegawarps.commands;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MainCommand extends PlayerCommand {
  private String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");

  public MainCommand() {
    super("omegawarps");

    // Set the description message
    setDescription("The main command for the OmegaWarp plugin");

    // Set the command aliases
    setAliases(Arrays.asList(
      "ow",
      "owarps"
    ));
  }

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(strings.length == 0) {
      Utilities.message(player, prefix + " &bRunning version: &c" + OmegaWarps.getInstance().getDescription().getVersion());
    }

    if (strings.length == 1) {
      if(strings[0].equalsIgnoreCase("help")) {
        Utilities.message(player,
          prefix + " &bReload Command: &c/omegawarps reload",
          prefix + " &bVersion Command: &c/omegawarps version",
          prefix + " &bSetWarp command: &c/setwarp <player> <warp> &b& &c/setwarp <warp>",
          prefix + " &bWarpList command: &c/listwarps",
          prefix + " &bRemoveWarp command: &c/delwarp <warp>",
          prefix + " &bWarpCheck command: &c/checkwarp <warp>",
          prefix + " &bWarp command: &c/warp <warp>"
        );
      }

      if(strings[0].equalsIgnoreCase("version")) {
        Utilities.message(player, prefix + " &bRunning version: &c" + OmegaWarps.getInstance().getDescription().getVersion());
      }

      if(strings[0].equalsIgnoreCase("reload")) {
        if(Utilities.checkPermission(player, "omegawarps.reload", true)) {
          OmegaWarps.getInstance().onReload();
          Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("Reload_Message"));
        } else {
          Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission"));
        }
      }
    }
  }
}
