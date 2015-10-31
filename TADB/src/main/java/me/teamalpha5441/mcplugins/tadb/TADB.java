package me.teamalpha5441.mcplugins.tadb;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariDataSource;

public class TADB extends JavaPlugin {
	
	static final String PLUGIN_NAME = "TADB";
	
	HikariDataSource dataSource;
	
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
		
		Database testDatabase = null;
		try {
			testDatabase = new Database(dataSource.getConnection(), getLogger());
			long testResult = (long)testDatabase.executeQueryScalar("SELECT 17");
			if (testResult != 17) {
				throw new Exception("Database test query failed");
			}
		} catch (Exception ex) {
			getLogger().log(Level.SEVERE, "Database test failed", ex);
			getServer().getPluginManager().disablePlugin(this);
		} finally {
			if (testDatabase != null)
				testDatabase.closeConnection();
		}
		
		getCommand("sql").setExecutor(new SQLCommand(this));
	}
	
	@Override
	public void onDisable() {
		dataSource.close();
	}
}