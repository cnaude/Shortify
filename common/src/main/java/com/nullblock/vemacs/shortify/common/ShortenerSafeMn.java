package com.nullblock.vemacs.shortify.common;

import com.nullblock.vemacs.shortify.util.ShortifyUtility;

public class ShortenerSafeMn implements Shortener {
    public String getShortenedUrl(String toshort) throws ShortifyException {
        String url = "http://safe.mn/api/shorten?url=%s&format=%s";
        url = String.format(url, toshort, "text");
        return ShortifyUtility.getUrlSimple(url).trim();
    }
}
