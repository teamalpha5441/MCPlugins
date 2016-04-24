package me.teamalpha5441.mcplugins.admintools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				onPlayerCommand(sender, (Player)sender);
				return true;
			} else {
				sender.sendMessage(StaticVars.MESSAGE_MUST_BE_PLAYER);
				return true;
			}
		} else if (args.length == 1) {
			Player player = Helper.getPlayerUnsafe(args[0]);
			if (player != null) {
				onPlayerCommand(sender, player);
			} else {
				sender.sendMessage(StaticVars.MESSAGE_PLAYER_NOT_FOUND);
			}
			return true;
		} else {
			sender.sendMessage(StaticVars.MESSAGE_TOO_MUCH_ARGUMENTS);
			return false;
		}
	}

	public abstract void onPlayerCommand(CommandSender sender, Player player);
}
