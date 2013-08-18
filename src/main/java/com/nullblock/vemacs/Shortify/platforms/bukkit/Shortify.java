package com.nullblock.vemacs.Shortify.platforms.bukkit;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.nullblock.vemacs.Shortify.common.Globals;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public final class Shortify extends JavaPlugin {

	private Listener listener;
	
	@Override
	public void onEnable() {
		// Load config.yml with snakeyaml
		Globals.c = ShortifyUtility.loadCfg(getFile());
		if (Globals.c.getBoolean("update")) {
			new Updater(this, "slug", this.getFile(), Updater.UpdateType.DEFAULT, false);
		}
		Globals.sm = ShortifyUtility.setupShorteners();
		Globals.sm = ShortifyUtility.reloadConfigShorteners(Globals.sm, Globals.c);
		ShortifyUtility.verifyConfiguration(Globals.c, getLogger());
		if (Globals.c.getBoolean("metrics")) {
			try {
				ShortifyUtility.setupMetrics(new Metrics(this), Globals.c);
				getLogger().info("Metrics setup.");
			} catch (IOException e) {
				getLogger().warning("Unable to set up Metrics.");
			}
		}
		try {
			Class.forName("org.bukkit.event.player.AsyncPlayerChatEvent");
			listener = new ShortifyListener(this);
			getLogger().info(
					"Detected CB 1.3.1 or above, using async chat event...");
		} catch (ClassNotFoundException e) {
			listener = new ShortifyLegacyListener(this);
			getLogger()
					.info("Detected early CB 1.3 beta or below, using regular chat event...");
		}
		getServer().getPluginManager().registerEvents(listener, this);
		getServer().getPluginManager().registerEvents(new ShortifyCommandListener(this), this);
		ShortifyUtility.dumpData(getFile(), Globals.c);
	}

	@Override
	public void onDisable() {
		listener = null;
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
				Globals.c = ShortifyUtility.loadCfg(getFile());
				ShortifyUtility.verifyConfiguration(Globals.c, getLogger());
				Globals.sm = ShortifyUtility.reloadConfigShorteners(Globals.sm, Globals.c);
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
}
