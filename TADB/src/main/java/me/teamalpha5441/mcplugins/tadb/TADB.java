package me.teamalpha5441.mcplugins.tadb;

import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class TADB extends JavaPlugin {

	DatabaseManager databaseManager;

	@Override
	public void onLoad() {
		saveDefaultConfig();

		try {
			this.databaseManager = new DatabaseManager(getConfig(), getLogger());
			getServer().getServicesManager().register(DatabaseManager.class, this.databaseManager, this, ServicePriority.Normal);
		} catch (SQLException ex) {
			this.databaseManager = null;
			getLogger().log(Level.SEVERE, "Database test failed", ex);
		}
	}

	@Override
	public void onEnable() {
		if (this.databaseManager != null) {
			getCommand("sql").setExecutor(new SQLCommand(this));
		} else {
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {
		if (this.databaseManager != null) {
			getServer().getServicesManager().unregister(this.databaseManager);
			this.databaseManager.dispose();
			this.databaseManager = null;
		}
	}
}
