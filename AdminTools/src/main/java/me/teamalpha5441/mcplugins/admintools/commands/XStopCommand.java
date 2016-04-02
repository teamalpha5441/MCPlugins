package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import me.teamalpha5441.mcplugins.admintools.AdminTools;
import me.teamalpha5441.mcplugins.admintools.NoArgCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class XStopCommand extends NoArgCommand {

	private final AdminTools base;
	
	public XStopCommand(AdminTools base) {
		this.base = base;
	}

	@Override
	public void onNoArgCommand(CommandSender sender) {
		final Server server = base.getServer();
		server.broadcastMessage(StaticVars.MESSAGE_XSTOP_WARNING);
		Runnable stopRunnable = new Runnable() {
			@Override
			public void run() {
				server.shutdown();
			}
		}; 
		server.getScheduler().scheduleSyncDelayedTask(base, stopRunnable, 3 * 20);
	}
}