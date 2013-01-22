package com.nullblock.vemacs.Shortify.common;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The generic Shortify listener interface used by all Shortify platforms. This
 * class should be extended from the listener and implement the listener as
 * well.
 * 
 * @author minecrafter/vemacs
 */
public class GenericShortifyListener {

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
					//CommonConfiguration <config>;
					//urlTmp = textToColor(<config>.getString("Prefix")) + urlTmp + ChatColor.RESET;
					//TODO: use a more extensible method for textToColor that doesn't rely on hardcoding
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

	public String classicUrlShorten(String message, Integer minln,
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
}
