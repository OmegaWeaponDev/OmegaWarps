package me.omegaweapon.omegawarps.settings;

public class MessagesFile extends FileManager {
  
  private MessagesFile(String fileName) {
    super(fileName);
    
    setHeader(new String[] {
      " -------------------------------------------------------------------------------------------\n",
      " \n",
      " Welcome to OmegaWarps messages file.\n",
      " \n",
      " Here you'll find all of the messages that you can\n",
      " customize to your server needs.\n",
      " \n",
      " Placeholders:",
      "  %warpName% - The name of the warp.",
      "  %warpOwner% -The name of the player the warp was created for.",
      " \n",
      " -------------------------------------------------------------------------------------------"
    });
  }
  
  public static String PREFIX, NO_PERMISSION, SETWARP_MESSAGE, WARP_MESSAGE;
  
  private void onLoad() {
    PREFIX = getString("Prefix");
    NO_PERMISSION = getString("No_Permission");
    SETWARP_MESSAGE = getString("Setwarp_Message");
    WARP_MESSAGE = getString("Warp_Message");
  }
  
  public static void init() {
    new MessagesFile("messages.yml").onLoad();
  }
}
