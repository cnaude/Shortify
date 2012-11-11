package com.nullblock.vemacs.Shortify;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ShortifyListener implements Listener {
 
    private Shortify plugin;
 
    public ShortifyListener(Shortify Shortify) {
        plugin = Shortify;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
 
    private Shortener getShortener() {
    	String service = plugin.getConfig().getString("shortener");
    	if(service.equals("googl")) {
    		return new ShortenerGooGl(plugin.getConfig().getString("googAPI"));
    	}
    	if(service.equals("bitly")) {
    		return new ShortenerBitLy(plugin.getConfig().getString("bitlyUSER"), plugin.getConfig().getString("bitlyAPI"));
    	}
    	if(service.equals("tinyurl")) {
    		return new ShortenerTinyUrl();
    	}
    	if(service.equals("turlca")) {
    		return new ShortenerTurlCa();
    	}
    	else {
    		return new ShortenerIsGd();
    	}
    }
    
        @EventHandler(priority = EventPriority.LOW)
        public void playerChat(AsyncPlayerChatEvent e){
    		String message = e.getMessage();
    		if(message.contains("http://") || message.contains("https://")) {       		    
    		    Pattern p = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?������]))");  
    		    Matcher m = p.matcher(message);  
    		    StringBuffer sb = new StringBuffer();
    		    String urlTmp = "";
    	    	String minlength = plugin.getConfig().getString("minlength");
    		    int min = Integer.parseInt(minlength);
    		    while (m.find())  
    		    {
    		      try {
    		    	  urlTmp = m.group(1);
    		    	  if(urlTmp.length() > min) {
    		    		  urlTmp = getShortener().getShortenedUrl(urlTmp);
    		    	  }
    		      } catch (ShortifyException e1) {
    		    	  Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Warning: "+e1.getMessage());
    		      }
    		      m.appendReplacement(sb, "");  
    		      sb.append(urlTmp);  
    		    }
    		    m.appendTail(sb);  
    		    e.setMessage(sb.toString());
    		}
        }
}
