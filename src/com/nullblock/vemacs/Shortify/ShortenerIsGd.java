package com.nullblock.vemacs.Shortify;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerIsGd implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		String toread = "http://is.gd/create.php?format=simple&url=" + toshort;
		String shortened = URLReader.getShortenedUrl(toread, "is.gd");
		return shortened;
	}
}
