package com.nullblock.vemacs.Shortify.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortenerBitLy;
import com.nullblock.vemacs.Shortify.common.ShortenerGooGl;
import com.nullblock.vemacs.Shortify.common.ShortenerIsGd;
import com.nullblock.vemacs.Shortify.common.ShortenerPasteDebianNet;
import com.nullblock.vemacs.Shortify.common.ShortenerTinyUrl;
import com.nullblock.vemacs.Shortify.common.ShortenerTurlCa;
import com.nullblock.vemacs.Shortify.common.ShortenerTx0;
import com.nullblock.vemacs.Shortify.common.ShortenerYourls;
import com.nullblock.vemacs.Shortify.common.ShortenerYu8Me;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.Metrics;

public class ShortifyUtility {
	public static void setupMetrics(Metrics metrics, CommonConfiguration cc) throws IOException {
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
			Shortener shortener) throws ShortifyException {
		// From Daring Fireball
		Pattern p = Pattern
				.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?������]))");
		Matcher m = p.matcher(txt);
		StringBuffer sb = new StringBuffer();
		String urlTmp = "";
		while (m.find()) {
			urlTmp = m.group(1);
			if (urlTmp.length() >= minln) {
				try {
					urlTmp = shortener.getShortenedUrl(java.net.URLEncoder
							.encode(urlTmp, "UTF-8"));
					// might as well put the encoder in the listener to
					// prevent possible injections
				} catch (UnsupportedEncodingException e1) {
					// do absolutely nothing
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
		String service = c.getString("shortener");
		Shortener shortener = null;
		if (service.equals("googl")) {
			shortener = new ShortenerGooGl(c.getString("googAPI"));
		}
		if (service.equals("bitly")) {
			shortener = new ShortenerBitLy(c.getString("bitlyUSER"),
					c.getString("bitlyAPI"));
		}
		if (service.equals("yourls")) {
			shortener = new ShortenerYourls(c.getString("yourlsURI"),
					c.getString("yourlsUSER"), c.getString("yourlsPASS"));
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
		// Accepting both frmli and pdo
		if (service.equals("frmli") || service.equals("pdo")) {
			shortener = new ShortenerPasteDebianNet();
		}
		if (service.equals("tx0") || service.equals("tx0org")) {
			shortener = new ShortenerTx0();
		}
		if (service.equals("yu8me") || service.equals("yu8")) {
			shortener = new ShortenerYu8Me();
		}
		if (shortener == null) {
			shortener = new ShortenerIsGd();
		}
		return shortener;
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
	//copied from http://forums.bukkit.org/threads/simple-colors-parsing-method.32058/#post-1251988
        char[] chrarray = text.toCharArray();
 
        for (int index = 0; index < chrarray.length; index ++) {
            char chr = chrarray[index];
            if (chr != '&') {
                continue;
            }
 
            if ((index + 1) == chrarray.length) {
                break;
            }
             char forward = chrarray[index + 1];
            if ((forward >= '0' && forward <= '9') || (forward >= 'a' && forward <= 'f') || (forward >= 'k' && forward <= 'r')) {
                chrarray[index] = '\u00A7';
            }
        }
         return new String(chrarray);
    	}
}
