package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class IgniteCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		player.setFireTicks(60 * 60 * 20);
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}
