package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class FlyCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		boolean canFly = !player.getAllowFlight();
		player.setAllowFlight(canFly);
		if (canFly) {
			player.sendMessage(StaticVars.MESSAGE_YOU_CAN_FLY_NOW);
		} else {
			player.sendMessage(StaticVars.MESSAGE_YOU_CANNOT_FLY_ANYMORE);
		}
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}
