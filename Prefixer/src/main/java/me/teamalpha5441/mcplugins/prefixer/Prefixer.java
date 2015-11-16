package me.teamalpha5441.mcplugins.prefixer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixer extends JavaPlugin implements Listener {

	private static PrefixManager prefixManager;
	
	static PrefixManager getPrefixManager() {
		return Prefixer.prefixManager;
	}
	
	@Override
	public void onEnable() {
		Prefixer.prefixManager = new PrefixManager();
		for (Player player : getServer().getOnlinePlayers()) {
			Prefixer.prefixManager.managePlayer(player);
		}
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		Prefixer.prefixManager = null;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent evt) {
		Prefixer.prefixManager.managePlayer(evt.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		Prefixer.prefixManager.unmanagePlayer(evt.getPlayer());
	}
}
