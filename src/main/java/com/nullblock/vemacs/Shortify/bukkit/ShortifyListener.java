package com.nullblock.vemacs.Shortify.bukkit;

/**
 * Listener for Shortify
 * By vemacs, some parts contributed by minecrafter
 *
 */

import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ShortifyListener implements Listener {

    private Shortify plugin;

    public ShortifyListener(Shortify Shortify) {
        plugin = Shortify;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void playerChat(final AsyncPlayerChatEvent e) {
        final Shortener shortener = ShortifyUtility.getShortener(Shortify.getConfiguration());
        if (e.getPlayer().hasPermission("shortify.shorten")) {
            try {
                if (Shortify.getConfiguration().getString("mode", "replace")
                        .equals("replace")) {
                    e.setMessage(ShortifyUtility.shortenAll(
                            e.getMessage(),
                            Integer.valueOf(Shortify.getConfiguration().getString(
                                    "minlength", "20")),
                            shortener,
                            Shortify.getConfiguration().getString("prefix")));
                } else if (Shortify.getConfiguration().getString("mode", "replace")
                        .equals("classic")) {
                    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final String newm = ShortifyUtility.classicUrlShorten(
                                        e.getMessage(), Integer.valueOf(Shortify.getConfiguration().getString("minlength", "20")),
                                        shortener);
                                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                                    @Override
                                    public void run() {
                                        plugin.getServer().broadcastMessage(newm);
                                    }

                                });
                            } catch (NumberFormatException e) {
                                plugin.getServer().getConsoleSender()
                                        .sendMessage(
                                                ChatColor.RED
                                                        + "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
                            } catch (ShortifyException e) {
                                plugin.getServer().getConsoleSender().sendMessage(
                                        ChatColor.RED + "Warning: " + e.getMessage());
                            }
                        }
                    });
                }
            } catch (NumberFormatException e1) {
                plugin.getServer().getConsoleSender()
                        .sendMessage(
                                ChatColor.RED
                                        + "Warning: Your config.yml is invalid: minlength is not a number or invalid.");
            } catch (ShortifyException e1) {
                plugin.getServer().getConsoleSender().sendMessage(
                        ChatColor.RED + "Warning: " + e1.getMessage());
            }
        }
    }
}
