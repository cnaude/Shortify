package com.nullblock.vemacs.Shortify.platforms.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Server;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class ShortifyClassicThread extends Thread {

	private CommonConfiguration cc;
	private Server s;
	private String m;
	
	public ShortifyClassicThread(CommonConfiguration c, Server se, String msg) {
		c = cc;
		s = se;
		m = msg;
	}
	
	@Override
	public void run() {
		try {
			s.broadcastMessage(ShortifyUtility.classicUrlShorten(
					m,
					Integer.valueOf(cc.getString(
							"minlength")), ShortifyUtility.getShortener(cc)));
		} catch (NumberFormatException e) {
			s.getConsoleSender().sendMessage(
					ChatColor.RED
							+ "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
		} catch (ShortifyException e) {
			s.getConsoleSender().sendMessage(
					ChatColor.RED + "Warning: " + e.getMessage());
		}
	}

}
