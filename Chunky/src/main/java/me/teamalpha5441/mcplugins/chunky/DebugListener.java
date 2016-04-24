package me.teamalpha5441.mcplugins.chunky;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class DebugListener implements Listener {

	private final Logger logger;

	public DebugListener(Logger logger) {
		this.logger = logger;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkLoadMonitor(ChunkLoadEvent evt) {
		logChunkEvent(evt.getChunk(), true, false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkUnloadMonitor(ChunkUnloadEvent evt) {
		logChunkEvent(evt.getChunk(), false, evt.isCancelled());
	}

	private void logChunkEvent(Chunk chunk, boolean loadUnload, boolean cancelled) {
		String location = chunk.getWorld().getName() + ", " + chunk.getX() + ", " + chunk.getZ();
		logger.log(Level.INFO, "Chunk " + (!loadUnload ? "un" : "") + "load (" + location + ")" + (cancelled ? " CANCELLED" : ""));
	}
}
