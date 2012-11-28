package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerYourls implements Shortener {

	private String yourls_apiuri;
	private String yourls_apiuser;
	private String yourls_apipass;

	public ShortenerYourls(String uri, String u, String p) {
		yourls_apiuri = uri;
		yourls_apiuser = u;
		yourls_apipass = p;
	}

	@Override
	public String getShortenedUrl(String toshort) throws ShortifyException {
		URL shorted = null;
		try {
			shorted = new URL(yourls_apiuri + "?username=" + yourls_apiuser
					+ "&password=" + yourls_apipass + "&action=shorturl&url="
					+ toshort + "&format=simple");
		} catch (MalformedURLException e1) {

		}
		String inputLine = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					shorted.openStream()));
			// with the encoding in the listener, should we make YOURLS reference URLReader?
			// YOURLS may output an XML document instead of an URL.
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.startsWith("<?xml")) {
					// API error, handle as needed
					if (inputLine.contains("already exists")) {
						return inputLine.split("<shorturl>")[1]
								.split("</shorturl>")[0];
					} else {
						throw new ShortifyException("YOURLS API error: "
								+ inputLine);
					}
				}
				if (inputLine.startsWith("http://")) {
					return inputLine;
				}
				throw new ShortifyException("YOURLS API error: " + inputLine);
			}
		} catch (IOException ex) {
			throw new ShortifyException(
					"Unable to shorten via YOURLS (host down?): "
							+ ex.getMessage());
		}
		return null;
	}

}
