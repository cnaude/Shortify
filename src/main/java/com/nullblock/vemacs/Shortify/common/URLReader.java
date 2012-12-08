package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class URLReader {
	public static BufferedReader getUrl(String toread) throws IOException {
		return new BufferedReader(new InputStreamReader(
					new URL(toread).openStream()));
	}
	
	public static String getUrlSimple(String uri, String srv) throws ShortifyException {
		String inputLine = null;
		try {
			BufferedReader in = URLReader.getUrl(uri);
			while ((inputLine = in.readLine()) != null)
				return inputLine;
			in.close();
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via "+srv+": "
					+ ex.getMessage());
		}
		return inputLine;
	}
}
