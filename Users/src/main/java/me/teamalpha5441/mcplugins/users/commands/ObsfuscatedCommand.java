package me.teamalpha5441.mcplugins.users.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.users.Constants;

public abstract class ObsfuscatedCommand {
	
	public abstract boolean onCommand(Player player, String args);
	
	public void execute(PluginCommand pluginCommand, Player player, String args, Logger logger) {
		
		if (logger != null) {
			logger.log(Level.INFO, player.getDisplayName() + " executed command /" + pluginCommand.getName());
		}
		
		String permission = pluginCommand.getPermission();
		if (permission != null) {
			if (!player.hasPermission(permission)) {
				player.sendMessage(Constants.MSG_NO_PERMISSION);
				return;
			}
		}
		
		if (!onCommand(player, args)) {
			player.sendMessage(pluginCommand.getUsage());
		}
	}
}
