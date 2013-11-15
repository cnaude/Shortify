package com.nullblock.vemacs.Shortify.common;

/**
 * This is the standard interface used by Shortify to shorten URLs.
 *
 * @author minecrafter
 * @since 1.3.0
 */
public interface Shortener {
    /**
     * Shorten a URL.
     *
     * @param toshort A long URL to shorten
     * @return a shortened URL
     * @throws ShortifyException for errors encountered
     */
    public String getShortenedUrl(String toshort) throws ShortifyException;
}
