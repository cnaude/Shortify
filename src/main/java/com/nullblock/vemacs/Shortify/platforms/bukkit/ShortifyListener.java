package com.nullblock.vemacs.Shortify.platforms.bukkit;

/**
 * Listener for Shortify
 * By vemacs, some parts contributed by minecrafter
 * 
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortifyListener implements Listener {

	private Shortify plugin;

	public ShortifyListener(Shortify Shortify) {
		plugin = Shortify;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(AsyncPlayerChatEvent e) {
		if (e.getPlayer().hasPermission("shortify.shorten")) {
			try {
				if (plugin.getCfg().getString("mode", "replace")
						.equals("replace")) {
					e.setMessage(ShortifyUtility.shortenAll(
							e.getMessage(),
							Integer.valueOf(plugin.getCfg().getString(
									"minlength")), ShortifyUtility.getShortener(plugin.getCfg())));
				} else if (plugin.getCfg().getString("mode", "replace")
						.equals("classic")) {
					new ShortifyClassicThread(plugin.getCfg(), plugin.getServer(), e.getMessage()).run();
				}
			} catch (NumberFormatException e1) {
				Bukkit.getConsoleSender()
						.sendMessage(
								ChatColor.RED
										+ "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
			} catch (ShortifyException e1) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "Warning: " + e1.getMessage());
			}
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void playerCommand(PlayerCommandPreprocessEvent e) {
				if (e.getPlayer().hasPermission("shortify.shorten")) {
			try {
				if (plugin.getCfg().getString("mode", "replace")
						.equals("replace")) {
					e.setMessage(ShortifyUtility.shortenAll(
							e.getMessage(),
							Integer.valueOf(plugin.getCfg().getString(
									"minlength")), ShortifyUtility.getShortener(plugin.getCfg())));
				} else if (plugin.getCfg().getString("mode", "replace")
						.equals("classic")) {
					new ShortifyClassicThread(plugin.getCfg(), plugin.getServer(), e.getMessage()).run();
				}
			} catch (NumberFormatException e1) {
				Bukkit.getConsoleSender()
						.sendMessage(
								ChatColor.RED
										+ "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
			} catch (ShortifyException e1) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "Warning: " + e1.getMessage());
			}
		}
	}
}
