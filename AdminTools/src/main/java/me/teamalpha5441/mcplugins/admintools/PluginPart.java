package me.teamalpha5441.mcplugins.admintools;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginPart {

	public abstract void onEnable(JavaPlugin plugin);

	public void onDisable(JavaPlugin plugin) { }
}
