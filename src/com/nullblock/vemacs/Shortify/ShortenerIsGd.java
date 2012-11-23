package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerIsGd implements Shortener {

	public String getShortenedUrl(String toshort) throws ShortifyException {
		URL shorted = null;
		try {
			shorted = new URL("http://is.gd/create.php?format=simple&url="
					+ toshort);
		} catch (MalformedURLException e1) {

		}
		String inputLine = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					shorted.openStream()));
			while ((inputLine = in.readLine()) != null)
				return inputLine;
			in.close();
		} catch (IOException ex) {
			try {
				String decoded = java.net.URLDecoder.decode(toshort, "UTF-8");
				throw new ShortifyException("Unable to shorten via is.gd: "
						+ decoded);
			} catch (UnsupportedEncodingException e) {
			}

		}
		return inputLine;
	}

}
