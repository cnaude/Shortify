/**
 * 
 */
package com.nullblock.vemacs.ShortifyTests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.ShortenerManager;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

/**
 * @author tux-amd64
 * 
 */
public class UrlShortenerTest {

	private CommonConfiguration c;
	private ShortenerManager sm;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		c = new CommonConfiguration();
		c.addDefault("googAPI", "none");
		c.addDefault("bitlyUSER", "none");
		c.addDefault("bitlyAPI", "none");
		c.addDefault("yourlsURI", "none");
		c.addDefault("yourlsUSER", "none");
		c.addDefault("yourlsPASS", "none");
		try {
			c.loadProperties(new File("ShortifyTests.properties"));
		} catch (FileNotFoundException e) {
			System.out
					.println("Couldn't load ShortifyTests.properties, creating...");
			try {
				c.dumpProperties(new File("ShortifyTests.properties"));
			} catch (IOException e1) {
				System.out
						.println("Couldn't dump ShortifyTests.properties due to other I/O error:");
				e1.printStackTrace();
				return;
			}
			System.out
					.println("Created. Edit this file to test other URL shorteners.");
			return;
		} catch (IOException e) {
			System.out
					.println("Couldn't load ShortifyTests.properties due to other I/O error:");
			e.printStackTrace();
			return;
		}
		sm = ShortifyUtility.setupShorteners();
		sm = ShortifyUtility.reloadConfigShorteners(sm, c);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void noConfigurationTest() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		// For each URL shortener supported without special configuration,
		// attempt to shorten the BukkitDev page.
		String[] shorteners = sm.list();
		for (int i = 0; i < shorteners.length; i++) {
			// Attempt to shorten
			doShortenerTest(shorteners[i]);
		}
	}

	private void doShortenerTest(String s) {
		try {
			System.out.println("Shortener "
							+ s
							+ ": "
							+ sm.getShortener(s).
								getShortenedUrl("http://dev.bukkit.org/server-mods/shortify/"));
		} catch (ShortifyException e) {
			if (e.getMessage().startsWith("No API")) {
				System.out.println("Shortener " + s + " is misconfigured: " + e.getMessage());
				return;
			}
			fail("Shortener " + s
					+ " (really " + sm.getShortener(s).getClass().getName() +
					") returned an error: " + e.getMessage());
		}
	}

}
