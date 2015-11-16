package me.teamalpha5441.mcplugins.prefixer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixer extends JavaPlugin implements Listener {

	private PrefixManager prefixManager;
	
	@Override
	public void onEnable() {
		this.prefixManager = new PrefixManager();
		for (Player player : getServer().getOnlinePlayers()) {
			this.prefixManager.managePlayer(player);
		}
		getServer().getServicesManager().register(PrefixManager.class, this.prefixManager, this, ServicePriority.Normal);
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		getServer().getServicesManager().unregister(this.prefixManager);
		this.prefixManager = null;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent evt) {
		this.prefixManager.managePlayer(evt.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent evt) {
		this.prefixManager.unmanagePlayer(evt.getPlayer());
	}
}
