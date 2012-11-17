package com.nullblock.vemacs.Shortify;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class Shortify extends JavaPlugin {
    @Override
    public void onEnable() {
        new ShortifyListener(this);
        final FileConfiguration config = this.getConfig();
        config.addDefault("shortener", "isgd");
        config.addDefault("googAPI", "none");
        config.addDefault("bitlyAPI", "none");
        config.addDefault("bitlyUSER", "none");
        config.addDefault("minlength", "20");
        config.addDefault("auto-update", "true");
        config.options().copyDefaults(true);
        saveConfig();
        if(this.getConfig().getString("auto-update").equals("true")){
        	Updater updater = new Updater(this, "Shortify", this.getFile(), Updater.UpdateType.DEFAULT, false);
        	getLogger().info("The latest reviewed version of Shortify is " + updater.getLatestVersionString());
        	getLogger().info("You are running version" + this.getDescription().getVersion());
        }
    }
    
    @Override
    public void onDisable() {        
        
    }
}
