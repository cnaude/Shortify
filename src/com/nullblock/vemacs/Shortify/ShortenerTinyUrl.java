package com.nullblock.vemacs.Shortify;

public class ShortenerTinyUrl implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		return URLReader.getUrlSimple("http://tinyurl.com/api-create.php?url="
					+ toshort, "tinyurl.com");
	}
}
