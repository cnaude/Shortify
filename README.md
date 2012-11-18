Shortify
========

The most advanced URL shortener for Bukkit. A fast and configurable plugin that automatically uses goo.gl, bit.ly, TinyURL, turl.ca (experimental) or is.gd to shorten all detected URLs in a player message.

Configuration
-------------

The configuration can be found in `plugins/Shortify/config.yml`.

Permissions
-----------

Users will need `shortify.shorten` in order to shorten URLs. This is granted by default to all users.

Authors
-------

* vemacs - creator, lead
* tuxed/minecrafter - co-maintainer, optimizer, tester
* Racorac - maintainer, the guy who started this whole thing

For Developers
-------

If you would like to implement a URL shortener, you should create your own class and implement the `Shortener` interface.

If the service requires any log ins, you are required to not provide any defaults and add a configuration option for it instead.
