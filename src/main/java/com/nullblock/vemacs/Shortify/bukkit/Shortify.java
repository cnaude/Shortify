package com.nullblock.vemacs.Shortify.bukkit;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.ShortenerManager;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

public final class Shortify extends JavaPlugin {

    private Listener listener;
    private static ShortenerManager shortenerManager;
    private static CommonConfiguration configuration;

    @Override
    public void onEnable() {
        // Load config.yml with snakeyaml
        configuration = ShortifyUtility.loadCfg(getFile());
        if (configuration.getBoolean("update")) {
            new Updater(this, 46984, this.getFile(), Updater.UpdateType.DEFAULT, false);
        }
        shortenerManager = ShortifyUtility.setupShorteners();
        ShortifyUtility.reloadConfigShorteners(shortenerManager, configuration);
        ShortifyUtility.verifyConfiguration(configuration, getLogger());
        if (configuration.getBoolean("metrics")) {
            try {
                ShortifyUtility.setupMetrics(new Metrics(this), configuration);
                getLogger().info("Metrics setup.");
            } catch (IOException e) {
                getLogger().warning("Unable to set up Metrics.");
            }
        }
        listener = new ShortifyListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getPluginManager().registerEvents(new ShortifyCommandListener(this), this);
        ShortifyUtility.dumpData(getFile(), configuration);
    }

    @Override
    public void onDisable() {
        listener = null;
        configuration = null;
        shortenerManager = null;
    }

    public boolean onCommand(CommandSender sender, Command command,
                             String commandLabel, String[] args) {
        // Check for permissions
        if (!sender.hasPermission("shortify.admin")) {
            sender.sendMessage(ChatColor.RED
                    + "You do not have permission to administer Shortify.");
            return true;
        }
        // Handle the command
        // This is currently only /shortify reload
        if (args.length > 0) {
            if (args[0].equals("reload")) {
                configuration = ShortifyUtility.loadCfg(getFile());
                ShortifyUtility.verifyConfiguration(configuration, getLogger());
                ShortifyUtility.reloadConfigShorteners(shortenerManager, configuration);
                sender.sendMessage(ChatColor.GREEN
                        + "Shortify has been reloaded.");
            } else {
                sender.sendMessage("/shortify reload");
            }
        } else {
            sender.sendMessage("/shortify reload");
        }
        return true;
    }

    public static ShortenerManager getShortenerManager() {
        return shortenerManager;
    }

    public static CommonConfiguration getConfiguration() {
        return configuration;
    }
}
