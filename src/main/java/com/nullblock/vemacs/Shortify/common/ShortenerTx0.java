package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write("url=" + toshort);
	        wr.flush();
	        
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            String s = "";
            while ((line = rd.readLine()) != null) {
            	s += line;
            }
            return s.split("CCC>")[1].split("</td>")[0];
		} catch (MalformedURLException ex) {
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via tx0.org: "
					+ ex.getMessage());
		} catch (ArrayIndexOutOfBoundsException ex) {
			throw new ShortifyException("Unable to shorten via tx0.org: Input returned was not expected!");
		}
		throw new ShortifyException("Unable to shorten via tx0.org: We shouldn't get here!");
	}

}
