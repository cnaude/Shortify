package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShortenerGooGl implements Shortener {

	private String a = "";

	public ShortenerGooGl(String apikey) {
		a = apikey;
	}

	public String getShortenedUrl(String toshort) throws ShortifyException {
		//copypasta code from http://www.glodde.com/blog/default.aspx?id=51&t=Java-Use-googl-URL-shorten-from-Java
		String shortUrl = "";
		String longUrl = toshort;
		String googUrl = "https://www.googleapis.com/urlshortener/v1/url?key=" + a;
		try
		{
			URLConnection conn = new URL(googUrl).openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter wr = 
					new OutputStreamWriter(conn.getOutputStream());
			wr.write("{\"longUrl\":\"" + longUrl + "\"}");
			wr.flush();

			BufferedReader rd = 
					new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
			String line;

			while ((line = rd.readLine()) != null)
			{
				if (line.indexOf("id") > -1)
				{
					shortUrl = line.substring(8, line.length() - 2);
					break;
				}
			}

			wr.close();
			rd.close();
		}
		catch (MalformedURLException ex)
		{
			shortUrl = longUrl;
		}
		catch (IOException ex)
		{
			throw new ShortifyException("Unable to shorten via goo.gl: " + ex.getMessage());
		}
		return shortUrl;
	}
}
