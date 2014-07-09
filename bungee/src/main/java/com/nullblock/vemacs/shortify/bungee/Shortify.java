package com.nullblock.vemacs.shortify.bungee;

import com.nullblock.vemacs.shortify.util.CommonConfiguration;
import com.nullblock.vemacs.shortify.common.ShortenerManager;
import com.nullblock.vemacs.shortify.util.ShortifyUtility;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.logging.Level;

public class Shortify extends Plugin {
    private Listener listener;
    private static ShortenerManager shortenerManager;
    private static CommonConfiguration configuration;

    @Override
    public void onEnable() {
        // Load config.yml with snakeyaml
        configuration = ShortifyUtility.loadCfg(getFile());
        shortenerManager = ShortifyUtility.setupShorteners();
        ShortifyUtility.reloadConfigShorteners(shortenerManager, configuration);
        ShortifyUtility.verifyConfiguration(configuration, getLogger());
        if (configuration.getBoolean("metrics")) {
            try {
                Metrics metrics = new Metrics(this);
                Metrics.Graph g = metrics.createGraph("URL Shortener");
                g.addPlotter(new Metrics.Plotter(configuration.getString("shortener", "isgd")) {
                    @Override
                    public int getValue() {
                        return 1;
                    }
                });
                metrics.start();
                getLogger().info("Metrics setup.");
            } catch (IOException e) {
                getLogger().log(Level.WARNING, "Unable to set up Metrics", e);
            }
        }
        listener = new ShortifyListener(this);
        getProxy().getPluginManager().registerCommand(this, new ShortifyCommand());
        ShortifyUtility.dumpData(getFile(), configuration);
    }

    @Override
    public void onDisable() {
        listener = null;
        configuration = null;
        shortenerManager = null;
    }

    public static ShortenerManager getShortenerManager() {
        return shortenerManager;
    }

    public static CommonConfiguration getConfiguration() {
        return configuration;
    }

    private class ShortifyCommand extends Command {
        public ShortifyCommand() {
            super("shortify", "shortify.admin");
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
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
        }
    }
}
