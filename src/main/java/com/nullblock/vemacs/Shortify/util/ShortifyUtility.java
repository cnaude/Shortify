package com.nullblock.vemacs.Shortify.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.MetricsImpl.Graph;
import com.nullblock.vemacs.Shortify.util.MetricsImpl.Plotter;

public class ShortifyUtility {
	public static void setupMetrics(MetricsImpl metrics, CommonConfiguration cc) throws IOException {
		// Cause I won't live and die
		// For the part with a dirty CD-i
		Graph g = metrics.createGraph("URL Shortener");
		g.addPlotter(new Plotter(cc.getString("shortener", "isgd")) {
			@Override
			public int getValue() {
				return 1;
			}
		});
		metrics.start();
	}
	
	public static BufferedReader getUrl(String toread) throws IOException {
		return new BufferedReader(new InputStreamReader(
				new URL(toread).openStream()));
	}

	public static String getUrlSimple(String uri, String srv)
			throws ShortifyException {
		String inputLine = null;
		try {
			BufferedReader in = getUrl(uri);
			String s = "";
			while ((inputLine = in.readLine()) != null)
				s += inputLine;
			in.close();
			return s;
		} catch (IOException ex) {
			throw new ShortifyException("Unable to shorten via " + srv + ": "
					+ ex.getMessage());
		}
	}
}
