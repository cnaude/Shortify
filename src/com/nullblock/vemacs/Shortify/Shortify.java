package com.nullblock.vemacs.Shortify;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.nullblock.vemacs.Shortify.Updater.UpdateResult;

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
        if(this.getConfig().getString("shortener").equals("bitly") && this.getConfig().getString("bitlyUSER").equals("none") && this.getConfig().getString("bitlyAPI").equals("none")){
            getLogger().info(ChatColor.RED + "bit.ly is not properly configured, see config.yml for details.");
            getLogger().info(ChatColor.RED + "Reverting to default shortener is.gd.");
        }
        if(this.getConfig().getString("shortener").equals("googl") && this.getConfig().getString("googAPI").equals("none")){
            getLogger().info(ChatColor.RED + "goo.gl is not properly configured, see config.yml for details.");
            getLogger().info(ChatColor.RED + "Reverting to default shortener is.gd.");
        }
        if(this.getConfig().getBoolean("auto-update")){
        	getLogger().info("Checking for updates, please wait...");
        	Updater updater = new Updater(this, "Shortify", this.getFile(), Updater.UpdateType.DEFAULT, false);
        	if(updater.getResult() == UpdateResult.SUCCESS) {
        		getLogger().info("An update (version "+updater.getLatestVersionString()+") of Shortify was found and installed. Please reload your server to use the new version.");
        	}
        }
    }
    
    @Override
    public void onDisable() {        
        
    }
}
