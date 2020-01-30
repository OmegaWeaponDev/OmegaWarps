package me.omegaweapon.omegawarps.events;

import me.omegaweapon.omegawarps.settings.ConfigFile;
import me.omegaweapon.omegawarps.settings.MessagesFile;
import me.omegaweapon.omegawarps.utils.ColourUtils;
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
        player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bOmegaWarps has been updated"));
        player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bPlease get the latest version"));
        player.sendMessage(ColourUtils.Colorize(MessagesFile.PREFIX + " &bfrom here: "));
      }
    }
  }
}
