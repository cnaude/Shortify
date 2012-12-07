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

import com.nullblock.vemacs.Shortify.GenericShortifyListener;
import com.nullblock.vemacs.Shortify.Shortener;
import com.nullblock.vemacs.Shortify.ShortifyException;

public class ShortifyListener extends GenericShortifyListener implements Listener {

	private Shortify plugin;

	public ShortifyListener(Shortify Shortify) {
		plugin = Shortify;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(AsyncPlayerChatEvent e) {
		Shortener shortener = BukkitShared.getShortener(plugin);
		if (e.getPlayer().hasPermission("shortify.shorten")) {
			String minlength = plugin.getConfig().getString("minlength");
			try {
				e.setMessage(shortenAll(e.getMessage(), Integer.valueOf(minlength), shortener));
			} catch (NumberFormatException e1) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
			} catch (ShortifyException e1) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "Warning: " + e1.getMessage());
			}
		}
	}
}
