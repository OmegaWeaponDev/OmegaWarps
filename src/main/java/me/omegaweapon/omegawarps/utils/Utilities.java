package me.omegaweapon.omegawarps.utils;

import me.omegaweapon.omegawarps.OmegaWarps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;

public class Utilities {
  
  public static void registerCommand(final Command command) {
    try{
      final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      commandMapField.setAccessible(true);
      
      final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
      commandMap.register(command.getLabel(), command);
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static void registerCommands(final Command... commands) {
    for (final Command command : commands) {
      registerCommand(command);
    }
  }
  
  public static void registerEvent(final Listener listener) {
    Bukkit.getServer().getPluginManager().registerEvents(listener, OmegaWarps.getInstance());
  }
  
  public static void registerEvents(final Listener... listeners) {
    final PluginManager manager = Bukkit.getServer().getPluginManager();
    for (final Listener listener : listeners) {
      manager.registerEvents(listener, OmegaWarps.getInstance());
    }
  }
  
  public static String Colourize(String str) {
    return ChatColor.translateAlternateColorCodes('&', str);
  }
}
