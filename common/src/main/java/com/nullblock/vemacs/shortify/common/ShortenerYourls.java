package com.nullblock.vemacs.shortify.common;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.nullblock.vemacs.shortify.util.ShortifyUtility;

public class ShortenerYourls implements Shortener {

    private String apiUrl;
    private String apiUser;
    private String apiPass;

    public ShortenerYourls(String uri, String u, String p) {
        apiUrl = uri;
        apiUser = u;
        apiPass = p;
    }

    @Override
    public String getShortenedUrl(String toshort) throws ShortifyException {
        if (apiUrl.equals("none")) {
            throw new ShortifyException("No API username/key");
        }
        String output = ShortifyUtility.getUrlSimple(apiUrl
                + "?username=" + apiUser + "&password="
                + apiPass + "&action=shorturl&url=" + toshort
                + "&format=json");
        try {
            return ShortifyUtility.getGson().fromJson(output, YourlsReply.class).getShortUrl();
        } catch (JsonParseException e) {
            throw new ShortifyException("Could not parse JSON from YOURLS", e);
        }
    }

    private class YourlsReply {
        @SerializedName("shorturl")
        private String shortUrl;

        public String getShortUrl() {
            return shortUrl;
        }
    }

}
