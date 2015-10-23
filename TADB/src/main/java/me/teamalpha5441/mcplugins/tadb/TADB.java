package me.teamalpha5441.mcplugins.tadb;

import org.bukkit.plugin.java.JavaPlugin;

public class TADB extends JavaPlugin {

	private DatabaseManager _DatabaseManager = null;
	
	/**
	 * Returns the instance of DatabaseManager managed by TADB
	 * @return The DatabaseManager
	 */
	public DatabaseManager getDatabaseManager() {
		return _DatabaseManager;
	}
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		_DatabaseManager = new DatabaseManager(this);
	}
	
	@Override
	public void onEnable() {
		getCommand("tadb").setExecutor(new TADBCommand(this));
		if (getConfig().getBoolean("enable-sql-command", false)) {
			getCommand("sql").setExecutor(new SQLCommand(this));
		}
	}
}