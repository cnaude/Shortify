package com.nullblock.vemacs.ShortifyTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nullblock.vemacs.Shortify.common.GenericShortifyListener;
import com.nullblock.vemacs.Shortify.common.ShortenerIsGd;
import com.nullblock.vemacs.Shortify.common.ShortifyException;

public class EventHandlerTest {

	@Test
	public void messageTest() {
		System.out.println("--- Event handler testing");
		System.out.println("Message with URL:");
		String msg = "";
		String msge = "";
		// With is.gd, attempt shortening a message.
		try {
			msge = "This is a sample text and should point to a Google query for Shortify: http://www.google.com/search?q=Shortify";
			msg = GenericShortifyListener.shortenAll(msge, 1, new ShortenerIsGd());
			if(!msg.startsWith("This is a sample text and should point to a Google query for Shortify: http://is.gd/")) {
				System.out.println("Expected message mismatch!");
				System.out.println("Got:");
				System.out.println(msg);
				System.out.println("Original:");
				System.out.println(msge);
				fail("Message didn't shorten! (Bug?)");
			}
		} catch (ShortifyException e) {
			fail("Couldn't shorten message!"
					+ " is.gd returned an error: " + e.getMessage());
		}
		System.out.println(msge + " -> " + msg);
		System.out.println("Message without URL:");
		// Try shortening a message without a URL in it.
		try {
			msge = "This is a sample text without any URLs.";
			msg = GenericShortifyListener.shortenAll(msge, 1, new ShortenerIsGd());
			if(!msg.equals(msge)) {
				System.out.println("Expected message mismatch!");
				System.out.println("Got:");
				System.out.println(msg);
				System.out.println("Original:");
				System.out.println(msge);
				fail("Message not expected! (Bug?)");
			}
		} catch (ShortifyException e) {
			fail("Couldn't shorten message!"
					+ " is.gd returned an error: " + e.getMessage());
		}
		System.out.println(msge + " -> " + msg);
	}

}
