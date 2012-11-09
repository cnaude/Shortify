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
        config.options().copyDefaults(true);
        saveConfig();
    }
    @Override
    public void onDisable() {        
        
    }
}
