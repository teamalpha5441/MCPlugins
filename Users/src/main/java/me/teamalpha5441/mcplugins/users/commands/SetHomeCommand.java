package me.teamalpha5441.mcplugins.users.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.users.Constants;
import me.teamalpha5441.mcplugins.users.backend.Backend;
import me.teamalpha5441.mcplugins.users.backend.Helper;

public class SetHomeCommand implements CommandExecutor {

	private final Backend backend;
	
	public SetHomeCommand(Backend backend) {
		this.backend = backend;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (args.length == 0) {
				try {
					String locationString = Helper.locationToString(player.getLocation(), true);
					backend.usersSetField(player, Backend.FIELD_USERS_HOME_LOCATION, locationString);
					player.sendMessage(Constants.MSG_HOME_SET);
				} catch (Exception e) {
					player.sendMessage(Constants.MSG_INTERNAL_ERROR);
				}
				return true;
			} else {
				player.sendMessage(Constants.MSG_TOO_MUCH_ARGUMENTS);
				return false;
			}
		} else {
			sender.sendMessage(Constants.MSG_MUST_BE_PLAYER);
			return true;
		}
	}
}
