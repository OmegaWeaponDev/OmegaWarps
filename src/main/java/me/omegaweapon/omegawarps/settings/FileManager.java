package me.omegaweapon.omegawarps.settings;

import me.omegaweapon.omegawarps.OmegaWarps;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FileManager extends YamlConfiguration {
  
  private final File file;
  private final YamlConfiguration defaults;
  private String[] editHeader;
  private String pathPrefix;
  
  public FileManager(String fileName) {
    this(fileName, true);
  }
  
  public FileManager(String fileName, boolean useDefaults) {
    
    if (useDefaults) {
      this.defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(FileManager.class.getResourceAsStream("/" + fileName), StandardCharsets.UTF_8));
      Objects.requireNonNull(defaults, "Could not get the default " + fileName + " inside of your plugin, make sure you created the file and that you did not replace the jar on a running server!");
      
    } else
      this.defaults = null;
    
    this.file = extract(fileName);
    
    loadConfig();
  }
  
  public void setHeader(String[] editHeader) {
    this.editHeader = editHeader;
  }
  
  
  public String[] getEditHeader() {
    return editHeader;
  }
  
  public void setPathPrefix(String pathPrefix) {
    this.pathPrefix = pathPrefix;
  }
  
  public void reloadConfig() {
    saveConfig();
    
    loadConfig();
  }
  
  public void write(String path, Object value) {
    set(path, value);
    
    reloadConfig();
  }
  
  public void saveConfig() {
    try {
      
      if (editHeader != null) {
        options().header(StringUtils.join(editHeader, System.lineSeparator()));
        options().copyHeader(true);
      }
      
      super.save(file);
      
    } catch (final IOException ex) {
      System.out.println("Failed to save configuration from " + file);
      
      ex.printStackTrace();
    }
  }
  
  public void deleteConfig() {
    try {
      file.delete();
      
    } catch (final Throwable t) {
      System.out.println("Failed to remove configuration file " + file);
      
      t.printStackTrace();
    }
  }
  
  private void loadConfig() {
    try {
      
      super.load(file);
      
    } catch (final Throwable t) {
      System.out.println("Failed to load configuration from " + file);
      
      t.printStackTrace();
    }
  }
  
  @Override
  public Object get(String path, Object def) {
    if (defaults != null) {
      
      if (def != null && !def.getClass().isPrimitive() && !PrimitiveWrapper.isWrapperType(def.getClass()))
        throw new IllegalArgumentException("The default value must be null since we use defaults from file inside of the plugin! Path: " + path + ", default called: " + def);
      
      if (super.get(path, null) == null) {
        final Object defaultValue = defaults.get(path);
        Objects.requireNonNull(defaultValue, "Default " + file.getName() + " in your .jar lacks a key at '" + path + "' path");
        
        Bukkit.getLogger().info("&fUpdating &a" + file.getName() + "&f. Set '&b" + path + "&f' to '" + defaultValue + "'");
        write(path, defaultValue);
      }
    }
    
    final String m = new Throwable().getStackTrace()[1].getMethodName();
    
    if (defaults == null && pathPrefix != null && !m.equals("getConfigurationSection") && !m.equals("get"))
      path = pathPrefix + "." + path;
    
    return super.get(path, null);
  }
  
  @Override
  public void set(String path, Object value) {
    
    final String m = new Throwable().getStackTrace()[1].getMethodName();
    
    if (defaults == null && pathPrefix != null && !m.equals("getConfigurationSection") && !m.equals("get"))
      path = pathPrefix + "." + path;
    
    super.set(path, value);
  }
  
  private File extract(String path) {
    final JavaPlugin i = OmegaWarps.getInstance();
    Objects.requireNonNull(i, "Please set your plugin's instance, place 'instance = this;' on the first line in onEnable in your main plugins class");
    
    final File file = new File(i.getDataFolder(), path);
    
    if (file.exists())
      return file;
    
    createFileAndDirectory(path);
    
    if (defaults != null)
      try (InputStream is = i.getResource(path)) {
        Objects.requireNonNull(is, "Inbuilt file not found: " + path);
        
        Files.copy(is, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
        
      } catch (final IOException e) {
        e.printStackTrace();
      }
    
    return file;
  }
  
  private File createFileAndDirectory(String path) {
    
    final File datafolder = OmegaWarps.getInstance().getDataFolder();
    final int lastIndex = path.lastIndexOf('/');
    final File directory = new File(datafolder, path.substring(0, lastIndex >= 0 ? lastIndex : 0));
    
    directory.mkdirs();
    
    final File destination = new File(datafolder, path);
    
    try {
      destination.createNewFile();
      
    } catch (final IOException ex) {
      System.out.println("Failed to create file " + path);
      
      ex.printStackTrace();
    }
    
    return destination;
  }
  
  private static final class PrimitiveWrapper {
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    
    private static boolean isWrapperType(Class<?> clazz) {
      return WRAPPER_TYPES.contains(clazz);
    }
    
    private static Set<Class<?>> getWrapperTypes() {
      final Set<Class<?>> ret = new HashSet<>();
      ret.add(Boolean.class);
      ret.add(Character.class);
      ret.add(Byte.class);
      ret.add(Short.class);
      ret.add(Integer.class);
      ret.add(Long.class);
      ret.add(Float.class);
      ret.add(Double.class);
      ret.add(Void.class);
      return ret;
    }
  }
}
