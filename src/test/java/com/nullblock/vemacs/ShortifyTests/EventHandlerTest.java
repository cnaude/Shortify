package com.nullblock.vemacs.ShortifyTests;

import junit.framework.Assert;
import org.junit.Test;

import com.nullblock.vemacs.Shortify.common.ShortenerIsGd;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;

public class EventHandlerTest {

	@Test
	public void messageTest() {
        Assert.assertTrue(ShortifyUtility.shortenAll("http://google.com/search?q=shortify+bukkit+plugin", 0, new ShortenerIsGd(), "").startsWith("http://is.gd/"));
        Assert.assertTrue(ShortifyUtility.shortenAll("http://google.com/search?q=shortify+bukkit+plugin check it out", 0, new ShortenerIsGd(), "").startsWith("http://is.gd/"));
        Assert.assertTrue(ShortifyUtility.shortenAll("check this out: http://google.com/search?q=shortify+bukkit+plugin", 0, new ShortenerIsGd(), "").startsWith("check this out: http://is.gd/"));
	}

}
