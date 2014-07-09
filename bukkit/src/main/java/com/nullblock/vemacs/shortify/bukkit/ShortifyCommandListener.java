package com.nullblock.vemacs.shortify.bukkit;

import com.nullblock.vemacs.shortify.common.Shortener;
import com.nullblock.vemacs.shortify.common.ShortifyException;
import com.nullblock.vemacs.shortify.util.ShortifyUtility;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ShortifyCommandListener implements Listener {

    private Shortify plugin;

    public ShortifyCommandListener(Shortify pl) {
        plugin = pl;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void playerCommand(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().hasPermission("shortify.shorten.cmd")) {
            try {
                e.setMessage(ShortifyUtility.shortenAll(
                        e.getMessage(),
                        Integer.valueOf(Shortify.getConfiguration().getString("minlength", "20")),
                        Shortify.getShortenerManager().getShortener(Shortify.getConfiguration().getString("shortener", "isgd")), ""));

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
