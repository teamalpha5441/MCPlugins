package me.teamalpha5441.mcplugins.chunky;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Chunky extends JavaPlugin implements Listener {

	private List<String> worlds;
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
	}
	
	@Override
	public void onEnable() {
		this.worlds = getConfig().getStringList("worlds");
		boolean debug = getConfig().getBoolean("debug", false);
		if (this.worlds != null) {
			if (this.worlds.size() == 0) {
				getLogger().log(Level.SEVERE, "No worlds configured");
			} else {
				getServer().getPluginManager().registerEvents(this, this);
			}
		} else {
			getLogger().log(Level.SEVERE, "Syntax error in configuration file");
			if (!debug) {
				getServer().getPluginManager().disablePlugin(this);
			}
		}
		if (debug) {
			getServer().getPluginManager().registerEvents(new DebugListener(getLogger()), this);
		}
	}
	
	@Override
	public void onDisable() {
		this.worlds.clear();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChunkUnload(ChunkUnloadEvent evt) {
		for (String worldName : this.worlds) {
			World world = Bukkit.getWorld(worldName);
			if (world != null) {
				if (evt.getWorld().equals(world)) {
					evt.setCancelled(true);
				}
			} else {
				getLogger().log(Level.SEVERE, "World not found: " + worldName);
			}
		}
	}
}
