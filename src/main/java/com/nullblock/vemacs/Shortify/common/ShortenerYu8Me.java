package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerYu8Me implements Shortener {

	@Override
	public String getShortenedUrl(String toshort) throws ShortifyException {
		return ShortifyUtility.getUrlSimple(
				"http://yu8.me/shorten.php?longurl=" + toshort);
	}

}
