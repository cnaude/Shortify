package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class PluginCommon {

	public static void verifyConfiguration(CommonConfiguration c, Logger l) {
		if (!(c.getString("mode").equals("replace") || c.getString("mode")
				.equals("classic"))) {
			l.info("Mode not configured correctly!");
			l.info("Reverting to replace mode.");
			c.set("mode", "replace");
		}
		if (c.getString("shortener").equals("bitly")
				&& (c.getString("bitlyUSER").equals("none") || c.getString(
						"bitlyAPI").equals("none"))) {
			l.info("bit.ly is not properly configured in config.yml.");
			l.info("Reverting to default shortener is.gd.");
			c.set("shortener", "isgd");
		}
		if (c.getString("shortener").equals("yourls")
				&& (c.getString("yourlsUSER").equals("none")
						|| c.getString("yourlsURI").equals("none") || c
						.getString("yourlsPASS").equals("none"))) {
			l.info("YOURLS is not properly configured in config.yml.");
			l.info("Reverting to default shortener is.gd.");
			c.set("shortener", "isgd");
		}
		if (c.getString("shortener").equals("googl")
				&& c.getString("googAPI").equals("none")) {
			l.info("goo.gl is not properly configured in config.yml.");
			l.info("Reverting to default shortener is.gd.");
			c.set("shortener", "isgd");
		}
	}

	public static CommonConfiguration loadCfg(File pl) {
		CommonConfiguration c = new CommonConfiguration(), c3 = null;
		c.addDefault("mode", "replace");
		c.addDefault("shortener", "isgd");
		c.addDefault("auto-update", "true");
		c.addDefault("prefix", "&n");
		c.addDefault("googAPI", "none");
		c.addDefault("bitlyUSER", "none");
		c.addDefault("bitlyAPI", "none");
		c.addDefault("yourlsURI", "none");
		c.addDefault("yourlsUSER", "none");
		c.addDefault("yourlsPASS", "none");
		BufferedReader c2 = null;
		try {
			c2 = ShortifyUtility.getUrl("jar:file:" + pl.getAbsolutePath()
					+ "!/config.yml");
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try {
			c3 = new CommonConfiguration(c2);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		File dataDir = new File(pl.getParent() + "/Shortify");
		File cfg = new File(dataDir, "config.yml");
		try {
			dataDir.mkdirs();
			c = new CommonConfiguration(cfg);
			try {
				c.dumpYaml(cfg);
			} catch (IOException e1) {
				// Ignore
			}
		} catch (FileNotFoundException e) {
			c = c3;
			try {
				cfg.createNewFile();
				c.dumpYaml(cfg);
			} catch (IOException e1) {
				// Ignore
			}
		}
		return c;
	}

	public static void dumpData(File pl, CommonConfiguration c) {
		File dataDir = new File(pl.getParent() + "/Shortify");
		try {
			new File(dataDir, "config.yml").createNewFile();
			c.dumpYaml(new File(dataDir, "config.yml"));
		} catch (IOException e1) {
			// Ignore
		}
	}
}
