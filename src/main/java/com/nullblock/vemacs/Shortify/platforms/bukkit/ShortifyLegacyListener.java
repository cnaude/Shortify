package com.nullblock.vemacs.Shortify.platforms.bukkit;

/**
 * Listener for Shortify, legacy Bukkit
 * By vemacs, some parts contributed by minecrafter
 * 
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

@SuppressWarnings("deprecation")
public class ShortifyLegacyListener implements Listener {

	private Shortify plugin;

	public ShortifyLegacyListener(Shortify Shortify) {
		plugin = Shortify;
	}

	/* This class is only used when we are working with 1.1, 1.2 or very early 1.3. */
	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(PlayerChatEvent e) {
		Shortener shortener = ShortifyUtility.getShortener(plugin.getCfg());
		if (e.getPlayer().hasPermission("shortify.shorten")) {
			try {
				if (plugin.getCfg().getString("mode", "replace")
						.equals("replace")) {
					e.setMessage(ShortifyUtility.shortenAll(
							e.getMessage(),
							Integer.valueOf(plugin.getCfg().getString(
									"minlength")), shortener, ""));
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
