package me.teamalpha5441.mcplugins.chatformatter;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatFormatter extends JavaPlugin implements Listener {

	private volatile String formatString;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		this.formatString = getConfig().getString("format-string");
		if (this.formatString != null) {
			this.formatString = ChatColor.translateAlternateColorCodes('&', this.formatString);
			getServer().getPluginManager().registerEvents(this, this);
		} else {
			getLogger().log(Level.WARNING, "No format string defined in config, using default format");
		}
	}

	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent evt) {
		if (this.formatString != null) {
			evt.setFormat(this.formatString);
		}
		if (evt.getPlayer().hasPermission("chatformatter.colorcodes")) {
			evt.setMessage(ChatColor.translateAlternateColorCodes('&', evt.getMessage()));
		}
	}
}
