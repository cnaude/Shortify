package com.nullblock.vemacs.Shortify;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShortenerBitLy implements Shortener {

	private String u, a = "";
	
	public ShortenerBitLy(String user, String apikey) {
		u = user;
		a = apikey;
	}
	
	public String getShortenedUrl(String toshort) throws ShortifyException {
	    String shortUrl = "";
	    try
	    {
	    	String bitlyUrl = "http://api.bit.ly/v3/shorten?login=" + u + "&apiKey=" + a + "&longUrl=" + toshort + "&format=json";
	        URLConnection conn = new URL(bitlyUrl).openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/json");
	        OutputStreamWriter wr = 
	                     new OutputStreamWriter(conn.getOutputStream());
	        wr.write("{\"longUrl\":\"" + toshort + "\"}");
	        wr.flush();

	        BufferedReader rd = 
	                     new BufferedReader(
	                     new InputStreamReader(conn.getInputStream()));
	        String line;

	        while ((line = rd.readLine()) != null)
	        {
	            if (line.indexOf("hash") > -1)
	            {
	                shortUrl = "http://bit.ly/" + line.substring(13, line.length() - 2);
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
	    	throw new ShortifyException("Unable to shorten: "+ex.getMessage());
	    }

	    return shortUrl;
	}

}
