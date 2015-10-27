package me.teamalpha5441.mcplugins.cmdtrap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandTrap implements Listener {
	
	private final boolean disablePlugins;
	private final boolean disableVersion;
	private final boolean disableHelp;

	private String messagePlugins;
	private String messageVersion;
	private String messageHelp;
	
	public CommandTrap(CmdTrap cmdTrap) {
		disablePlugins = cmdTrap.getConfig().getBoolean(StaticVars.CONF_DISABLE_PLUGINS, true);
		disableVersion = cmdTrap.getConfig().getBoolean(StaticVars.CONF_DISABLE_VERSION, true);
		disableHelp = cmdTrap.getConfig().getBoolean(StaticVars.CONF_DISABLE_HELP, true);
		
		messagePlugins = cmdTrap.getConfig().getString(StaticVars.CONF_MESSAGE_PLUGINS, null);
		messageVersion = cmdTrap.getConfig().getString(StaticVars.CONF_MESSAGE_VERSION, null);
		messageHelp = cmdTrap.getConfig().getString(StaticVars.CONF_MESSAGE_HELP, null);
		
		messagePlugins = ChatColor.translateAlternateColorCodes('&', messagePlugins);
		messageVersion = ChatColor.translateAlternateColorCodes('&', messageVersion);
		messageHelp = ChatColor.translateAlternateColorCodes('&', messageHelp); 
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split("\\s+")[0].substring(1);
        Player player = event.getPlayer();
        
	    if (command.equalsIgnoreCase("pl") || command.equalsIgnoreCase("plugins")) {
	    	if (disablePlugins && !player.hasPermission(StaticVars.PERM_ALLOW_PLUGINS)) {
	    		if (messagePlugins != null) {
	    			player.sendMessage(messagePlugins);
	    		}
	    		event.setCancelled(true);
	    	}
        } else if (command.equalsIgnoreCase("ver") || command.equalsIgnoreCase("version")
        		   || command.equalsIgnoreCase("about")) {
        	if (disableVersion && !player.hasPermission(StaticVars.PERM_ALLOW_VERSION)) {
                if (messageVersion != null) {
                	player.sendMessage(messageVersion);
                }
                event.setCancelled(true);	
        	}
        } else if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("?")) {
        	if (disableHelp && !player.hasPermission(StaticVars.PERM_ALLOW_HELP)) {
        		if (messageHelp != null) {
        			player.sendMessage(messageHelp);
        		}
        		event.setCancelled(true);
        	}
        }
    }
}