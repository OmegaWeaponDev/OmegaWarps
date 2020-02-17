package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.ConfigFile;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.settings.WarpFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand extends Command {
  OmegaWarps plugin;
  
  public SetWarpCommand(OmegaWarps plugin) {
    super("setwarp");
    this.plugin = plugin;
  }
  
  @Override
  public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
    if(sender instanceof Player) {
      Player player = (Player) sender;
  
      if (args.length == 0) {
        if (player.hasPermission("omegawarps.set") || player.isOp()) {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &b/setwarp <playername> <warp name> - Sets a warp for a specific player."));
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &b/setwarp <playername> <warp name> - Sets a warp with no owner."));
        } else {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
        }
      }
  
      if (args.length == 1) {
        if (player.hasPermission("omegawarps.set") || player.isOp()) {
          String warpCreator = player.getName();
          String warpName = args[0];
          Location warpLoction = player.getLocation();
  
          WarpFile.getWarpData().createSection(warpName);
          WarpFile.getWarpData().set(warpName + ".Set By", warpCreator);
          WarpFile.getWarpData().set(warpName + ".Warp Location.World", player.getWorld().getName());
          WarpFile.getWarpData().set(warpName + ".Warp Location.X", warpLoction.getX());
          WarpFile.getWarpData().set(warpName + ".Warp Location.Y", warpLoction.getY());
          WarpFile.getWarpData().set(warpName + ".Warp Location.Z", warpLoction.getZ());
          WarpFile.saveWarpData();
  
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.SETWARP_MESSAGE).replace("%warpName%", warpName).replace("%warpOwner%", "Yourself"));
        } else {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
        }
      }
  
      if (args.length == 2) {
        if (player.hasPermission("omegawarps.set") || player.isOp()) {
          String warpCreator = player.getName();
          String warpOwnerName = args[0];
          Player warpOwner = Bukkit.getServer().getPlayer(warpOwnerName);
          String warpName = args[1].toLowerCase();
          Location warpLocation = player.getLocation();
          Double warpCost = ConfigFile.WARP_COST;
  
          if (warpOwner != null) {
  
            if(ConfigFile.WARP_COST_ENABLED.equals(true) && !warpOwner.hasPermission("omegawarps.cost.bypass")) {
    
    
              if(warpOwner.hasPermission("omegawarps.cost")) {
                Double warpOwnerBalance = OmegaWarps.getEconomy().getBalance(warpOwner);
      
                if(warpOwnerBalance < ConfigFile.WARP_COST) {
                  player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &c" + warpOwner + " &bDoes not have the required amount to pay for the warp."));
                } else {
                  WarpFile.getWarpData().createSection(warpName);
                  WarpFile.getWarpData().set(warpName + ".Set By", warpCreator);
                  WarpFile.getWarpData().set(warpName + ".Set For", warpOwnerName);
                  WarpFile.getWarpData().set(warpName + ".Warp Location.World", player.getWorld().getName());
                  WarpFile.getWarpData().set(warpName + ".Warp Location.X", warpLocation.getX());
                  WarpFile.getWarpData().set(warpName + ".Warp Location.Y", warpLocation.getY());
                  WarpFile.getWarpData().set(warpName + ".Warp Location.Z", warpLocation.getZ());
                  WarpFile.saveWarpData();
                  
                  OmegaWarps.getEconomy().withdrawPlayer(warpOwner, warpCost);
                  warpOwner.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bThe amount of price &c$" + warpCost + " &bhas been taken from your account for the warp."));
                }
              }
            } else {
              WarpFile.getWarpData().createSection(warpName);
              WarpFile.getWarpData().set(warpName + ".Set By", warpCreator);
              WarpFile.getWarpData().set(warpName + ".Set For", warpOwnerName);
              WarpFile.getWarpData().set(warpName + ".Warp Location.World", player.getWorld().getName());
              WarpFile.getWarpData().set(warpName + ".Warp Location.X", warpLocation.getX());
              WarpFile.getWarpData().set(warpName + ".Warp Location.Y", warpLocation.getY());
              WarpFile.getWarpData().set(warpName + ".Warp Location.Z", warpLocation.getZ());
              WarpFile.saveWarpData();
            }
            
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.SETWARP_MESSAGE).replace("%warpName%", warpName).replace("%warpOwner%", warpOwnerName));
          } else {
            player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &cSorry, either that player does not exists or is offline."));
          }
        } else {
          player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " " + MessagesFile.NO_PERMISSION));
        }
      }
    }
    return true;
  }
}
