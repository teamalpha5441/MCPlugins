package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.teamalpha5441.mcplugins.admintools.NoArgCommand;

public class GCCommand extends NoArgCommand {
	
	@Override
	public void onNoArgCommand(CommandSender sender) {
		System.gc();
		sender.sendMessage(ChatColor.GREEN + "Garbage collector should run now");
	}
}
