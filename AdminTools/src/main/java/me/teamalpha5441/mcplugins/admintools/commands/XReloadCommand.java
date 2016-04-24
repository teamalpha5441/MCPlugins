package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import me.teamalpha5441.mcplugins.admintools.AdminTools;
import me.teamalpha5441.mcplugins.admintools.NoArgCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class XReloadCommand extends NoArgCommand {

	private final AdminTools base;

	public XReloadCommand(AdminTools base) {
		this.base = base;
	}

	@Override
	public void onNoArgCommand(CommandSender sender) {
		final Server server = base.getServer();
		server.broadcastMessage(StaticVars.MESSAGE_XRELOAD_WARNING);
		final Runnable reloadRunnable = new Runnable() {
			@Override
			public void run() {
				for (World world : server.getWorlds()) {
					world.save();
				}
				server.savePlayers();
				sender.sendMessage(StaticVars.MESSAGE_DATA_SAVED_RELOADING_NOW);
				server.reload();
				server.broadcastMessage(StaticVars.MESSAGE_XRELOAD_FINISHED);
			}
		};
		server.getScheduler().scheduleSyncDelayedTask(base, reloadRunnable, 3 * 20);
	}
}
