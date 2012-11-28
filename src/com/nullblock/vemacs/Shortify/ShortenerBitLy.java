package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerBitLy implements Shortener {

	private String u, a = "";

	public ShortenerBitLy(String user, String apikey) {
		u = user;
		a = apikey;
	}

	public String getShortenedUrl(String toshort) throws ShortifyException {
		String toread = "http://api.bit.ly/v3/shorten?login=" + u
					+ "&apiKey=" + a + "&longUrl=" + toshort + "&format=txt";
		String shortened = URLReader.getShortenedUrl(toread, "bit.ly");
		return shortened;
	}

}
