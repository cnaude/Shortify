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
import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortenerBitLy;
import com.nullblock.vemacs.Shortify.common.ShortenerGooGl;
import com.nullblock.vemacs.Shortify.common.ShortenerIsGd;
import com.nullblock.vemacs.Shortify.common.ShortenerNigGr;
import com.nullblock.vemacs.Shortify.common.ShortenerPasteDebianNet;
import com.nullblock.vemacs.Shortify.common.ShortenerTinyUrl;
import com.nullblock.vemacs.Shortify.common.ShortenerTurlCa;
import com.nullblock.vemacs.Shortify.common.ShortenerTx0;
import com.nullblock.vemacs.Shortify.common.ShortenerYourls;
import com.nullblock.vemacs.Shortify.common.ShortenerYu8Me;
import com.nullblock.vemacs.Shortify.common.ShortifyException;

/**
 * @author tux-amd64
 * 
 */
public class UrlShortenerTest {

	private CommonConfiguration c;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void noConfigurationTest() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		// For each URL shortener supported without special configuration,
		// attempt to shorten the BukkitDev page.
		System.out.println("---- No specially configured shorteners");
		Class[] shorteners = { ShortenerIsGd.class, ShortenerTinyUrl.class,
				ShortenerTurlCa.class, ShortenerTx0.class, ShortenerYu8Me.class, ShortenerNigGr.class };
		for (int i = 0; i < shorteners.length; i++) {
			// Attempt to shorten
			doShortenerTest((Shortener) shorteners[i].getConstructor()
					.newInstance());
		}
	}

	@Test
	public void configuredUrlShorteners() {
		System.out.println("---- Specially configured shorteners");
		String bu = c.getString("bitlyUSER", "none");
		String ba = c.getString("bitlyAPI", "none");
		boolean bypass = false;
		if (bu.equals("none")) {
			System.out.println("No bit.ly user specified.");
			bypass = true;
		}
		if (ba.equals("none")) {
			System.out.println("No bit.ly API key specified.");
			bypass = true;
		}
		if (!bypass) {
			doShortenerTest(new ShortenerBitLy(bu, ba));
		}
		bypass = false;
		String ga = c.getString("googAPI", "none");
		if (ga.equals("none")) {
			System.out.println("No goo.gl API key specified.");
			bypass = true;
		}
		if (!bypass) {
			doShortenerTest(new ShortenerGooGl(ga));
		}
		bypass = false;
		String yur = c.getString("yourlsURI", "none");
		String yu = c.getString("yourlsUSER", "none");
		String yp = c.getString("yourlsPASS", "none");
		if (yur.equals("none")) {
			System.out.println("No YOURLS API URL specified.");
			bypass = true;
		}
		if (yu.equals("none")) {
			System.out.println("No YOURLS username specified.");
			bypass = true;
		}
		if (yp.equals("none")) {
			System.out.println("No YOURLS password specified.");
			bypass = true;
		}
		if (!bypass) {
			doShortenerTest(new ShortenerYourls(yur, yu, yp));
		}
	}

	private void doShortenerTest(Shortener shortener) {
		try {
			System.out
					.println("Shortener "
							+ shortener.getClass().getName()
							+ ": "
							+ shortener
									.getShortenedUrl("http://dev.bukkit.org/server-mods/shortify/"));
		} catch (ShortifyException e) {
			fail("Shortener " + shortener.getClass().getName()
					+ " returned an error: " + e.getMessage());
		}
	}

}
