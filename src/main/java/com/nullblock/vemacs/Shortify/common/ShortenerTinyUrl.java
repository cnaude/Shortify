package com.nullblock.vemacs.Shortify.common;


public class ShortenerTinyUrl implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		return URLReader.getUrlSimple("http://tinyurl.com/api-create.php?url="
					+ toshort, "tinyurl.com");
	}
}
