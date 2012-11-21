package com.nullblock.vemacs.Shortify;

/**
 * Listener for Shortify
 * By vemacs, some parts contributed by minecrafter
 * 
 */

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ShortifyListener implements Listener {

	private Shortify plugin;
	private Shortener shortener;

	public ShortifyListener(Shortify Shortify) {
		plugin = Shortify;
		reinitializeShortener();
	}

	protected void reinitializeShortener() {
		String service = plugin.getConfig().getString("shortener");
		if (service.equals("googl")) {
			if (plugin.getConfig().getString("googAPI").equals("none")) {
				shortener = new ShortenerIsGd();
			} else {
				shortener = new ShortenerGooGl(plugin.getConfig().getString(
						"googAPI"));
			}
		}
		if (service.equals("bitly")) {
			if (plugin.getConfig().getString("bitlyUSER").equals("none")
					&& plugin.getConfig().getString("bitlyAPI").equals("none")) {
				shortener = new ShortenerIsGd();
			} else {
				shortener = new ShortenerBitLy(plugin.getConfig().getString(
						"bitlyUSER"), plugin.getConfig().getString("bitlyAPI"));
			}
		}
		if (service.equals("yourls")) {
			if (plugin.getConfig().getString("yourlsURI").equals("none")
					&& plugin.getConfig().getString("yourlsUSER")
							.equals("none")
					&& plugin.getConfig().getString("yourlsPASS")
							.equals("none")) {
				shortener = new ShortenerIsGd();
			} else {
				shortener = new ShortenerYourls(plugin.getConfig().getString(
						"yourlsURI"), plugin.getConfig().getString("yourlsUSER"),
						plugin.getConfig().getString("yourlsPASS"));
			}
		}
		if (service.equals("tinyurl")) {
			shortener = new ShortenerTinyUrl();
		}
		if (service.equals("turlca")) {
			shortener = new ShortenerTurlCa();
		} else {
			shortener = new ShortenerIsGd();
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(AsyncPlayerChatEvent e) {
		String message = e.getMessage();
		// regex operations aren't that expensive, now www. to www999. URLs
		// should be shortened
		if (e.getPlayer().hasPermission("shortify.shorten")) {
			// REGEX from Daring Fireball
			Pattern p = Pattern
					.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?������]))");
			Matcher m = p.matcher(message);
			StringBuffer sb = new StringBuffer();
			String urlTmp = "";
			String minlength = plugin.getConfig().getString("minlength");
			int min = Integer.parseInt(minlength);
			while (m.find()) {
				try {
					urlTmp = m.group(1);
					if (urlTmp.length() > min) {
						try {
							urlTmp = shortener
									.getShortenedUrl(java.net.URLEncoder
											.encode(urlTmp, "UTF-8"));
							// might as well put the encoder in the listener to
							// prevent possible injections
						} catch (UnsupportedEncodingException e1) {
							// do absolutely nothing
						}
					}
				} catch (ShortifyException e1) {
					Bukkit.getConsoleSender().sendMessage(
							ChatColor.RED + "Warning: " + e1.getMessage());
				}
				m.appendReplacement(sb, "");
				sb.append(urlTmp);
			}
			m.appendTail(sb);
			e.setMessage(sb.toString());
		}
	}
}
