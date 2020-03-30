package me.omegaweapon.omegawarps.events;

import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.UpdateChecker;
import me.ou.library.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;

public class PlayerListener implements Listener {
  
  @EventHandler
  public static void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
    Player player = playerJoinEvent.getPlayer();
    
    if(player.hasPermission("omegawarps.update") || player.isOp()) {
      
      if(OmegaWarps.getConfigFile().getConfig().getBoolean("Update_Notify")) {

        // Send the player a message on join if there is an update for the plugin
        if(Utilities.checkPermission(player, "omegawarps.update", true)) {
          new UpdateChecker(OmegaWarps.getInstance(), 74788).getVersion(version -> {
            if (!OmegaWarps.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
              PluginDescriptionFile pdf = OmegaWarps.getInstance().getDescription();
              Utilities.message(player,
                "&bA new version of &c" + pdf.getName() + " &bis avaliable!",
                "&bCurrent Version: &c" + pdf.getVersion() + " &b> New Version: &c" + version,
                "&bGrab it here:&c https://spigotmc.org/resources/73013"
              );
            }
          });
        }
      }
    }
  }
}
