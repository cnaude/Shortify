package com.nullblock.vemacs.Shortify.platforms.bukkit;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.PluginCommon;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
import com.nullblock.vemacs.Shortify.util.Updater;
import com.nullblock.vemacs.Shortify.util.Updater.UpdateResult;

public final class Shortify extends JavaPlugin {

	private Listener listener;
	private CommonConfiguration c;

	@Override
	public void onEnable() {
		// Load config.yml with snakeyaml
		c = PluginCommon.loadCfg(getFile());
		PluginCommon.verifyConfiguration(c, getLogger());
		try {
			ShortifyUtility.setupMetrics(new Metrics(this), c);
			getLogger().info("Metrics setup.");
		} catch (IOException e) {
			getLogger().warning("Unable to set up Metrics.");
		}
		listener = new ShortifyListener(this);
		getServer().getPluginManager().registerEvents(listener, this);
		if (c.getString("auto-update").equals("true")) {
			getLogger().info("Checking for updates, please wait...");
			Updater updater = new Updater(getLogger(), "Shortify", 
					this.getDescription().getVersion(), this.getFile(),
					Updater.UpdateType.DEFAULT, false);
			if (updater.getResult() == UpdateResult.SUCCESS) {
				getLogger()
						.info(ChatColor.GREEN
								+ "An update (version "
								+ updater.getLatestVersionString()
								+ ") of Shortify was found and installed. Please restart your server to use the new version.");
			}
			if (updater.getResult() == UpdateResult.NO_UPDATE) {
				getLogger().info("No updates found.");
			}
			updater = null;
		}
		PluginCommon.dumpData(getFile(), c);
	}

	@Override
	public void onDisable() {
		listener = null;
		c = null;
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
				c = PluginCommon.loadCfg(getFile());
				PluginCommon.verifyConfiguration(c, getLogger());
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

	protected CommonConfiguration getCfg() {
		return c;
	}
}
