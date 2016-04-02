package me.teamalpha5441.mcplugins.admintools;

import org.bukkit.plugin.java.JavaPlugin;

import me.teamalpha5441.mcplugins.admintools.commands.*;
import me.teamalpha5441.mcplugins.admintools.hide.*;
import me.teamalpha5441.mcplugins.admintools.mechanics.*;

public class AdminTools extends JavaPlugin {
	
	private final HidePluginPart _HidePluginPart = new HidePluginPart();
	
	@Override
	public void onEnable() {
		getCommand("drunk").setExecutor(new DrunkCommand());
		getCommand("eat").setExecutor(new EatCommand());
		getCommand("enderchest").setExecutor(new EnderChestCommand());
		getCommand("explode").setExecutor(new ExplodeCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("freeze").setExecutor(new FreezeCommand());
		getCommand("gc").setExecutor(new GCCommand());
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("ignite").setExecutor(new IgniteCommand());
		getCommand("inventory").setExecutor(new InventoryCommand());
		getCommand("launch").setExecutor(new LaunchCommand());
		getCommand("online").setExecutor(new OnlineCommand());
		getCommand("poison").setExecutor(new PoisonCommand());
		getCommand("sword").setExecutor(new SwordCommand());
		getCommand("xreload").setExecutor(new XReloadCommand(this));
		getCommand("xstop").setExecutor(new XStopCommand(this));

		_HidePluginPart.onEnable(this);
		
		getServer().getPluginManager().registerEvents(new ColoredSigns(), this);
		getServer().getPluginManager().registerEvents(new OpenIronDoors(), this);
	}
	
	@Override
	public void onDisable() {
		_HidePluginPart.onDisable(this);
	}
}
