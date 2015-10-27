package me.teamalpha5441.mcplugins.admintools.hide;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnHideCommand implements CommandExecutor {
	
	private final HidePluginPart pluginPart;
	
	public UnHideCommand(HidePluginPart pluginPart) {
		this.pluginPart = pluginPart;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (pluginPart._HiddenPlayers.contains(player.getUniqueId())) {
				pluginPart._HiddenPlayers.remove(player.getUniqueId());
				for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					onlinePlayer.showPlayer(player);
				}
				sender.sendMessage(ChatColor.GREEN + "You are now visible");
			} else {
				sender.sendMessage(ChatColor.YELLOW + "You are already visible");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player");
		}
		return true;
	}
}