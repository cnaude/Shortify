package com.nullblock.vemacs.Shortify.common;

public class ShortenerPasteDebianNet implements Shortener {

	@Override
	public String getShortenedUrl(String toshort) throws ShortifyException {
		// Note!
		// The split()s are hacks.
		return "http://frm.li/"
				+ URLReader.getUrlSimple("http://frm.li/add/" + toshort,
						"frm.li (paste.debian.net)").split(
						"ShortURL information for short URL ")[1].split(" -")[0];
	}

}
