package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class InventoryCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		if (sender instanceof Player) {
			((Player)sender).openInventory(player.getInventory());
		} else {
			sender.sendMessage(StaticVars.MESSAGE_MUST_BE_PLAYER);
		}
	}
}
