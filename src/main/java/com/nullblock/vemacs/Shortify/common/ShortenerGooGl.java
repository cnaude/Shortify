package com.nullblock.vemacs.Shortify.common;

import com.google.common.io.ByteStreams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class ShortenerGooGl implements Shortener {

    private String a = "";

    public ShortenerGooGl(String apikey) {
        a = apikey;
    }

    public String getShortenedUrl(String toshort) throws ShortifyException {
        // copypasta code from
        // http://www.glodde.com/blog/default.aspx?id=51&t=Java-Use-googl-URL-shorten-from-Java
        if (a.equals("none")) {
            throw new ShortifyException("No API key");
        }

        JSONParser jp = new JSONParser();
        String shortUrl = "";

        try {
            toshort = URLDecoder.decode(toshort, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
        }

        String longUrl = toshort;
        String googUrl = "https://www.googleapis.com/urlshortener/v1/url?key="
                + a;
        try {
            URLConnection conn = new URL(googUrl).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            try (OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream())) {
                JSONObject jo = new JSONObject();
                jo.put("longUrl", longUrl);
                wr.write(jo.toJSONString());
                wr.flush();
            }

            try (InputStream is = conn.getInputStream()) {
                String json = new String(ByteStreams.toByteArray(is));
                Object object = jp.parse(json);
                if (object instanceof JSONObject) {
                    JSONObject jo = (JSONObject) object;
                    Object urlObj = jo.get("id");
                    if (urlObj != null && urlObj instanceof String)
                        shortUrl = (String) jo.get("id");
                    else
                        throw new ShortifyException("Unexpected JSON response from goo.gl!");
                }
            } catch (ParseException e) {
                throw new ShortifyException("Unable to parse JSON", e);
            }
        } catch (MalformedURLException ex) {
            shortUrl = longUrl;
        } catch (IOException ex) {
            throw new ShortifyException("Unable to shorten via goo.gl", ex);
        }
        return shortUrl;
    }
}
