package me.teamalpha5441.mcplugins.realtime;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RealTime extends JavaPlugin implements Listener, Runnable {

	private int taskID;
	private TimeCorrelator timeCorrelator;
	private List<String> worldNames;
	private ArrayList<World> worlds;

	public void loadWorld(World world) {
		world.setGameRuleValue("doDaylightCycle", "false");
		this.worlds.add(world);
	}

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		this.worldNames = getConfig().getStringList("worlds");
		this.worlds = new ArrayList<World>();
		if (this.worldNames != null) {
			for (String worldName : this.worldNames) {
				World world = getServer().getWorld(worldName);
				if (world != null) {
					loadWorld(world);
				}
			}
		} else {
			throw new RuntimeException("Couldn't retrieve worlds from config");
		}

		String timeZoneString = getConfig().getString("timezone");
		TimeZone timeZone = null;
		if (timeZoneString != null) {
			timeZone = TimeZone.getTimeZone(timeZoneString);
		}

		String correlatorType = getConfig().getString("correlator-type", "simple");
		if (correlatorType.equals("simple")) {
			this.timeCorrelator = new SimpleTimeCorrelator(timeZone);
		} else if (correlatorType.equals("complex")) {
			throw new RuntimeException("ComplexTimeCorrelator not yet supported");
		}

		int taskDelay = getConfig().getInt("task-delay", 72);
		this.taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, this, 0, taskDelay);
		if (this.taskID < 0) {
			throw new RuntimeException("Couldn't start task");
		}
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTask(this.taskID);
	}

	@EventHandler
	public void onWorldLoadEvent(WorldLoadEvent evt) {
		World world = evt.getWorld();
		if (!this.worlds.contains(world) && this.worldNames.contains(world.getName())) {
			loadWorld(world);
		}
	}

	@EventHandler
	public void onWorldUnloadEvent(WorldUnloadEvent evt) {
		this.worlds.remove(evt.getWorld());
	}

	@Override
	public void run() {
		for (World world : worlds) {
			world.setFullTime(this.timeCorrelator.getMinecraftTime());
		}
	}
}
