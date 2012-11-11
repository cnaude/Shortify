package com.nullblock.vemacs.Shortify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ShortenerTurlCa implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
	    URL shorted = null;
		try {
			shorted = new URL("http://turl.ca/api.php?url=" + toshort );
		} catch (MalformedURLException e1) {

		}
        String inputLine = null;
        try {
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(shorted.openStream()));
	        while ((inputLine = in.readLine()) != null) {
	        	if(inputLine.startsWith("SUCCESS:")) {
	        		return inputLine.replace("SUCCESS:", "http://turl.ca/");
	        	}
		    	if(inputLine.startsWith("ERROR:")) {
		    		throw new ShortifyException("Unable to shorten via turl.ca (API error): "+inputLine);
		    	}
	        }
	        in.close();
        }
	    catch (IOException ex)
	    {    
	    	throw new ShortifyException("Unable to shorten via turl.ca: "+ex.getMessage());
	    }
		return inputLine;
	}
}
