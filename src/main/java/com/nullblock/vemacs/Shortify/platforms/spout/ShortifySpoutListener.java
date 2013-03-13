package com.nullblock.vemacs.Shortify.platforms.spout;

import org.spout.api.chat.ChatArguments;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerChatEvent;

import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortifySpoutListener implements Listener {
	ShortifySpoutPlugin plugin;

	public ShortifySpoutListener(ShortifySpoutPlugin p) {
		plugin = p;
	}

	@EventHandler(order = Order.LATEST)
	public void playerChat(PlayerChatEvent event) {
		/* Spout is an interesting case. */
		if (event.getPlayer().hasPermission("shortify.shorten")) {
			String msg = event.getMessage().getPlainString();
			String minlength = plugin.getConfig().getString("minlength");
			try {
				if (plugin.getConfig().getString("mode", "replace")
						.equals("replace")) {
					event.setMessage(new ChatArguments(ShortifyUtility.shortenAll(msg,
							Integer.valueOf(minlength),
							ShortifyUtility.getShortener(plugin.getConfig()), "")));//plugin.getConfig().getString("prefix"))));
				} else if (plugin.getConfig().getString("mode", "replace")
						.equals("classic")) {
					event.getPlayer().getActiveChannel().broadcastToReceivers(new ChatArguments(
									ShortifyUtility.classicUrlShorten(msg,
											Integer.valueOf(minlength),
											ShortifyUtility.getShortener(plugin.getConfig()))));
				}
			} catch (ShortifyException e) {
				plugin.getLog().severe("Shortify warning: " + e.getMessage());
			} catch (NumberFormatException e) {
				plugin.getLog()
						.severe("Shortify warning: Your configuration file is not correct, the minlength parameter is invalid.");
			}
		}
	}
}
