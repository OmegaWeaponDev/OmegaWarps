package me.omegaweapondev.omegawarps.commands;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.Utilities;
import me.ou.library.builders.TabCompleteBuilder;
import me.ou.library.commands.GlobalCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class MainCommand extends GlobalCommand implements TabCompleter {
  private final OmegaWarps plugin;
  private final MessageHandler messageHandler;

  public MainCommand(final OmegaWarps plugin) {
    this.plugin = plugin;
    messageHandler = new MessageHandler(plugin, plugin.getSettingsHandler().getMessagesFile().getConfig());
  }

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
        messageHandler.getPrefix() + "#14abc9OmegaWarps #ff4a4av" + plugin.getDescription().getVersion() + " #14abc9By OmegaWeaponDev",
        messageHandler.getPrefix() + "#ff4a4a/omegawarps help #14abc9to display all the commands"
      );
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      Utilities.logInfo(true,
        "OmegaWarps v" + plugin.getDescription().getVersion() + " By OmegaWeaponDev",
        "/omegawarps help to display all the commands"
      );
    }
  }

  private void reloadCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(!Utilities.checkPermissions(player, true, "omegawarps.reload", "omegawarps.admin")) {
        Utilities.message(player, messageHandler.string("No_Permission", "#ff4a4aSorry, but you do not have permission to use this command."));
        return;
      }

      plugin.onReload();
      Utilities.message(player, messageHandler.string("Reload_Message", "#14abc9OmegaWarps has successfully been reloaded"));
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      plugin.onReload();
      Utilities.logInfo(true, messageHandler.console("Reload_Message", "OmegaWarps has successfully been reloaded"));
    }
  }

  private void versionCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      Utilities.message(player, messageHandler.getPrefix() + "#14abc9OmegaWarps #ff4a4av" + plugin.getDescription().getVersion() + " #14abc9By OmegaWeaponDev");
      return;
    }

    if(sender instanceof ConsoleCommandSender) {
      Utilities.logInfo(true, "OmegaWarps v" + plugin.getDescription().getVersion() + " By OmegaWeaponDev");
    }
  }

  private void helpCommand(final CommandSender sender) {
    if(sender instanceof Player) {
      Player player = (Player) sender;

      Utilities.message(player,
        messageHandler.getPrefix() + " #14abc9OmegaWarps v" + plugin.getDescription().getVersion() + " By OmegaWeaponDev",
        messageHandler.getPrefix() + "#14abc9Reload Command: #ff4a4a/omegawarps reload",
        messageHandler.getPrefix() + "#14abc9Version Command: #ff4a4a/omegawarps version",
        messageHandler.getPrefix() + "#14abc9SetWarp command: #ff4a4a/setwarp <player> <warp> #14abc9& #ff4a4a/setwarp <warp>",
        messageHandler.getPrefix() + "#14abc9WarpList command: #ff4a4a/listwarps",
        messageHandler.getPrefix() + "#14abc9RemoveWarp command: #ff4a4a/delwarp <warp>",
        messageHandler.getPrefix() + "#14abc9WarpCheck command: #ff4a4a/checkwarp <warp>",
        messageHandler.getPrefix() + "#14abc9Warp command: #ff4a4a/warp <warp>"
      );
    }

    if(sender instanceof ConsoleCommandSender) {
      Utilities.logInfo(true,
        " OmegaWarps v" + plugin.getDescription().getVersion() + " By OmegaWeaponDev",
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

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
    if(strings.length <= 1) {
      return new TabCompleteBuilder(commandSender)
        .checkCommand("reload", true, "omegawarps.reload", "omegawarps.admin")
        .checkCommand("help", true, "omegawarps.admin")
        .checkCommand("version", true, "omegawarps.admin")
        .build(strings[0]);
    }

    return Collections.emptyList();
  }
}
