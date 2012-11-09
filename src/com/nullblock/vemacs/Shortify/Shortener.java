package com.nullblock.vemacs.Shortify;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class Shortener {
	public static String GetShortedTinyURL(String toshort) {
	    URL shorted = null;
		try {
			shorted = new URL("http://tinyurl.com/api-create.php?url=" + toshort );
		} catch (MalformedURLException e1) {

		}
        String inputLine = null;
        try {
        BufferedReader in = new BufferedReader(
        new InputStreamReader(shorted.openStream()));
        while ((inputLine = in.readLine()) != null)
    		return inputLine;
        in.close();
        }
	    catch (IOException ex)
	    {    
	    	Bukkit.broadcastMessage("TinyURL.com was unable to shorten " + ChatColor.RED + toshort);
	    	inputLine = toshort;
	    }
		return inputLine;
	}
	public static String GetShortedISGD(String toshort) {
	    URL shorted = null;
		try {
			shorted = new URL("http://is.gd/create.php?format=simple&url=" + toshort );
		} catch (MalformedURLException e1) {

		}
        String inputLine = null;
        try {
        BufferedReader in = new BufferedReader(
        new InputStreamReader(shorted.openStream()));
        while ((inputLine = in.readLine()) != null)
    		return inputLine;
        in.close();
        }
	    catch (IOException ex)
	    {    
	    	Bukkit.broadcastMessage("is.gd was unable to shorten " + ChatColor.RED + toshort);
	    	inputLine = toshort;
	    }
		return inputLine;
	}
	public static String GetShortedBITLY(String toshort, String bitlyUSER, String bitlyAPI) {
	    URL shorted = null;
		try {
			shorted = new URL("http://api.bit.ly/v3/shorten?login=" + bitlyUSER + "&apiKey=" + bitlyAPI + "&longUrl=" + toshort + "&format=txt");
		} catch (MalformedURLException e1) {

		}
        String inputLine = null;
        try {
        BufferedReader in = new BufferedReader(
        new InputStreamReader(shorted.openStream()));
        while ((inputLine = in.readLine()) != null)
    		return inputLine;
        in.close();
        }
	    catch (IOException ex)
	    {    
	    	Bukkit.broadcastMessage("bit.ly was unable to shorten " + ChatColor.RED + toshort);
	    	inputLine = toshort;
	    }
		return inputLine;
	}

public static String GetShortedGOOGL(String longUrl, String googAPI)
	{
	    String shortUrl = "";
	    try
	    {
	    	String googUrl = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=http://goo.gl/fbsS&key=" + googAPI;
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
	    }
	    catch (IOException ex)
	    {    
	    	Bukkit.broadcastMessage("goo.gl was unable to shorten " + ChatColor.RED + longUrl);
	    	shortUrl = longUrl;
	    }

	    return shortUrl;
	}

}
