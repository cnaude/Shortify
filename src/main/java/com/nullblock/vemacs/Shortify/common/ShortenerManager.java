package com.nullblock.vemacs.Shortify.common;

import java.util.HashMap;

public class ShortenerManager {
	private HashMap<String, Shortener> shorteners = new HashMap<String, Shortener>();
	
	public boolean registerShortener(String name, Shortener shortener) {
		if (shortener == null || name == null) return false;
		if (shortenerExists(name)) return false;
		shorteners.put(name, shortener);
		return true;
	}
	
	public boolean unregisterShortener(String name) {
		if (name == null) return false;
		if (shortenerExists(name)) return false;
		shorteners.remove(name);
		return true;
	}
	
	public boolean shortenerExists(String name) {
		return shorteners.containsKey(name);
	}
	
	public Shortener getShortener(String name) {
		if (!shortenerExists(name)) {
			// This shortener doesn't exist.
			System.out.println("!!! .------------------------------------------------------. !!!");
			System.out.println("!!! | YOU HAVE AN INVALID SHORTENER IN YOUR CONFIGURATION! | !!!");
			System.out.println("!!! '------------------------------------------------------' !!!");
			System.out.println("Falling back to is.gd.");
			return new ShortenerIsGd();
		}
		return shorteners.get(name);
	}
	
	public String[] list() {
		return shorteners.keySet().toArray(new String[]{});
	}
}
