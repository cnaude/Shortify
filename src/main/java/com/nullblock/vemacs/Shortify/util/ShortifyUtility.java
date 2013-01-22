package com.nullblock.vemacs.Shortify.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.MetricsImpl.Graph;
import com.nullblock.vemacs.Shortify.util.MetricsImpl.Plotter;

public class ShortifyUtility {
	public static void setupMetrics(MetricsImpl metrics, CommonConfiguration cc) throws IOException {
		// Cause I won't live and die
		// For the part with a dirty CD-i
		Graph g = metrics.createGraph("URL Shortener");
		g.addPlotter(new Plotter(cc.getString("shortener", "isgd")) {
			@Override
			public int getValue() {
				return 1;
			}
		});
		metrics.start();
	}
	
	public static BufferedReader getUrl(String toread) throws IOException {
		return new BufferedReader(new InputStreamReader(
				new URL(toread).openStream()));
	}

	public static String getUrlSimple(String uri, String srv)
			throws ShortifyException {
		String inputLine = null;
		try {
			BufferedReader in = getUrl(uri);
			String s = "";
			while ((inputLine = in.readLine()) != null)
				s += inputLine;
			in.close();
			return s;
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via " + srv + ": "
					+ ex.getMessage());
		}
	}
	
	public static String textToColor(String text)
	{
		//from http://dev.bukkit.org/server-mods/chatman/pages/code/text-to-color/
		//there are probably better implementations available, this is missing underlines, bold, etc...
		//look into encoding
		text = text.replaceAll("&0", ChatColor.BLACK+"");
		text = text.replaceAll("&1", ChatColor.DARK_BLUE+"");
		text = text.replaceAll("&2", ChatColor.DARK_GREEN+"");
		text = text.replaceAll("&3", ChatColor.DARK_AQUA+"");
		text = text.replaceAll("&4", ChatColor.DARK_RED+"");
		text = text.replaceAll("&5", ChatColor.DARK_PURPLE+"");
		text = text.replaceAll("&6", ChatColor.GOLD+"");te
		text = text.replaceAll("&7", ChatColor.GRAY+"");
		text = text.replaceAll("&8", ChatColor.DARK_GRAY+"");
		text = text.replaceAll("&9", ChatColor.BLUE+"");
		text = text.replaceAll("&A", ChatColor.GREEN+"");
		text = text.replaceAll("&B", ChatColor.AQUA+"");
		text = text.replaceAll("&C", ChatColor.RED+"");
		text = text.replaceAll("&D", ChatColor.LIGHT_PURPLE+"");
		text = text.replaceAll("&E", ChatColor.YELLOW+"");
		text = text.replaceAll("&F", ChatColor.WHITE+"");
		text = text.replaceAll("&a", ChatColor.GREEN+"");
		text = text.replaceAll("&b", ChatColor.AQUA+"");
		text = text.replaceAll("&c", ChatColor.RED+"");
		text = text.replaceAll("&d", ChatColor.LIGHT_PURPLE+"");
		text = text.replaceAll("&e", ChatColor.YELLOW+"");
		text = text.replaceAll("&u", ChatColor.UNDERLINE+"");
		text = text.replaceAll("&U", ChatColor.UNDERLINE+"");
		text = text.replaceAll("&n", ChatColor.BOLD+"");
		text = text.replaceAll("&N", ChatColor.BOLD+"");
		text = text.replaceAll("&o", ChatColor.ITALIC+"");
		text = text.replaceAll("&O", ChatColor.ITALIC+"");
		text = text.replaceAll("&i", ChatColor.ITALIC+"");
		text = text.replaceAll("&I", ChatColor.ITALIC+"");
		text = text.replaceAll("&k", ChatColor.MAGIC+"");
		text = text.replaceAll("&K", ChatColor.MAGIC+"");
		text = text.replaceAll("&r", ChatColor.RESET+"");
		return text;
	}
}
