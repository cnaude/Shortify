package com.nullblock.vemacs.Shortify.util;

import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;
import com.nullblock.vemacs.Shortify.bukkit.Shortify;
import com.nullblock.vemacs.Shortify.common.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortifyUtility {

    private static final Pattern URL_PATTERN = Pattern
            .compile("((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)");

    public static ShortenerManager setupShorteners() {
        ShortenerManager sm = new ShortenerManager();
        sm.registerShortener("isgd", new ShortenerIsGd());
        sm.registerShortener("niggr", new ShortenerNigGr());
        sm.registerShortener("safemn", new ShortenerSafeMn());
        sm.registerShortener("tinyurl", new ShortenerTinyUrl());
        sm.registerShortener("yu8me", new ShortenerYu8Me());
        return sm;
    }

    public static void reloadConfigShorteners(ShortenerManager sm, CommonConfiguration c) {
        sm.unregisterShortener("bitly");
        sm.registerShortener("bitly", new ShortenerBitLy(
                c.getString("bitlyUSER"), c.getString("bitlyAPI")));
        sm.unregisterShortener("yourls");
        sm.registerShortener("yourls", new ShortenerYourls(
                c.getString("yourlsURI"), c.getString("yourlsUSER"),
                c.getString("yourlsPASS")));
        sm.unregisterShortener("googl");
        sm.registerShortener("googl", new ShortenerGooGl(
                c.getString("googAPI")));
    }

    public static String getUrlSimple(String uri)
            throws ShortifyException {
        try (InputStream is = new URL(uri).openStream()) {
            return new String(ByteStreams.toByteArray(is));
        } catch (IOException ex) {
            throw new ShortifyException("Unable to fetch URL", ex);
        }
    }

    /**
     * Shorten all URLs in a String.
     *
     * @param txt
     * @param minln
     * @param shortener
     * @param prefix
     * @return
     * @throws ShortifyException
     */
    public static String shortenAll(String txt, int minln,
            Shortener shortener, String prefix) throws ShortifyException {
        // From Daring Fireball
        prefix = replaceColors(prefix);
        Matcher m = URL_PATTERN.matcher(txt);

        // TODO Replace this with StringBuilder
        StringBuffer sb = new StringBuffer();
        String urlTmp;
        while (m.find()) {
            urlTmp = m.group(1);
            if (urlTmp.length() >= minln) {
                try {
                    if (!prefix.equals("")) {
                        urlTmp = prefix
                                + shortener.getShortenedUrl(java.net.URLEncoder
                                        .encode(urlTmp, "UTF-8"))
                                + replaceColors("&r");
                    } else {
                        urlTmp = shortener.getShortenedUrl(java.net.URLEncoder
                                .encode(urlTmp, "UTF-8"));
                    }
                    // might as well put the encoder in the listener to
                    // prevent possible injections
                } catch (UnsupportedEncodingException e1) {
                    // do absolutely nothing
                }
            } else {
                if (!prefix.equals("")) {
                    urlTmp = prefix + urlTmp + replaceColors("&r");
                }
            }
            m.appendReplacement(sb, "");
            sb.append(urlTmp);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     *
     * @param config
     * @return
     */
    public static Shortener getShortener(CommonConfiguration config) {
        return Shortify.getShortenerManager().getShortener(config.getString("shortener"));
    }

    public static String classicUrlShorten(String message, int minln,
            Shortener shortener) throws ShortifyException {
        Matcher m = URL_PATTERN.matcher(message);
        String urlTmp;
        List<String> urls = new ArrayList<>();
        while (m.find()) {
            urlTmp = m.group(1);
            if (urlTmp.length() > minln) {
                try {
                    urls.add(shortener.getShortenedUrl(java.net.URLEncoder
                            .encode(urlTmp, "UTF-8")));
                    // might as well put the encoder in the listener to
                    // prevent possible injections
                } catch (UnsupportedEncodingException e1) {
                    // do absolutely nothing
                }
            }
        }
        return "The following URLs were shortened: " + Joiner.on(", ").join(urls);
    }

    public static String replaceColors(String text) {
        // copied from
        // http://forums.bukkit.org/threads/simple-colors-parsing-method.32058/#post-1251988
        char[] chrarray = text.toCharArray();

        for (int index = 0; index < chrarray.length; index++) {
            char chr = chrarray[index];
            if (chr != '&') {
                continue;
            }

            if ((index + 1) == chrarray.length) {
                break;
            }
            char forward = chrarray[index + 1];
            if ((forward >= '0' && forward <= '9')
                    || (forward >= 'a' && forward <= 'f')
                    || (forward >= 'k' && forward <= 'r')) {
                chrarray[index] = '\u00A7';
            }
        }
        return new String(chrarray);
    }

    public static void verifyConfiguration(CommonConfiguration config, Logger logger) {
        if (!(config.getString("mode").equals("replace") || config.getString("mode")
                .equals("classic"))) {
            logger.info("Mode not configured correctly!");
            logger.info("Reverting to replace mode.");
            config.set("mode", "replace");
        }
        if (config.getString("shortener").equals("bitly")
                && (config.getString("bitlyUSER").equals("none") || config.getString(
                        "bitlyAPI").equals("none"))) {
            logger.info("bit.ly is not properly configured in config.yml.");
            logger.info("Reverting to default shortener is.gd.");
            config.set("shortener", "isgd");
        }
        if (config.getString("shortener").equals("yourls")
                && (config.getString("yourlsUSER").equals("none")
                || config.getString("yourlsURI").equals("none") || config
                .getString("yourlsPASS").equals("none"))) {
            logger.info("YOURLS is not properly configured in config.yml.");
            logger.info("Reverting to default shortener is.gd.");
            config.set("shortener", "isgd");
        }
        if (config.getString("shortener").equals("googl")
                && config.getString("googAPI").equals("none")) {
            logger.info("goo.gl is not properly configured in config.yml.");
            logger.info("Reverting to default shortener is.gd.");
            config.set("shortener", "isgd");
        }
    }

    public static CommonConfiguration loadCfg(File file) {
        CommonConfiguration c = new CommonConfiguration();
        c.addDefault("mode", "replace");
        c.addDefault("shortener", "isgd");
        c.addDefault("prefix", "&n");
        c.addDefault("minlength", "20");
        c.addDefault("googAPI", "none");
        c.addDefault("bitlyUSER", "none");
        c.addDefault("bitlyAPI", "none");
        c.addDefault("yourlsURI", "none");
        c.addDefault("yourlsUSER", "none");
        c.addDefault("yourlsPASS", "none");

        File dataDir = new File(file.getParent());
        File cfg = new File(dataDir, "config.yml");
        try {
            dataDir.mkdirs();
            if (!cfg.exists()) {
                cfg.createNewFile();
                c.dumpYaml(cfg);
            } else {
                c.loadYaml(cfg);
                c.mergeDefaults();
                c.dumpYaml(cfg);
            }
        } catch (IOException ignored) {
        }
        return c;
    }

    public static void dumpData(File file, CommonConfiguration c) {
        File dataDir = new File(file.getParent());
        try {
            new File(dataDir, "config.yml").createNewFile();
            c.dumpYaml(new File(dataDir, "config.yml"));
        } catch (IOException e1) {
            // Ignore
        }
    }
}
