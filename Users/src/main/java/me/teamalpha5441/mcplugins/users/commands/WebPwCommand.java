package me.teamalpha5441.mcplugins.users.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.users.Constants;
import me.teamalpha5441.mcplugins.users.backend.Backend;
import me.teamalpha5441.mcplugins.users.backend.Helper;

public class WebPwCommand extends ObsfuscatedCommand {

	private final Backend backend;

	public WebPwCommand(Backend backend) {
		this.backend = backend;
	}

	@Override
	public boolean onCommand(Player player, String args) {
		if (args.length() > 0) {
			try {
				String passwordHash = Helper.hashPassword(args);
				backend.usersSetField(player, Backend.FIELD_USERS_WEB_PASSWORD, passwordHash);
				player.sendMessage(ChatColor.GREEN + "Web password set");
			} catch (Exception e) {
				player.sendMessage(Constants.MSG_INTERNAL_ERROR);
			}
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "No password provided");
			return false;
		}
	}
}
