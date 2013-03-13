package com.nullblock.vemacs.Shortify.platforms.spout;

import java.util.logging.Logger;

import org.spout.api.plugin.CommonPlugin;
import org.spout.api.scheduler.TaskPriority;
import org.spout.api.event.Listener;

import com.nullblock.vemacs.Shortify.common.CommonConfiguration;
import com.nullblock.vemacs.Shortify.common.PluginCommon;
import com.nullblock.vemacs.Shortify.common.ShortifyCommonPlugin;
import com.nullblock.vemacs.Shortify.util.Metrics;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
import com.nullblock.vemacs.Shortify.util.Updater;
import com.nullblock.vemacs.Shortify.util.Updater.UpdateResult;

public class ShortifySpoutPlugin extends CommonPlugin implements ShortifyCommonPlugin {

	private CommonConfiguration c;
	private Listener listener;

	@Override
	public void onDisable() {
		c = null;
		listener = null;
		getLogger().info("Shortify disabled!");
	}

	@Override
	public void onEnable() {
		c = PluginCommon.loadCfg(this.getFile());
		PluginCommon.verifyConfiguration(c, getLogger());
		try {
			ShortifyUtility.setupMetrics(new Metrics(getDescription().getName(), getDescription().getVersion(), this.getFile().getParentFile(), this), c);
			getLogger().info("Metrics setup.");
		} catch (Exception e) {
			getLogger().warning("Unable to set up Metrics.");
		}
		listener = new ShortifySpoutListener(this);
		getEngine().getEventManager().registerEvents(listener, this);
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

	@Override
	public Logger getLog() {
		return getLogger();
	}

	@Override
	public int scheduleTaskRepeating(Runnable r, long i, long d) {
		return this.getEngine().getScheduler().scheduleSyncRepeatingTask(this, r, i, d, TaskPriority.NORMAL).getTaskId();
	}

	@Override
	public void cancelTask(int t) {
		getEngine().getScheduler().cancelTask(t);
	}

	@Override
	public String serverInfo() {
		// As Spout does not have a notation of "Online Mode", we will assume that yes, we're in online mode.
		return "true&" + getEngine().getVersion() + "&" + getEngine().getAllPlayers().size();
	}

}
