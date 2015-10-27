package me.teamalpha5441.mcplugins.admintools.hide;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HideListener implements Listener {

	private final HidePluginPart pluginPart;
	
	public HideListener(HidePluginPart pluginPart) {
		this.pluginPart = pluginPart;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player joiningPlayer = evt.getPlayer();
		for (UUID hiddenPlayer : pluginPart._HiddenPlayers) {
			joiningPlayer.hidePlayer(Bukkit.getPlayer(hiddenPlayer));
		}
	}
}
