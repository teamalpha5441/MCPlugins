package me.teamalpha5441.mcplugins.tasuperuser;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RunAsCommand implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	private Player getPlayerUnsafe(String playerName) {
		return Bukkit.getServer().getPlayer(playerName);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			Player player = getPlayerUnsafe(args[0]);
			if (player == null) {
				sender.sendMessage(StaticVars.MESSAGE_PLAYER_NOT_FOUND);
			} else {
				StringBuilder sb = new StringBuilder();
				if (args[1].startsWith("/")) {
					sb.append(args[1].substring(1));
				} else {
					sb.append(args[1]);
				}
				for (int i = 2; i < args.length; i++) {
					sb.append(' ');
					sb.append(args[i]);
				}
				Bukkit.getServer().dispatchCommand(player, sb.toString());
				sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
			}
			return true;
		} else {
			return false;
		}
	}
}
