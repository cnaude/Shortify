package com.nullblock.vemacs.Shortify;

public class ShortifyException extends Exception {

	private static final long serialVersionUID = 4584407356148298247L;
	String msg = "";
	
	public ShortifyException(String string) {
	}

	public String getMessage() {
		return msg;
	}
	
}
