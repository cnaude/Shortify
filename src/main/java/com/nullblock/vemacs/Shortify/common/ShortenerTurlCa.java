package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedReader;
import java.io.IOException;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerTurlCa implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		String inputLine = null;
		try {
			BufferedReader in = ShortifyUtility.getUrl("http://turl.ca/api.php?url="
					+ toshort);
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.startsWith("SUCCESS:")) {
					return inputLine.replace("SUCCESS:", "http://turl.ca/");
				}
				if (inputLine.startsWith("ERROR:")) {
					throw new ShortifyException(
							"Unable to shorten via turl.ca (API error): "
									+ inputLine);
				}
			}
			in.close();
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via turl.ca: "
					+ ex.getMessage());
		}
		return inputLine;
	}
}
