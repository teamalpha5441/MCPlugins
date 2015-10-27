package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;

public class LaunchCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		Vector velocity = player.getVelocity();
		velocity.setY(100);
		player.setVelocity(velocity);
	}
}
