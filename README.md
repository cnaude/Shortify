Shortify
========

The most advanced URL shortener for Bukkit. A fast and configurable plugin that automatically uses goo.gl, bit.ly, TinyURL, turl.ca (experimental) or is.gd to shorten all detected URLs in a player message.

Builds
------

Dev builds can be found on [Jenkins](http://ci.md-5.net/job/Shortify/)

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

If you would like to implement a URL shortener, you should create your own class and implement the `Shortener` interface, in `ShortifyUtility.java`, add a value for your class in `setupShorteners()`,
then, if needed, add config loading in `reloadConfigShorteners()`. Finally add a value in the test class.

If the service is not working ingame, but passes the tests, try decoding the URL before passing it on. This usually fixes the issue for special shorteners.

If the service requires any log ins, you are required to not provide any defaults and add a configuration option for it instead.
