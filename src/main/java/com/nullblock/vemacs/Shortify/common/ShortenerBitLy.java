package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerBitLy implements Shortener {

	private String u, a = "";

	public ShortenerBitLy(String user, String apikey) {
		u = user;
		a = apikey;
	}

	public String getShortenedUrl(String toshort) throws ShortifyException {
		if (u.equals("none") || a.equals("none")) {
			throw new ShortifyException("No API username/key");
		}
		return ShortifyUtility.getUrlSimple(
				"http://api.bit.ly/v3/shorten?login=" + u + "&apiKey=" + a
						+ "&longUrl=" + toshort + "&format=txt", "bit.ly");
	}

}
