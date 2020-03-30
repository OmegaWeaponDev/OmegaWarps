package me.omegaweapon.omegawarps.commands.warps;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.ou.library.Utilities;
import me.ou.library.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SetWarp extends PlayerCommand {
  String prefix = OmegaWarps.getMessagesFile().getConfig().getString("Prefix");
  
  public SetWarp() {
    super("setwarp");

    // Set the permission and permission message
    setPermission("omegawarps.setwarp");
    setPermissionMessage(Utilities.colourise(prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("No_Permission")));

    // Set the command description
    setDescription("Creates a warp for the given player");

    // Set the usage message
    setUsage("/setwarp <player name> <warp name>");

    // Set some aliases for the command
    setAliases(Arrays.asList("createwarp", "addwarp"));
  }

  @Override
  protected void onCommand(final Player player, final String[] strings) {

    if(strings.length == 0) {
      Utilities.message(player, prefix + " &b/setwarp <player name> <warp name> - Create a warp for the given player.");
      Utilities.message(player, prefix + " &b/setwarp <warp name> - Create a warp with no owner set.");
    }

    if(strings.length == 1) {
      // Get the warpname, player name and the location for the warp
      String warpCreator = player.getName();
      String warpName = strings[0].toLowerCase();
      Location warpLocation = player.getLocation();

      // Add all the warp details to the warps file then save the file
      OmegaWarps.getWarpsFile().getConfig().createSection(warpName);
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set By", warpCreator);
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.World", player.getWorld().getName());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.X", warpLocation.getX());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Y", warpLocation.getY());
      OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Z", warpLocation.getZ());
      OmegaWarps.getWarpsFile().saveConfig();

      Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("Setwarp_Message.Without_Owner").replace("%warpName%", warpName));
    }

    if(strings.length == 2) {
      String warpCreator = player.getName();
      String warpOwnerName = strings[0];
      Player warpOwner = Bukkit.getPlayer(warpOwnerName);
      String warpName = strings[1].toLowerCase();
      Location warpLocation = player.getLocation();
      Double warpCost = OmegaWarps.getConfigFile().getConfig().getDouble("Warp_Cost.Cost");
      Long setDateTime = System.currentTimeMillis();

      if(warpOwner != null) {
        if(OmegaWarps.getConfigFile().getConfig().getBoolean("Warp_Cost.Enabled") && !player.hasPermission("omegawarps.cost.bypass")) {
          double warpOwnerBalance = OmegaWarps.getEconomy().getBalance(warpOwner);

          if(warpOwnerBalance >= warpCost) {
            OmegaWarps.getWarpsFile().getConfig().createSection(warpName);
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set By", warpCreator);
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Set For", warpOwnerName);
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Time Set", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.World", player.getWorld().getName());
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.X", warpLocation.getX());
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Y", warpLocation.getY());
            OmegaWarps.getWarpsFile().getConfig().set(warpName + ".Warp Location.Z", warpLocation.getZ());
            OmegaWarps.getWarpsFile().saveConfig();

            OmegaWarps.getEconomy().withdrawPlayer(warpOwner, warpCost);
            Utilities.message(warpOwner, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("Warp_Cost_Taken").replace("%warpCost%", warpCost.toString()));
            Utilities.message(player, prefix + " " + OmegaWarps.getMessagesFile().getConfig().getString("Setwarp_Message.With_Owner").replace("%warpName%", warpName).replace("%warpOwner%", warpOwnerName));
          } else {
            Utilities.message(player, prefix + " &bThe player " + warpOwnerName + " does not have enough money to pay for the warp.");
          }
        }
      } else {
        Utilities.message(player, prefix + " &cSorry, that player does not exist or they are offline.");
      }
    }
  }
}
