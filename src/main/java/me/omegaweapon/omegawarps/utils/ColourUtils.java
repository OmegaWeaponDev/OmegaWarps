package me.omegaweapon.omegawarps.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColourUtils {
  
  public static String Colorize(String str) {
    ChatColor.translateAlternateColorCodes('&', str);
    return str;
  }
  
  public static List<String> Colourize(List<String> strs) {
  
    List<String> strings = new ArrayList<>();
    
    for (String string : strs) {
      strings.add(ChatColor.translateAlternateColorCodes('&', string));
    }
    return strings;
  }
}
