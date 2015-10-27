package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.teamalpha5441.mcplugins.admintools.AdminTools;

public class XReloadCommand implements CommandExecutor {

	private final AdminTools base;
	
	public XReloadCommand(AdminTools base) {
		this.base = base;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		final Server server = base.getServer();
		server.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + ChatColor.YELLOW + "Reloading server in 3 seconds, please prepare and reconnect if you disconnect.");
		final Runnable reloadRunnable = new Runnable() {
			@Override
			public void run() {
				for (World world : server.getWorlds()) {
					world.save();
					sender.sendMessage(ChatColor.AQUA + "World \"" + world.getName() + "\" saved");
				}
				server.savePlayers();
				sender.sendMessage(ChatColor.AQUA + "All player data saved");
				sender.sendMessage(ChatColor.AQUA + "Reloading now");
				server.reload();
				server.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + ChatColor.GREEN + "Reloading has finished, thank you for your patience!");
			}
		}; 
		server.getScheduler().scheduleSyncDelayedTask(base, reloadRunnable, 3 * 20);
		return true;
	} 
}