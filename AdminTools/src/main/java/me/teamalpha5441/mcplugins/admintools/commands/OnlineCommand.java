package me.teamalpha5441.mcplugins.admintools.commands;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.NoArgCommand;

public class OnlineCommand extends NoArgCommand {

	@Override
	public void onNoArgCommand(CommandSender sender) {
		Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
		ArrayList<String> displayNames = new ArrayList<String>(players.size());
		for (Player player : players) {
			displayNames.add(player.getDisplayName() + ChatColor.RESET);
		}
		sender.sendMessage(String.join(", ", displayNames));
	}
}
