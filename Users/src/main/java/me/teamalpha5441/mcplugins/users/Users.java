package me.teamalpha5441.mcplugins.users;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.teamalpha5441.mcplugins.prefixer.PrefixManager;
import me.teamalpha5441.mcplugins.tadb.DatabaseManager;
import me.teamalpha5441.mcplugins.users.backend.Backend;
import me.teamalpha5441.mcplugins.users.commands.*;

public class Users extends JavaPlugin {

	public Backend backend;
	public Config config;

	PrefixManager prefixManager;

	private DatabaseManager databaseManager;
	private HashMap<String, ObsfuscatedCommand> obsfuscatedCommands;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		this.databaseManager = getServer().getServicesManager().load(DatabaseManager.class);
		if (this.databaseManager != null) {
			this.backend = new Backend(this.databaseManager, getLogger());
			this.config = new Config(getConfig());

			if (this.config.Prefixes_Enabled) {
				if (getServer().getPluginManager().isPluginEnabled("Prefixer")) {
					this.prefixManager = getServer().getServicesManager().load(PrefixManager.class);
				} else {
					getLogger().log(Level.WARNING, "Prefixer not found, prefixes disabled!");
				}
			}

			// register events
			getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);

			// register normal commands
			if (this.config.Homes_Enabled) {
				getCommand("home").setExecutor(new HomeCommand(this.backend));
				getCommand("sethome").setExecutor(new SetHomeCommand(this.backend));
			}
			getCommand("mailbox").setExecutor(new MailboxCommand(this.backend));

			// register obsfuscated commands
			obsfuscatedCommands = new HashMap<String, ObsfuscatedCommand>();
			if (this.config.WebPw_Enabled) {
				obsfuscatedCommands.put("webpw", new WebPwCommand(this.backend));
			}
		} else {
			getLogger().log(Level.SEVERE, "Couldn't get DatabaseManager");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evt) {
		Player player = evt.getPlayer();
		for (String command : obsfuscatedCommands.keySet()) {
			String message = evt.getMessage();
			command = "/" + command;
			if (message.equals(command)) {
				obsfuscatedCommands.get(command).onCommand(player, "");
				evt.setCancelled(true);
			} else if (message.startsWith(command + " ")) {
				String args = message.substring(command.length() + 1);
				obsfuscatedCommands.get(command).onCommand(player, args);
				evt.setCancelled(true);
			}
		}
	}

	@Override
	public void onDisable() {
		this.databaseManager = null;
		this.backend = null;
		this.prefixManager = null;
	}
}
