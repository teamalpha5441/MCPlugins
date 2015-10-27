package me.teamalpha5441.mcplugins.admintools.mechanics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class ColoredSigns implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignChange(SignChangeEvent evt) {
		Player player = evt.getPlayer();
		if (player.hasPermission(StaticVars.PERM_CREATE_COLORED_SIGNS)) {
			String[] lines = evt.getLines();
			for (int i = 0; i < lines.length; i++) {
				evt.setLine(i, ChatColor.translateAlternateColorCodes('&', lines[i]));
			}
		}
	}
}