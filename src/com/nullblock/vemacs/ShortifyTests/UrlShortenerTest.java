/**
 * 
 */
package com.nullblock.vemacs.ShortifyTests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nullblock.vemacs.Shortify.*;

/**
 * @author tux-amd64
 *
 */
public class UrlShortenerTest {

	private Shortener shortener;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// For each URL shortener supported without special configuration, attempt to shorten the BukkitDev page.
		Class[] shorteners = {ShortenerIsGd.class, ShortenerTinyUrl.class, ShortenerTurlCa.class};
		for(int i=0;i<shorteners.length;i++) {
			// Attempt to shorten
			shortener = (Shortener) shorteners[i].getConstructor().newInstance();
			try {
				System.out.println("Shortener "+shorteners[i].getName() + " (no encoding): " + shortener.getShortenedUrl("http://dev.bukkit.org/server-mods/shortify/"));
				System.out.println("Shortener "+shorteners[i].getName() + " (with encoding): " + shortener.getShortenedUrl("http://dev.bukkit.org/server-mods/shortify/#encodingtest"));
			} catch (ShortifyException e) {
				fail("Shortener "+shorteners[i].getName() + " returned an error: "+e.getMessage());
			}
		}
	}

}
