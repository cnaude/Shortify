package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerTurlCa implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		String shorted = "http://turl.ca/api.php?url=" + toshort;
		shorted = URLReader.getShortenedUrl(shorted, "turl.ca");
		return shorted;
	}
}
