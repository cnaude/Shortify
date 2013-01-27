package com.nullblock.vemacs.Shortify.common;

import java.util.logging.Logger;

public interface ShortifyCommonPlugin {
	public Logger getLog();
	public int scheduleTaskRepeating(Runnable r, long i, long d);
	public void cancelTask(int t);
	public String serverInfo();
}
