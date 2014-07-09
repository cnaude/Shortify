package com.nullblock.vemacs.ShortifyTests;

import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
import org.junit.Test;

public class TestFetch {
    @Test
    public void testFetching() {
        ShortifyUtility.getUrlSimple("http://google.com");
    }
}
