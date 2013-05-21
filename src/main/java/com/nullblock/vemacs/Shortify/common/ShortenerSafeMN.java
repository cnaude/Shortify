package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerSafeMN implements Shortener {
  public String getShortenedUrl(String toshort) throws ShortifyException {
		String url = "http://safe.mn/api/shorten?url=%s&format=%s";
		url = String.format(url, toshort, "text");
		return ShortifyUtility.getUrlSimple(url, "safe.mn");
	}
}
