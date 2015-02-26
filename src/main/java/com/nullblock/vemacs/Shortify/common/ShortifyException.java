package com.nullblock.vemacs.Shortify.common;

/**
 * ShortifyException is the general exception thrown by Shortify, mostly those
 * classes implementing the {@link Shortener} interface, and may possibly expand
 * to all of Shortify itself.
 * 
 * If thrown by classes implementing the Shortener interface, it should only
 * when an HTTP or API error occurs with the shortener.
 *
 * @author minecrafter
 * @since 1.3.0
 */

public class ShortifyException extends RuntimeException {

    public ShortifyException() {
        super();
    }

    public ShortifyException(String message) {
        super(message);
    }

    public ShortifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShortifyException(Throwable cause) {
        super(cause);
    }

    protected ShortifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
