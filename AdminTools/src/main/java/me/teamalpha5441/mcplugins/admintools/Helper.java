package me.teamalpha5441.mcplugins.admintools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Helper {

	@SuppressWarnings("deprecation")
	public static Player getPlayerUnsafe(String playerName) {
		return Bukkit.getPlayer(playerName);
	}
}
