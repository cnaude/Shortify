package com.nullblock.vemacs.Shortify.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerNigGr implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		//"special"
		try {
			toshort = URLDecoder.decode(toshort, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return "http://nig.gr/" + ShortifyUtility
				.getUrlSimple( "http://nig.gr/src/web/api/"
						+ toshort, "nig.gr");
	}
}
