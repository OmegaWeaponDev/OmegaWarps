package me.omegaweapon.omegawarps.settings;

public class ConfigFile extends FileManager {
  
  private ConfigFile(String fileName) {
    super(fileName);
    
    setHeader(new String[]{
      "-------------------------------------------------------------------------------------------",
      " \n",
      " Welcome to OmegaWarps main configuration file.",
      " \n",
      " Here you'll find of the settings and options that you can",
      " customize to your server needs. Most features are customizable",
      " to an extent.",
      " \n",
      " You can find all the particle effects here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html\n",
      "-------------------------------------------------------------------------------------------"
    });
  }
  
  public static Boolean UPDATE_NOTIFY, WARP_COST_ENABLED;
  public static Double WARP_COST;
  public static String BEFORE_WARP_PARTICLES, POST_WARP_PARTICLES;
  
  private void onLoad() {
    UPDATE_NOTIFY = getBoolean("Update_Notify");
    WARP_COST_ENABLED = getBoolean("Warp_Cost.Enabled");
    WARP_COST = getDouble("Warp_Cost.Cost");
    BEFORE_WARP_PARTICLES = getString("Warp_Particle_Effects.Before_Warp");
    POST_WARP_PARTICLES = getString("Warp_Particle_Effects.After_Warp");
  }
  
  public static void init() {
    new ConfigFile("config.yml").onLoad();
  }
}
