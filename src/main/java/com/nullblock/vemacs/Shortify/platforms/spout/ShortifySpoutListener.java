package com.nullblock.vemacs.Shortify.platforms.spout;

import org.spout.api.Engine;
import org.spout.api.Spout;
import org.spout.api.chat.ChatArguments;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerChatEvent;

import com.nullblock.vemacs.Shortify.common.GenericShortifyListener;
import com.nullblock.vemacs.Shortify.common.ShortifyException;

public class ShortifySpoutListener extends GenericShortifyListener implements
		Listener {
	ShortifySpoutPlugin plugin;

	public ShortifySpoutListener(ShortifySpoutPlugin p) {
		plugin = p;
	}

	@EventHandler(order = Order.LATEST)
	public void playerChat(PlayerChatEvent event) {
		// Spout is an interesting case.
		if (event.getPlayer().hasPermission("shortify.shorten")) {
			String msg = event.getMessage().getPlainString();
			String minlength = plugin.getConfig().getString("minlength");
			try {
				if (plugin.getConfig().getString("mode", "replace")
						.equals("replace")) {
					event.setMessage(new ChatArguments(shortenAll(msg,
							Integer.valueOf(minlength),
							getShortener(plugin.getConfig()))));
				} else if (plugin.getConfig().getString("mode", "replace")
						.equals("classic")) {
					Engine.STANDARD_BROADCAST_CHANNEL
							.broadcastToReceivers(new ChatArguments(
									classicUrlShorten(msg,
											Integer.valueOf(minlength),
											getShortener(plugin.getConfig()))));
				}
			} catch (ShortifyException e) {
				Spout.getLogger().severe("Shortify warning: " + e.getMessage());
			} catch (NumberFormatException e) {
				Spout.getLogger()
						.severe("Shortify warning: Your configuration file is not correct, the minlength parameter is invalid.");
			}
		}
	}
}
