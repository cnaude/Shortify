package com.nullblock.vemacs.Shortify;

/**
 * URL shortener standard interface to handle using a URL shortening service to
 * shorten a URL.
 * 
 * @author minecrafter
 */
public interface Shortener {
	/**
	 * Shorten a URL.
	 * 
	 * @param toshort A long URL to shorten
	 * @returns a String which is the shortened URL
     * @throws ShortifyException for errors encountered
	 */
	public String getShortenedUrl(String toshort) throws ShortifyException;
}
