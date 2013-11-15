package com.nullblock.vemacs.Shortify.bukkit;

/**
 * Listener for Shortify, legacy Bukkit
 * By vemacs, some parts contributed by minecrafter
 * 
 */

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.nullblock.vemacs.Shortify.common.Globals;
import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

@SuppressWarnings("deprecation")
public class ShortifyLegacyListener implements Listener {

	private Shortify plugin;

	public ShortifyLegacyListener(Shortify Shortify) {
		plugin = Shortify;
	}

	/*
	 * This class is only used when we are working with 1.1, 1.2 or very early
	 * 1.3.
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(final PlayerChatEvent e) {
		final Shortener shortener = ShortifyUtility.getShortener(Globals.c);
		if (e.getPlayer().hasPermission("shortify.shorten")) {
			try {
				if (Globals.c.getString("mode", "replace")
						.equals("replace")) {
					e.setMessage(ShortifyUtility.shortenAll(
							e.getMessage(),
							Integer.valueOf(Globals.c.getString(
									"minlength")), shortener, ""));
				} else if (Globals.c.getString("mode", "replace")
						.equals("classic")) {
					plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
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
}
