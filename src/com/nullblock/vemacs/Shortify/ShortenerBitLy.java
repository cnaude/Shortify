package com.nullblock.vemacs.Shortify;


import com.rosaloves.bitlyj.*;
import static com.rosaloves.bitlyj.Bitly.*;


public class ShortenerBitLy implements Shortener {

	private String u, a = "";
	
	public ShortenerBitLy(String user, String apikey) {
		u = user;
		a = apikey;
	}
	
	public String getShortenedUrl(String toshort) throws ShortifyException {
	    String shortUrl = "";
	    Url url = as(u, a).call(shorten(toshort));
		shortUrl = url.getShortUrl();
	    return shortUrl;
	}

}
