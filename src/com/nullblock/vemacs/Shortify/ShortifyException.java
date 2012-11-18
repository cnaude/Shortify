package com.nullblock.vemacs.Shortify;

/**
 * ShortifyException is the general exception thrown by Shortify, mostly those
 * classes implementing the {@link Shortener} interface, and may possibly expand
 * to all of Shortify itself.
 * 
 * If thrown by classes implementing the Shortener interface, it should only
 * when an HTTP or API error occurs with the shortener.
 * 
 * @since 1.3.0
 * @author minecrafter
 */

public class ShortifyException extends Exception {

	private static final long serialVersionUID = 4584407356148298247L;
	private String msg = "";

	/**
	 * Initialize the exception. A message is required.
	 * 
	 * @param message
	 *            a message to be shown by getMessage()
	 */
	public ShortifyException(String message) {
		msg = message;
	}

	/**
	 * Return the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return msg;
	}

}
