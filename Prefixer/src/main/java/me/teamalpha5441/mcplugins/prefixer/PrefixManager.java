package me.teamalpha5441.mcplugins.prefixer;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PrefixManager {

	public static PrefixManager getPrefixManager() {
		return Prefixer.getPrefixManager();
	}
	
	private final Scoreboard scoreboard;
	private final HashMap<UUID, String> lowPriorityPrefixes;
	private final HashMap<UUID, String> prefixes;
	
	public PrefixManager() {
		this.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		this.lowPriorityPrefixes = new HashMap<UUID, String>();
		this.prefixes = new HashMap<UUID, String>();
	}

	public void managePlayer(Player player) {
		player.setScoreboard(this.scoreboard);
	}
	
	public void unmanagePlayer(Player player) {
		UUID uuid = player.getUniqueId();
		this.lowPriorityPrefixes.remove(uuid);
		this.prefixes.remove(uuid);
		updatePlayerPrefix(player);
	}

	public void setLowPriorityPrefix(Player player, String prefix) {
		UUID uuid = player.getUniqueId();
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		this.lowPriorityPrefixes.put(uuid, prefix);
		updatePlayerPrefix(player);
	}
	
	public void setPrefix(Player player, String prefix) {
		UUID uuid = player.getUniqueId();
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		this.prefixes.put(uuid, prefix);
		updatePlayerPrefix(player);
	}
	
	public void removePrefix(Player player) {
		UUID uuid = player.getUniqueId();
		this.prefixes.remove(uuid);
		updatePlayerPrefix(player);
	}
	
	public String getCalculatedPrefix(Player player) {
		UUID uuid = player.getUniqueId();
		String prefix = this.prefixes.get(uuid);
		if (prefix != null && prefix.length() > 0) {
			return prefix;
		} else {
			prefix = this.lowPriorityPrefixes.get(uuid);
			if (prefix != null && prefix.length() > 0) {
				return prefix;
			}
		}
		return null;
	}
	
	private void updatePlayerPrefix(Player player) {
		Team currentTeam = this.scoreboard.getEntryTeam(player.getName());
		if (currentTeam != null) {
			currentTeam.removeEntry(player.getName());
			if (currentTeam.getEntries().size() == 0) {
				currentTeam.unregister();
			}
		}
		String prefix = getCalculatedPrefix(player);
		if (prefix != null) {
			Team newTeam = this.scoreboard.getTeam(prefix);
			if (newTeam == null) {
				newTeam = this.scoreboard.registerNewTeam(prefix);
				newTeam.setPrefix(prefix);
				newTeam.setSuffix("" + ChatColor.RESET);
			}
			newTeam.addEntry(player.getName());
			player.setDisplayName(prefix + player.getName() + ChatColor.RESET);
		}
	}
}
