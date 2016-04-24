package me.teamalpha5441.mcplugins.admintools.hide;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.teamalpha5441.mcplugins.admintools.PluginPart;

public class HidePluginPart extends PluginPart {

	public ArrayList<UUID> _HiddenPlayers = new ArrayList<UUID>();
	private HideListener _HideListener = new HideListener(this);

	@Override
	public void onEnable(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(_HideListener, plugin);
		plugin.getCommand("hide").setExecutor(new HideCommand(this));
		plugin.getCommand("unhide").setExecutor(new UnHideCommand(this));
	}
}
