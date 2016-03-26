package me.teamalpha5441.mcplugins.admintools.hide;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class HideCommand implements CommandExecutor {
	
	private final HidePluginPart pluginPart;
	
	public HideCommand(HidePluginPart pluginPart) {
		this.pluginPart = pluginPart;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (!pluginPart._HiddenPlayers.contains(player.getUniqueId())) {
				pluginPart._HiddenPlayers.add(player.getUniqueId());
				for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					onlinePlayer.hidePlayer(player);
				}
				sender.sendMessage(ChatColor.GREEN + "You are now hidden");
			} else {
				sender.sendMessage(ChatColor.YELLOW + "You are already hidden");
			}
		} else {
			sender.sendMessage(StaticVars.MESSAGE_MUST_BE_PLAYER);
		}
		return true;
	}
}