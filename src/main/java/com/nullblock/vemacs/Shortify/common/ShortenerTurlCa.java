package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortenerTurlCa implements Shortener {
	public String getShortenedUrl(String toshort) throws ShortifyException {
        String input = ShortifyUtility
                .getUrlSimple("http://turl.ca/api.php?url=" + toshort);
        if (input.startsWith("SUCCESS:")) {
            return input.replace("SUCCESS:", "http://turl.ca/");
        }
        else {
            throw new ShortifyException(
                    "Unable to shorten via turl.ca (API error): "
                            + input);
        }
	}
}
