package com.nullblock.vemacs.Shortify.common;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

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
		CommonConfiguration c = new CommonConfiguration();
		c.addDefault("mode", "replace");
		c.addDefault("shortener", "isgd");
		c.addDefault("auto-update", "true");
		c.addDefault("prefix", "&n");
		c.addDefault("minlength", "20");
		c.addDefault("googAPI", "none");
		c.addDefault("bitlyUSER", "none");
		c.addDefault("bitlyAPI", "none");
		c.addDefault("yourlsURI", "none");
		c.addDefault("yourlsUSER", "none");
		c.addDefault("yourlsPASS", "none");

		File dataDir = new File(pl.getParent() + "/Shortify");
		File cfg = new File(dataDir, "config.yml");
		try {
			dataDir.mkdirs();
			if (!cfg.exists()) {
				cfg.createNewFile();
				c.dumpYaml(cfg);
			} else {
				c.loadYaml(cfg);
				c.mergeDefaults();
				c.dumpYaml(cfg);
			}
		} catch (IOException e) {
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
