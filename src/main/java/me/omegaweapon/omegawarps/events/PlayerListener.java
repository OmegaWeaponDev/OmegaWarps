package me.omegaweapon.omegawarps.events;

import me.omegaweapon.omegawarps.OmegaUpdater;
import me.omegaweapon.omegawarps.OmegaWarps;
import me.omegaweapon.omegawarps.settings.ConfigFile;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
  
  @EventHandler
  public static void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
    Player player = playerJoinEvent.getPlayer();
    
    if(player.hasPermission("omegawarps.update") || player.isOp()) {
      
      if(ConfigFile.UPDATE_NOTIFY.equals(true)) {
        // Update Checker
        new OmegaUpdater(74788) {

          @Override
          public void onUpdateAvailable() {
            player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + "&b A new update has been released!"));
            player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + "&b Your current version is: &c" + OmegaWarps.getInstance().getDescription().getVersion()));
            player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + "&b The latest version is: &c" + OmegaUpdater.getLatestVersion()));
            player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + "&b You can update here:"));
            player.sendMessage(Utilities.Colourize(MessagesFile.PREFIX + "&c https://www.spigotmc.org/resources/omegadeath." + OmegaUpdater.getProjectId()));
          }
        }.runTaskAsynchronously(OmegaWarps.getInstance());
      }
    }
  }
}
