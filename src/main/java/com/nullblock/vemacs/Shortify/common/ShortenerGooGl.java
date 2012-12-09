package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShortenerGooGl implements Shortener {

	private String a = "";

	public ShortenerGooGl(String apikey) {
		a = apikey;
	}

	public String getShortenedUrl(String toshort) throws ShortifyException {
		String shortUrl = "";
		try {
			String googUrl = "https://www.googleapis.com/urlshortener/v1/url?shortUrl="
					+ toshort + "&key=" + a;
			URLConnection conn = new URL(googUrl).openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write("{\"longUrl\":\"" + toshort + "\"}");
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;

			while ((line = rd.readLine()) != null) {
				if (line.indexOf("id") > -1) {
					shortUrl = line.substring(8, line.length() - 2);
					break;
				}
			}

			wr.close();
			rd.close();
		} catch (MalformedURLException ex) {
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via goo.gl: "
					+ ex.getMessage());
		}

		return shortUrl;
	}

}
