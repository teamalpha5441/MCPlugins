package me.teamalpha5441.mcplugins.realtime;

import java.util.Calendar;
import java.util.List;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RealTime extends JavaPlugin implements Runnable {

	private int taskID;
	private World[] worlds;
	
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
	}
	
	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTask(this.taskID);
	}

	@Override
	public void run() {
		Calendar calendar = Calendar.getInstance();
		float realHours = calendar.get(Calendar.HOUR_OF_DAY);
		realHours += calendar.get(Calendar.MINUTE) / 60f;
		realHours += calendar.get(Calendar.SECOND) / 3600f;
		int mcTime = (int)(realHours * 1000 + 0.5f) - 6000;
		if (mcTime < 0) {
			mcTime += 24000;
		}
		
		for (World world : worlds) {
			world.setFullTime(mcTime);
		}
	}
}
