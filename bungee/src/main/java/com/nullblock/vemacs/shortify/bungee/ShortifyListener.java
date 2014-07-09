package com.nullblock.vemacs.shortify.bungee;

import com.nullblock.vemacs.shortify.common.Shortener;
import com.nullblock.vemacs.shortify.common.ShortifyException;
import com.nullblock.vemacs.shortify.util.ShortifyUtility;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.logging.Level;

public class ShortifyListener implements Listener {
    private final Shortify plugin;

    public ShortifyListener(Shortify plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer))
            return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if ((!event.isCommand() && player.hasPermission("shortify.shorten"))
                || (event.isCommand() && player.hasPermission("shortify.shorten.cmd"))) {
            final Shortener shortener = Shortify.getShortenerManager().getShortener(Shortify.getConfiguration().getString("shortener", "isgd"));
            try {
                event.setMessage(ShortifyUtility.shortenAll(
                        event.getMessage(),
                        Integer.valueOf(Shortify.getConfiguration().getString(
                                "minlength", "20")),
                        shortener,
                        Shortify.getConfiguration().getString("prefix")));
            } catch (NumberFormatException e1) {
                plugin.getLogger().warning("Your config.yml is invalid: minlength is not a number or invalid.");
            } catch (ShortifyException e1) {
                plugin.getLogger().log(Level.SEVERE, "Unable to shorten link for " + player.getName(), e1);
            }
        }
    }
}
