package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class ExplodeCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		Location location = player.getLocation();
		player.getWorld().createExplosion(
			location.getX(),
			location.getY(),
			location.getZ(),
			1f, false, false);
		player.setHealth(0);
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}
