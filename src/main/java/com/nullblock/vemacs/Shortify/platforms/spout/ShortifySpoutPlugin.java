package com.nullblock.vemacs.Shortify.platforms.spout;

import org.spout.api.Spout;
import org.spout.api.UnsafeMethod;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.event.Listener;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.PluginCommon;

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
