package me.omegaweapon.omegawarps.settings;

public class ConfigFile extends FileManager {
  
  private ConfigFile(String fileName) {
    super(fileName);
    
    setHeader(new String[]{
      "-------------------------------------------------------------------------------------------\n",
      " \n",
      " Welcome to OmegaWarps main configuration file.\n",
      " \n",
      " Here you'll find of the settings and options that you can\n",
      " customize to your server needs. Most features are customizable\n",
      " to an extent.\n",
      " \n",
      "-------------------------------------------------------------------------------------------"
    });
  }
  
  public static Boolean UPDATE_NOTIFY, WARP_COST_ENABLED;
  public static Double WARP_COST;
  
  private void onLoad() {
    UPDATE_NOTIFY = getBoolean("Update_Notify");
    WARP_COST_ENABLED = getBoolean("Warp_Cost.Enabled");
    WARP_COST = getDouble("Warp_Cost.Cost");
  }
  
  public static void init() {
    new ConfigFile("config.yml").onLoad();
  }
}
