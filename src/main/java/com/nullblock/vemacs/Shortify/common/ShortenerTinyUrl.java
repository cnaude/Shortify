package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerTinyUrl implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		return ShortifyUtility.getUrlSimple(
				"http://tinyurl.com/api-create.php?url=" + toshort);
	}
}
