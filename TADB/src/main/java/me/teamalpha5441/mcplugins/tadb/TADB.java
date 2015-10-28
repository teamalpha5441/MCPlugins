package me.teamalpha5441.mcplugins.tadb;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariDataSource;

public class TADB extends JavaPlugin {
	
	private static final String PLUGIN_NAME = "TADB";
	
	private HikariDataSource dataSource;
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
	}
	
	@Override
	public void onEnable() {
		dataSource = new HikariDataSource();
		dataSource.setMinimumIdle(2);
		dataSource.setPoolName(getName());
		dataSource.setDataSourceClassName("org.mariadb.jdbc.MySQLDataSource");
		dataSource.addDataSourceProperty("serverName", getConfig().getString("mariadb.hostname"));
		dataSource.addDataSourceProperty("port", getConfig().getInt("mariadb.port"));
		dataSource.addDataSourceProperty("user", getConfig().getString("mariadb.username"));
		dataSource.addDataSourceProperty("password", getConfig().getString("mariadb.password"));
		dataSource.addDataSourceProperty("databaseName", getConfig().getString("mariadb.database"));
		
		getCommand("sql").setExecutor(new SQLCommand(this));
	}
	
	@Override
	public void onDisable() {
		dataSource.close();
	}
	
	public static Database getDatabaseWrapper() throws Exception {
		try {
			TADB tadb = (TADB)Bukkit.getServer().getPluginManager().getPlugin(PLUGIN_NAME);
			if (tadb == null) {
				throw new Exception(PLUGIN_NAME + " not found");
			} else if (!tadb.isEnabled()) {
				throw new Exception(PLUGIN_NAME + " not enabled");
			} else {
				return new Database(tadb.dataSource.getConnection());
			}
		} catch (Exception ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Couldn't connect to database", ex);
			return null;
		}
	}
}