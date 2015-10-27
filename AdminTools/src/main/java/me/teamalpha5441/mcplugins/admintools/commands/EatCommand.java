package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class EatCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		player.setFoodLevel(20);
		player.setSaturation(20);
		player.setExhaustion(0);
		player.sendMessage(StaticVars.MESSAGE_ITADAKIMASU);
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}
