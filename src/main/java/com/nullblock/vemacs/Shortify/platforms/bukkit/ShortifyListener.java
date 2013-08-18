package com.nullblock.vemacs.Shortify.platforms.bukkit;

/**
 * Listener for Shortify
 * By vemacs, some parts contributed by minecrafter
 * 
 */

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.nullblock.vemacs.Shortify.common.Globals;
import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortifyListener implements Listener {

	private Shortify plugin;

	public ShortifyListener(Shortify Shortify) {
		plugin = Shortify;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(final AsyncPlayerChatEvent e) {
		final Shortener shortener = ShortifyUtility.getShortener(Globals.c);
		if (e.getPlayer().hasPermission("shortify.shorten")) {
			try {
				if (Globals.c.getString("mode", "replace")
						.equals("replace")) {
					e.setMessage(ShortifyUtility.shortenAll(
							e.getMessage(),
							Integer.valueOf(Globals.c.getString(
									"minlength", "20")),
									shortener,
							Globals.c.getString("prefix")));
				} else if (Globals.c.getString("mode", "replace")
						.equals("classic")) {
					plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
						@Override
						public void run() {
							try {
								final String newm = ShortifyUtility.classicUrlShorten(
									e.getMessage(), Integer.valueOf(Globals.c.getString("minlength", "20")),
									shortener);
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

									@Override
									public void run() {
										plugin.getServer().broadcastMessage(newm);
									}
									
								});
							} catch (NumberFormatException e) {
								plugin.getServer().getConsoleSender()
										.sendMessage(
												ChatColor.RED
														+ "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
							} catch (ShortifyException e) {
								plugin.getServer().getConsoleSender().sendMessage(
										ChatColor.RED + "Warning: " + e.getMessage());
							}
						}
					});
				}
			} catch (NumberFormatException e1) {
				plugin.getServer().getConsoleSender()
						.sendMessage(
								ChatColor.RED
										+ "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
			} catch (ShortifyException e1) {
				plugin.getServer().getConsoleSender().sendMessage(
						ChatColor.RED + "Warning: " + e1.getMessage());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		if (e.getPlayer().hasPermission("shortify.shorten.cmd")) {
			try {
				e.setMessage(ShortifyUtility.shortenAll(
						e.getMessage(),
						Integer.valueOf(Globals.c.getString("minlength", "20")),
						ShortifyUtility.getShortener(Globals.c), ""));

			} catch (NumberFormatException e1) {
				plugin.getServer().getConsoleSender()
						.sendMessage(
								ChatColor.RED
										+ "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
			} catch (ShortifyException e1) {
				plugin.getServer().getConsoleSender().sendMessage(
						ChatColor.RED + "Warning: " + e1.getMessage());
			}
		}
	}
}
