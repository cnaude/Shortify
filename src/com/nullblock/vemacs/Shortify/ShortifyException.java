package com.nullblock.vemacs.Shortify;

public class ShortifyException extends Exception {

	private static final long serialVersionUID = 4584407356148298247L;
	private String msg = "";

	public ShortifyException(String string) {
		msg = string;
	}

	public String getMessage() {
		return msg;
	}

}
