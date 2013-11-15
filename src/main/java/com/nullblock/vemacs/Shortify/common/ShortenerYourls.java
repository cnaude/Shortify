package com.nullblock.vemacs.Shortify.common;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

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
        if (apiUrl.equals("none") || apiUser.equals("none") || apiPass.equals("none")) {
            throw new ShortifyException("No API username/key");
        }
        String output = ShortifyUtility.getUrlSimple(apiUrl
                + "?username=" + apiUser + "&password="
                + apiPass + "&action=shorturl&url=" + toshort
                + "&format=simple");
        // YOURLS may output an XML document instead of an URL.
        if (output.startsWith("<?xml")) {
            // API error, handle as needed
            if (output.contains("already exists")) {
                return output.split("<shorturl>")[1]
                        .split("</shorturl>")[0];
            } else {
                throw new ShortifyException("YOURLS API error: "
                        + output);
            }
        }
        if (output.startsWith("http://")) {
            return output.trim();
        }
        throw new ShortifyException("YOURLS API error: " + output);
    }

}
