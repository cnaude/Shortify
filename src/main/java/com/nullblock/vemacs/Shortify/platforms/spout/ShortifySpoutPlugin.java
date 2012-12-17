package com.nullblock.vemacs.Shortify.platforms.spout;

import org.spout.api.Spout;
import org.spout.api.UnsafeMethod;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.event.Listener;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.PluginCommon;
import com.nullblock.vemacs.Shortify.util.Updater;
import com.nullblock.vemacs.Shortify.util.Updater.UpdateResult;

public class ShortifySpoutPlugin extends CommonPlugin {

	private CommonConfiguration c;
	private Listener listener;

	@Override
	@UnsafeMethod
	public void onDisable() {
		c = null;
		listener = null;
		getLogger().info("Shortify disabled!");
	}

	@Override
	@UnsafeMethod
	public void onEnable() {
		c = PluginCommon.loadCfg(this.getFile());
		PluginCommon.verifyConfiguration(c, getLogger());
		listener = new ShortifySpoutListener(this);
		Spout.getEventManager().registerEvents(listener, this);
		if (c.getString("auto-update").equals("true")) {
			getLogger().info("Checking for updates, please wait...");
			Updater updater = new Updater(getLogger(), "Shortify", 
					this.getDescription().getVersion(), this.getFile(),
					Updater.UpdateType.DEFAULT, false);
			if (updater.getResult() == UpdateResult.SUCCESS) {
				getLogger()
						.info("An update (version "
								+ updater.getLatestVersionString()
								+ ") of Shortify was found and installed. Please restart your server to use the new version.");
			}
			if (updater.getResult() == UpdateResult.NO_UPDATE) {
				getLogger().info("No updates found.");
			}
			updater = null;
		}
		PluginCommon.dumpData(getFile(), c);
		getLogger().info("Shortify enabled.");
	}

	@Override
	@UnsafeMethod
	public void onReload() {
		// Gracefully reload our configuration
		c = PluginCommon.loadCfg(this.getFile());
		PluginCommon.verifyConfiguration(c, getLogger());
		PluginCommon.dumpData(getFile(), c);
		getLogger().info("Shortify reloaded.");
	}

	protected CommonConfiguration getConfig() {
		return c;
	}

}
