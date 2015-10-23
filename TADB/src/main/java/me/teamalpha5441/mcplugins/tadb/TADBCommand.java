package me.teamalpha5441.mcplugins.tadb;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

class TADBCommand implements CommandExecutor {

	private TADB base;
	
	public TADBCommand(TADB base) {
		this.base = base;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			return false;
		} else if (args.length < 2) {
			if (args[0].equals("reload")) {
				base.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "TADB reloaded");
				return true;
			} else if (args[0].equals("test")) {
				boolean testResult = base.getDatabaseManager().getDatabase().testConnection();
				sender.sendMessage(testResult ? ChatColor.GREEN + "DB is working" : ChatColor.RED + "DB isn't working");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}