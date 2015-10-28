package me.teamalpha5441.mcplugins.admintools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class NoArgCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			onNoArgCommand(sender);
			return true;
		} else {
			sender.sendMessage(StaticVars.MESSAGE_TOO_MUCH_ARGUMENTS);
			return false;
		}
	}

	public abstract void onNoArgCommand(CommandSender sender);
}
