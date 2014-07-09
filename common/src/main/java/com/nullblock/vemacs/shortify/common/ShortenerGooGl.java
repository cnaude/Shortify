package com.nullblock.vemacs.shortify.common;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nullblock.vemacs.shortify.util.ShortifyUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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

        try {
            toshort = URLDecoder.decode(toshort, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
        }

        String googUrl = "https://www.googleapis.com/urlshortener/v1/url?key="
                + a;
        try {
            URLConnection conn = new URL(googUrl).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            try (OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream())) {
                JsonObject jo = new JsonObject();
                jo.addProperty("longUrl", toshort);
                wr.write(ShortifyUtility.getGson().toJson(jo));
                wr.flush();
            }

            try (InputStream is = conn.getInputStream()) {
                String json = new String(ByteStreams.toByteArray(is));
                GooGlReply reply = ShortifyUtility.getGson().fromJson(json, GooGlReply.class);
                return reply.getId();
            } catch (JsonParseException e) {
                throw new ShortifyException("Unable to parse JSON", e);
            }
        } catch (IOException ex) {
            throw new ShortifyException("Unable to shorten via goo.gl", ex);
        }
    }

    private class GooGlReply {
        private String id;

        public String getId() {
            return id;
        }
    }
}
