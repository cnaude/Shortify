package com.nullblock.vemacs.Shortify.platforms.bukkit;

import org.bukkit.plugin.Plugin;

import com.nullblock.vemacs.Shortify.Shortener;
import com.nullblock.vemacs.Shortify.ShortenerBitLy;
import com.nullblock.vemacs.Shortify.ShortenerGooGl;
import com.nullblock.vemacs.Shortify.ShortenerIsGd;
import com.nullblock.vemacs.Shortify.ShortenerTinyUrl;
import com.nullblock.vemacs.Shortify.ShortenerTurlCa;
import com.nullblock.vemacs.Shortify.ShortenerYourls;

public class BukkitShared {
	protected static Shortener getShortener(Plugin plugin) {
		String service = plugin.getConfig().getString("shortener");
		Shortener shortener = null;
		if (service.equals("googl")) {
			shortener = new ShortenerGooGl(plugin.getConfig().getString(
					"googAPI"));
		}
		if (service.equals("bitly")) {
			shortener = new ShortenerBitLy(plugin.getConfig().getString(
					"bitlyUSER"), plugin.getConfig().getString("bitlyAPI"));
		}
		if (service.equals("yourls")) {
			shortener = new ShortenerYourls(plugin.getConfig().getString(
					"yourlsURI"), plugin.getConfig().getString("yourlsUSER"),
					plugin.getConfig().getString("yourlsPASS"));
		}
		if (service.equals("tinyurl")) {
			shortener = new ShortenerTinyUrl();
		}
		if (service.equals("turlca")) {
			shortener = new ShortenerTurlCa();
		}
		if (service.equals("isgd")) {
			shortener = new ShortenerIsGd();
		}
		if(shortener == null) {
			shortener = new ShortenerIsGd();
		}
		return shortener;
	}
}
