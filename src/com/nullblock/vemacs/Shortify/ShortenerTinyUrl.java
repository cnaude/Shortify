package com.nullblock.vemacs.Shortify;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerTinyUrl implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
		String toread = "http://tinyurl.com/api-create.php?url=" + toshort;
		String shortened = URLReader.getShortenedUrl(toread, "TinyURL");
		return shortened;
	}
}
