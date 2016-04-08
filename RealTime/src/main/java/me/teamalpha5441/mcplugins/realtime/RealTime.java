package me.teamalpha5441.mcplugins.realtime;

import java.util.List;
import java.util.TimeZone;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RealTime extends JavaPlugin implements Runnable {

	private int taskID;
	private World[] worlds;
	private TimeCorrelator timeCorrelator;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		int taskDelay = getConfig().getInt("task-delay", 72);
		this.taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, this, 0, taskDelay);
		if (this.taskID < 0) {
			throw new RuntimeException("Couldn't start task");
		}

		List<String> strWorlds = getConfig().getStringList("worlds");
		if (strWorlds != null) {
			this.worlds = new World[strWorlds.size()];
			for (int i = 0; i < worlds.length; i++) {
				this.worlds[i] = getServer().getWorld(strWorlds.get(i));
				this.worlds[i].setGameRuleValue("doDaylightCycle", "false");
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
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTask(this.taskID);
	}

	@Override
	public void run() {
		for (World world : worlds) {
			world.setFullTime(this.timeCorrelator.getMinecraftTime());
		}
	}
}
