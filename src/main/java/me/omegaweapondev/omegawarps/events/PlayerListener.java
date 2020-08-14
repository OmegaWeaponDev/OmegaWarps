package me.omegaweapondev.omegawarps.events;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.ou.library.SpigotUpdater;
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

    if(!OmegaWarps.getInstance().getConfigFile().getConfig().getBoolean("Update_Notify")) {
      return;
    }

    if(!Utilities.checkPermissions(player, true, "omegawarps.update", "omegawarps.*")) {
      return;
    }

    // Send the player a message on join if there is an update for the plugin
    new SpigotUpdater(OmegaWarps.getInstance(), 74788).getVersion(version -> {
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
