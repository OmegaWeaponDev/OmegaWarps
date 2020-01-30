package me.omegaweapon.omegawarps;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public abstract class OmegaUpdater extends BukkitRunnable {
  
  private static int projectId;
  private static String latestVersion = "";
  
  public OmegaUpdater(int projectId) {
    OmegaUpdater.projectId = projectId;
  }
  
  @Override
  public void run() {
    try {
      final URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectId);
      final URLConnection con = url.openConnection();
      
      try (BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
        latestVersion = r.readLine();
      }
      
      if (isUpdateAvailable())
        onUpdateAvailable();
      
    } catch (final IOException ex) {
      ex.printStackTrace();
    }
  }
  
  public abstract void onUpdateAvailable();
  
  public static boolean isUpdateAvailable() {
    return !latestVersion.equals(OmegaWarps.getInstance().getDescription().getVersion());
  }
  
  public static String[] getUpdateMessage() {
    final PluginDescriptionFile pdf = OmegaWarps.getInstance().getDescription();
    
    return new String[] {
      "A new version of " + pdf.getName() + " is available!",
      "Current: " + pdf.getVersion() + ", new: " + getLatestVersion(),
      "Grab it here: https://spigotmc.org/resources/" + getProjectId()
    };
  }
  
  public static int getProjectId() {
    return projectId;
  }
  
  public static String getLatestVersion() {
    return latestVersion;
  }
}
