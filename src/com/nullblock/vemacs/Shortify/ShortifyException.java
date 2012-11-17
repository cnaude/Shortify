package com.nullblock.vemacs.Shortify;

import org.bukkit.Bukkit;


public class ShortifyException extends Exception {

	private static final long serialVersionUID = 4584407356148298247L;
	String msg = "";
	
	public ShortifyException(String string) {
		Bukkit.broadcastMessage(string);
	}

	public String getMessage() {
		return msg;
	}
	
}
