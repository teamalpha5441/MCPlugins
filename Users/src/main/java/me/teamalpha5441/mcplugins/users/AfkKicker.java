package me.teamalpha5441.mcplugins.users;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class AfkKicker implements Listener, Runnable {

	private Users base;
	private int maxIdleSeconds;
	private HashMap<UUID, Integer> playerIdleSeconds;
	private int taskID = -1;

	public AfkKicker(Users base, int maxIdleSeconds) {
		this.base = base;
		this.maxIdleSeconds = maxIdleSeconds;
		this.playerIdleSeconds = new HashMap<UUID, Integer>();
	}

	public void start() {
		if (this.taskID < 0) {
			Bukkit.getPluginManager().registerEvents(this, base);
			this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(base, this, 0, 20);
			if (this.taskID < 0) {
				throw new RuntimeException("Task creation failed");
			}
		} else {
			throw new RuntimeException("Already startet");
		}
	}

	public void stop() {
		if (this.taskID < 0) {
			throw new RuntimeException("Not started");
		} else {
			Bukkit.getScheduler().cancelTask(this.taskID);
		}
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			int currentPlayerIdleSeconds = this.playerIdleSeconds.get(player.getUniqueId());
			if (currentPlayerIdleSeconds > this.maxIdleSeconds) {
				player.kickPlayer("AFK for too long");
			} else {
				this.playerIdleSeconds.put(player.getUniqueId(), currentPlayerIdleSeconds + 1);
			}
		}
	}

	private void resetPlayerIdleSeconds(UUID playerUUID) {
		this.playerIdleSeconds.put(playerUUID, 0);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {
		this.playerIdleSeconds.remove(evt.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	//TODO Make async
	@EventHandler
	public void onPlayerChatEvent(PlayerChatEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	public void onPlayerItemHeld(PlayerItemHeldEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent evt) {
		resetPlayerIdleSeconds(evt.getPlayer().getUniqueId());
	}
}
