package com.nullblock.vemacs.Shortify.bukkit;

/**
 * Listener for Shortify
 * By vemacs, some parts contributed by minecrafter
 *
 */

import com.google.common.collect.ImmutableSet;
import com.nullblock.vemacs.Shortify.common.Shortener;
import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ShortifyListener implements Listener {

    private Shortify plugin;

    public ShortifyListener(Shortify Shortify) {
        plugin = Shortify;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("shortify.shorten")) {
            final Shortener shortener = ShortifyUtility.getShortener(Shortify.getConfiguration());
            try {
                if (Shortify.getConfiguration().getString("mode", "replace")
                        .equals("replace")) {
                    event.setMessage(ShortifyUtility.shortenAll(
                            event.getMessage(),
                            Integer.valueOf(Shortify.getConfiguration().getString(
                                    "minlength", "20")),
                            shortener,
                            Shortify.getConfiguration().getString("prefix")));
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

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsyncPlayerChatMonitor(final AsyncPlayerChatEvent event) {
        if (Shortify.getConfiguration().getString("mode", "replace")
                .equals("classic") && event.getPlayer().hasPermission("shortify.shorten")) {
            final Shortener shortener = ShortifyUtility.getShortener(Shortify.getConfiguration());
            final Set<Player> recipients = ImmutableSet.copyOf(event.getRecipients());
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    try {
                        final String newm = ShortifyUtility.classicUrlShorten(
                                event.getMessage(), Integer.valueOf(Shortify.getConfiguration().getString("minlength", "20")),
                                shortener);
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                            @Override
                            public void run() {
                                for (Player player : recipients)
                                    player.sendMessage(newm);
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
    }
}
