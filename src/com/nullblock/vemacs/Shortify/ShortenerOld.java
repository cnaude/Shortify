package com.nullblock.vemacs.Shortify;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class ShortenerOld {

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
