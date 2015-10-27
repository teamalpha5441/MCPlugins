package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.teamalpha5441.mcplugins.admintools.AdminTools;

public class XStopCommand implements CommandExecutor {

	private final AdminTools base;
	
	public XStopCommand(AdminTools base) {
		this.base = base;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		final Server server = base.getServer();
		server.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + ChatColor.YELLOW + "Stopping server in 3 seconds, please prepare.");
		Runnable stopRunnable = new Runnable() {
			@Override
			public void run() {
				server.shutdown();
			}
		}; 
		server.getScheduler().scheduleSyncDelayedTask(base, stopRunnable, 3 * 20);
		return true;
	} 
}