package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerIsGd implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		return ShortifyUtility
				.getUrlSimple("http://is.gd/create.php?format=simple&url="
						+ toshort);
	}
}
