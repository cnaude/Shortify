package com.nullblock.vemacs.Shortify.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShortenerManager {
	private Map<String, Shortener> shorteners = new HashMap<>();
	
	public void registerShortener(String name, Shortener shortener) {
		if (shortener == null || name == null) return;
		if (shortenerExists(name)) return;
		shorteners.put(name, shortener);
    }
	
	public void unregisterShortener(String name) {
		if (name == null) return;
		if (shortenerExists(name)) return;
		shorteners.remove(name);
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
        Set<String> set = shorteners.keySet();
        return set.toArray(new String[set.size()]);
	}
}
