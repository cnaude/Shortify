package com.nullblock.vemacs.Shortify.bukkit;

import com.nullblock.vemacs.Shortify.common.ShortifyException;
import com.nullblock.vemacs.Shortify.util.ShortifyUtility;
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
                        ShortifyUtility.getShortener(Shortify.getConfiguration()), ""));

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
