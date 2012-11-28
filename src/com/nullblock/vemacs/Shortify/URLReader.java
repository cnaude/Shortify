package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLReader {
	public String getShortenedUrl(String toread, String service) throws ShortifyException {
		URL shorted = null;
		try {
			shorted = new URL(toread);
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
			throw new ShortifyException("Unable to shorten via " + service + ": "
					+ toread);
		}
		return inputLine;
	}
}
