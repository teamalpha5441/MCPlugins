package me.teamalpha5441.mcplugins.takeeplevels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TAKeepLevels extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent evt) {
		if (evt.getEntity().hasPermission("takeeplevels.keep")) {
			evt.setKeepLevel(true);
			evt.setDroppedExp(0);
		}
	}
}
