package me.omegaweapondev.omegawarps.events;

import me.omegaweapondev.omegawarps.OmegaWarps;
import me.omegaweapondev.omegawarps.commands.warps.Warp;
import me.omegaweapondev.omegawarps.utils.MessageHandler;
import me.ou.library.SpigotUpdater;
import me.ou.library.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitTask;

public class PlayerListener implements Listener {
  private final OmegaWarps plugin;
  private final Warp warpCommand;
  private final MessageHandler messageHandler;

  public PlayerListener(final OmegaWarps plugin) {
    this.plugin = plugin;

    messageHandler = new MessageHandler(plugin, plugin.getSettingsHandler().getMessagesFile().getConfig());
    warpCommand = new Warp(plugin);
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
    Player player = playerJoinEvent.getPlayer();

    if(!plugin.getSettingsHandler().getConfigFile().getConfig().getBoolean("Update_Notify")) {
      return;
    }

    if(!Utilities.checkPermissions(player, true, "omegawarps.update", "omegawarps.admin")) {
      return;
    }

    // Send the player a message on join if there is an update for the plugin
    // The Updater
    new SpigotUpdater(plugin, 74788).getVersion(version -> {
      int spigotVersion = Integer.parseInt(version.replace(".", ""));
      int pluginVersion = Integer.parseInt(plugin.getDescription().getVersion().replace(".", ""));

      if(pluginVersion >= spigotVersion) {
        return;
      }

      PluginDescriptionFile pdf = plugin.getDescription();
      Utilities.message(player,
        "#14abc9A new version of #ff4a4a" + pdf.getName() + " #14abc9is avaliable!",
        "#14abc9Current Version: #ff4a4a" + pdf.getVersion() + " #14abc9> New Version: #ff4a4a" + version,
        "#14abc9Grab it here: #ff4a4ahttps://www.spigotmc.org/resources/omegawarps.74788/"
      );
    });
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
    Player player = playerQuitEvent.getPlayer();

    if(warpCommand.getPlayerWarpMap().containsKey(player.getUniqueId())) {
      BukkitTask warpTask = warpCommand.getPlayerWarpMap().get(player.getUniqueId());
      warpTask.cancel();
      warpCommand.getPlayerWarpMap().remove(player.getUniqueId());
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
    Player player = playerMoveEvent.getPlayer();

    if(warpCommand.getPlayerWarpMap().containsKey(player.getUniqueId())) {
      BukkitTask warpTask = warpCommand.getPlayerWarpMap().get(player.getUniqueId());
      warpTask.cancel();
      warpCommand.getPlayerWarpMap().remove(player.getUniqueId());
      Utilities.message(player, messageHandler.string("Warp_Delay_Interrupted", "#ffa4aYou have cancelled the warp!"));
    }
  }
}
