package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GCCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			System.gc();
			sender.sendMessage(ChatColor.GREEN + "Garbage collector should run now");
		} catch (Exception ex) {
			sender.sendMessage(ChatColor.RED + "ERROR: " + ex.getMessage());
		}
		return true;
	}
}
