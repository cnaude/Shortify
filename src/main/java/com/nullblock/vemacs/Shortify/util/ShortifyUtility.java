package com.nullblock.vemacs.Shortify.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mcstats.Metrics;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.Globals;
import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortenerBitLy;
import com.nullblock.vemacs.Shortify.common.ShortenerGooGl;
import com.nullblock.vemacs.Shortify.common.ShortenerIsGd;
import com.nullblock.vemacs.Shortify.common.ShortenerManager;
import com.nullblock.vemacs.Shortify.common.ShortenerNigGr;
import com.nullblock.vemacs.Shortify.common.ShortenerSafeMn;
import com.nullblock.vemacs.Shortify.common.ShortenerTinyUrl;
import com.nullblock.vemacs.Shortify.common.ShortenerTurlCa;
import com.nullblock.vemacs.Shortify.common.ShortenerTx0;
import com.nullblock.vemacs.Shortify.common.ShortenerYourls;
import com.nullblock.vemacs.Shortify.common.ShortenerYu8Me;
import com.nullblock.vemacs.Shortify.common.ShortifyException;

public class ShortifyUtility {
	public static ShortenerManager setupShorteners() {
		ShortenerManager sm = new ShortenerManager();
		sm.registerShortener("isgd", new ShortenerIsGd());
		sm.registerShortener("niggr", new ShortenerNigGr());
		sm.registerShortener("safemn", new ShortenerSafeMn());
		sm.registerShortener("tinyurl", new ShortenerTinyUrl());
		sm.registerShortener("turlca", new ShortenerTurlCa());
		sm.registerShortener("tx0", new ShortenerTx0());
		sm.registerShortener("yu8me", new ShortenerYu8Me());
		return sm;
	}
	public static ShortenerManager reloadConfigShorteners(ShortenerManager sm, CommonConfiguration c) {
		sm.unregisterShortener("bitly");
		sm.registerShortener("bitly", new ShortenerBitLy(
				c.getString("bitlyUSER"), c.getString("bitlyAPI")));
		sm.unregisterShortener("yourls");
		sm.registerShortener("yourls", new ShortenerYourls(
				c.getString("yourlsURI"), c.getString("yourlsUSER"),
				c.getString("yourlsPASS")));
		sm.unregisterShortener("googl");
		sm.registerShortener("googl", new ShortenerGooGl(
				c.getString("googAPI")));
		return sm;
	}
	public static void setupMetrics(Metrics metrics, CommonConfiguration cc)
			throws IOException {
		// Cause I won't live and die
		// For the part with a dirty CD-i
		Metrics.Graph g = metrics.createGraph("URL Shortener");
		g.addPlotter(new Metrics.Plotter(cc.getString("shortener", "isgd")) {
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

	/**
	 * Shorten all URLs in a String.
	 * 
	 * @throws ShortifyException
	 */
	public static String shortenAll(String txt, Integer minln,
			Shortener shortener, String prefix) throws ShortifyException {
		// From Daring Fireball
		prefix = replaceColors(prefix);
		Pattern p = Pattern
				.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?������]))");
		Matcher m = p.matcher(txt);
		StringBuffer sb = new StringBuffer();
		String urlTmp = "";
		while (m.find()) {
			urlTmp = m.group(1);
			if (urlTmp.length() >= minln) {
				try {
					if (!prefix.equals("")) {
						urlTmp = prefix
								+ shortener.getShortenedUrl(java.net.URLEncoder
										.encode(urlTmp, "UTF-8"))
								+ replaceColors("&r");
					} else {
						urlTmp = shortener.getShortenedUrl(java.net.URLEncoder
								.encode(urlTmp, "UTF-8"));
					}
					// might as well put the encoder in the listener to
					// prevent possible injections
				} catch (UnsupportedEncodingException e1) {
					// do absolutely nothing
				}
			} else {
				if (!prefix.equals("")) {
					urlTmp = prefix + urlTmp + replaceColors("&r");
				}
			}
			m.appendReplacement(sb, "");
			sb.append(urlTmp);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 
	 */
	public static Shortener getShortener(CommonConfiguration c) {
		return Globals.sm.getShortener(c.getString("shortener"));
	}

	public static String classicUrlShorten(String message, Integer minln,
			Shortener shortener) throws ShortifyException {
		Pattern p = Pattern
				.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½]))");
		Matcher m = p.matcher(message);
		String urlTmp = "";
		String output = "The following URLs were shortened: ";
		while (m.find()) {
			urlTmp = m.group(1);
			if (urlTmp.length() > minln) {
				try {
					output = output
							+ shortener.getShortenedUrl(java.net.URLEncoder
									.encode(urlTmp, "UTF-8")) + " ,";
					// might as well put the encoder in the listener to
					// prevent possible injections
				} catch (UnsupportedEncodingException e1) {
					// do absolutely nothing
				}
			}
		}
		return output.substring(0, output.length() - 3);
	}

	public static String replaceColors(String text) {
		// copied from
		// http://forums.bukkit.org/threads/simple-colors-parsing-method.32058/#post-1251988
		char[] chrarray = text.toCharArray();

		for (int index = 0; index < chrarray.length; index++) {
			char chr = chrarray[index];
			if (chr != '&') {
				continue;
			}

			if ((index + 1) == chrarray.length) {
				break;
			}
			char forward = chrarray[index + 1];
			if ((forward >= '0' && forward <= '9')
					|| (forward >= 'a' && forward <= 'f')
					|| (forward >= 'k' && forward <= 'r')) {
				chrarray[index] = '\u00A7';
			}
		}
		return new String(chrarray);
	}
	
	public static void verifyConfiguration(CommonConfiguration c, Logger l) {
		if (!(c.getString("mode").equals("replace") || c.getString("mode")
				.equals("classic"))) {
			l.info("Mode not configured correctly!");
			l.info("Reverting to replace mode.");
			c.set("mode", "replace");
		}
		if (c.getString("shortener").equals("bitly")
				&& (c.getString("bitlyUSER").equals("none") || c.getString(
						"bitlyAPI").equals("none"))) {
			l.info("bit.ly is not properly configured in config.yml.");
			l.info("Reverting to default shortener is.gd.");
			c.set("shortener", "isgd");
		}
		if (c.getString("shortener").equals("yourls")
				&& (c.getString("yourlsUSER").equals("none")
						|| c.getString("yourlsURI").equals("none") || c
						.getString("yourlsPASS").equals("none"))) {
			l.info("YOURLS is not properly configured in config.yml.");
			l.info("Reverting to default shortener is.gd.");
			c.set("shortener", "isgd");
		}
		if (c.getString("shortener").equals("googl")
				&& c.getString("googAPI").equals("none")) {
			l.info("goo.gl is not properly configured in config.yml.");
			l.info("Reverting to default shortener is.gd.");
			c.set("shortener", "isgd");
		}
	}

	public static CommonConfiguration loadCfg(File pl) {
		CommonConfiguration c = new CommonConfiguration();
		c.addDefault("mode", "replace");
		c.addDefault("shortener", "isgd");
		c.addDefault("auto-update", "true");
		c.addDefault("prefix", "&n");
		c.addDefault("minlength", "20");
		c.addDefault("googAPI", "none");
		c.addDefault("bitlyUSER", "none");
		c.addDefault("bitlyAPI", "none");
		c.addDefault("yourlsURI", "none");
		c.addDefault("yourlsUSER", "none");
		c.addDefault("yourlsPASS", "none");

		File dataDir = new File(pl.getParent() + "/Shortify");
		File cfg = new File(dataDir, "config.yml");
		try {
			dataDir.mkdirs();
			if (!cfg.exists()) {
				cfg.createNewFile();
				c.dumpYaml(cfg);
			} else {
				c.loadYaml(cfg);
				c.mergeDefaults();
				c.dumpYaml(cfg);
			}
		} catch (IOException e) {
		}
		return c;
	}

	public static void dumpData(File pl, CommonConfiguration c) {
		File dataDir = new File(pl.getParent() + "/Shortify");
		try {
			new File(dataDir, "config.yml").createNewFile();
			c.dumpYaml(new File(dataDir, "config.yml"));
		} catch (IOException e1) {
			// Ignore
		}
	}
}
