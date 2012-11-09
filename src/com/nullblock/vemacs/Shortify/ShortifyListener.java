package com.nullblock.vemacs.Shortify;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 
        @EventHandler(priority = EventPriority.LOW)
        public void playerChat(AsyncPlayerChatEvent e){
        		String message = e.getMessage();
        		if(message.contains("http://") || message.contains("https://")) {       		    
        		    Pattern p = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");  
        		    Matcher m = p.matcher(message);  
        		    StringBuffer sb = new StringBuffer();  
            		String service = plugin.getConfig().getString("shortener");
            		String googAPI = plugin.getConfig().getString("googAPI");
      		if(service.equals("googl")){
        		    while (m.find())  
        		    {  
        		      m.appendReplacement(sb, "");  
        		      sb.append(Shortener.GetShortedGOOGL(m.group(1), googAPI));  
        		    }  
        		    m.appendTail(sb);  
        		    e.setMessage(sb.toString());  
        		}
      			if(service.equals("tinyurl")){
        		    while (m.find())  
        		    {  
        		      m.appendReplacement(sb, "");  
        		      sb.append(Shortener.GetShortedTinyURL(m.group(1)));  
        		    }  
        		    m.appendTail(sb);  
        		    e.setMessage(sb.toString());  
        		}
            		if(service.equals("isgd")){
        		    while (m.find())  
        		    {  
        		      m.appendReplacement(sb, "");  
        		      sb.append(Shortener.GetShortedISGD(m.group(1)));  
        		    }  
        		    m.appendTail(sb);  
        		    e.setMessage(sb.toString());  
        		}
        		}
        }
}
