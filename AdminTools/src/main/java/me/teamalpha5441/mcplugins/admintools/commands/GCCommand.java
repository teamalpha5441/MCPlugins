package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;

import me.teamalpha5441.mcplugins.admintools.NoArgCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class GCCommand extends NoArgCommand {

	@Override
	public void onNoArgCommand(CommandSender sender) {
		System.gc();
		sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
	}
}
