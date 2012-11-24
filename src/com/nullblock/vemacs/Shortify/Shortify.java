package com.nullblock.vemacs.Shortify;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.nullblock.vemacs.Shortify.Updater.UpdateResult;

public final class Shortify extends JavaPlugin {

	private ShortifyListener listener;
	private ShortifyClassicListener listener1;

	private void simpleReload() {
		if (this.getConfig().getString("shortener").equals("bitly")
				&& (this.getConfig().getString("bitlyUSER").equals("none") || this
						.getConfig().getString("bitlyAPI").equals("none"))) {
			getLogger()
					.info(ChatColor.RED
							+ "bit.ly is not properly configured, see config.yml for details.");
			getLogger().info(
					ChatColor.RED + "Reverting to default shortener is.gd.");
			this.getConfig().set("shortener", "isgd");
		}
		if (this.getConfig().getString("shortener").equals("yourls")
				&& (this.getConfig().getString("yourlsUSER").equals("none")
						|| this.getConfig().getString("yourlsURI")
								.equals("none") || this.getConfig()
						.getString("yourlsPASS").equals("none"))) {
			getLogger()
					.info(ChatColor.RED
							+ "YOURLS is not properly configured, see config.yml for details.");
			getLogger().info(
					ChatColor.RED + "Reverting to default shortener is.gd.");
			this.getConfig().set("shortener", "isgd");
		}
		if (this.getConfig().getString("shortener").equals("googl")
				&& this.getConfig().getString("googAPI").equals("none")) {
			getLogger()
					.info(ChatColor.RED
							+ "goo.gl is not properly configured, see config.yml for details.");
			getLogger().info(
					ChatColor.RED + "Reverting to default shortener is.gd.");
			this.getConfig().set("shortener", "isgd");
		}
	}

	@Override
	public void onEnable() {
		if (this.getConfig().getString("mode").equals("replace")) {
			listener = new ShortifyListener(this);
			getServer().getPluginManager().registerEvents(listener, this);
		}
		if (this.getConfig().getString("mode").equals("classic")) {
			listener1 = new ShortifyClassicListener(this);
			getServer().getPluginManager().registerEvents(listener1, this);
		}
		simpleReload();
		if (this.getConfig().getBoolean("auto-update")) {
			getLogger().info("Checking for updates, please wait...");
			Updater updater = new Updater(this, "Shortify", this.getFile(),
					Updater.UpdateType.DEFAULT, false);
			if (updater.getResult() == UpdateResult.SUCCESS) {
				getLogger()
						.info("An update (version "
								+ updater.getLatestVersionString()
								+ ") of Shortify was found and installed. Please restart your server to use the new version.");
			}
			updater = null;
		}
		this.saveConfig();
	}

	@Override
	public void onDisable() {
		listener = null;
		listener1 = null;
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
				reloadConfig();
				simpleReload();
				listener.reinitializeShortener();
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
