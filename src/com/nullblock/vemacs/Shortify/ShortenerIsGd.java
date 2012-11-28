package com.nullblock.vemacs.Shortify;

public class ShortenerIsGd implements Shortener {

	public String getShortenedUrl(String toshort) throws ShortifyException {
		return URLReader.getUrlSimple("http://is.gd/create.php?format=simple&url="
					+ toshort, "is.gd");
	}

}
