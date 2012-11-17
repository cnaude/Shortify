package com.nullblock.vemacs.Shortify;


import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerTinyUrl implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
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
	    	throw new ShortifyException("Unable to shorten: "+ex.getMessage());
	    }
		return inputLine;
	}
}
