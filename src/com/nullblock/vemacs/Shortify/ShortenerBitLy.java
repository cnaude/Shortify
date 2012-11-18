package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerBitLy implements Shortener {

	private String u, a = "";

	public ShortenerBitLy(String user, String apikey) {
		u = user;
		a = apikey;
	}

	public String getShortenedUrl(String toshort) throws ShortifyException {
		URL shorted = null;
		try {
			String encoded = java.net.URLEncoder.encode(toshort, "UTF-8");
			// proper URL encoding to not fuck up anything
			shorted = new URL("http://api.bit.ly/v3/shorten?login=" + u
					+ "&apiKey=" + a + "&longUrl=" + encoded + "&format=txt");
		} catch (MalformedURLException e1) {

		} catch (UnsupportedEncodingException e) {
		}
		String inputLine = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					shorted.openStream()));
			while ((inputLine = in.readLine()) != null)
				return inputLine;
			in.close();
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via bit.ly: "
					+ ex.getMessage());
		}
		return inputLine;
	}

}
