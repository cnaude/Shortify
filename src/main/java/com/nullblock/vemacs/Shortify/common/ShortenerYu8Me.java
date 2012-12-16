package com.nullblock.vemacs.Shortify.common;

public class ShortenerYu8Me implements Shortener {

	@Override
	public String getShortenedUrl(String toshort) throws ShortifyException {
		return URLReader.getUrlSimple("http://yu8.me/shorten.php?longurl="
				+ toshort, "yu8.me");
	}

}
