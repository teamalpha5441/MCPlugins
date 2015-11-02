package me.teamalpha5441.mcplugins.tamerge;

import org.bukkit.plugin.java.JavaPlugin;

public class TAMerge extends JavaPlugin {

	public int cfgCostMerge = 0;
	public int cfgCostExtract = 0;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}
	
	@Override
	public void onEnable() {
		cfgCostMerge = getConfig().getInt("cost.merge", cfgCostMerge);
		cfgCostExtract = getConfig().getInt("cost.extract", cfgCostExtract);
		
		getCommand("merge").setExecutor(new MergeCommand(this));
		getCommand("extract").setExecutor(new ExtractCommand(this));
	}
}
