package com.nullblock.vemacs.Shortify;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The generic Shortify listener interface used by all Shortify platforms.
 * This class should be extended from the listener and implement the listener
 * as well.
 * 
 * @author minecrafter/vemacs
 */
public class GenericShortifyListener {
	/**
	 * Shorten all URLs in a String.
	 * @throws ShortifyException 
	 */
	protected String shortenAll(String txt, Integer minln, Shortener shortener) throws ShortifyException {
		// From Daring Fireball
		Pattern p = Pattern
				.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?������]))");
		Matcher m = p.matcher(txt);
		StringBuffer sb = new StringBuffer();
		String urlTmp = "";
		while (m.find()) {
			urlTmp = m.group(1);
			if (urlTmp.length() > minln) {
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
			m.appendReplacement(sb, "");
			sb.append(urlTmp);
		}
		return sb.toString();
	}
}
