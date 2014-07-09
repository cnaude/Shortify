package com.nullblock.vemacs.shortify.common;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShortenerTx0 implements Shortener {

    @Override
    public String getShortenedUrl(String toshort) throws ShortifyException {
        try {
            URLConnection conn = new URL("http://tx0.org").openConnection();
            conn.setDoOutput(true);
            try (OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream())) {
                wr.write("url=" + toshort);
                wr.flush();
            }

            String s;
            try (InputStream is = conn.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                ByteStreams.copy(is, out);
                s = new String(out.toByteArray());
            }
            return s.split("CCC>")[1].split("</td>")[0];
        } catch (MalformedURLException ignored) {
        } catch (IOException ex) {
            throw new ShortifyException("Unable to shorten via tx0.org: "
                    + ex.getMessage());
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ShortifyException(
                    "Unable to shorten via tx0.org: Input returned was not expected!");
        }
        throw new ShortifyException(
                "Unable to shorten via tx0.org: We shouldn't get here!");
    }

}
