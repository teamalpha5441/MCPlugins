package me.teamalpha5441.mcplugins.tadb;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class DatabaseManager {

	private TADB base;
	
	public DatabaseManager(TADB base) {
		this.base = base;
	}
	
	/**
	 * Returns the DatabaseManager of TADB running in the current running server
	 * @return The DatabaseManager or null if TADB is not running
	 */
	public static DatabaseManager getDatabaseManager() {
		return getDatabaseManager(Bukkit.getServer());
	}

	/**
	 * Returns the DatabaseManager of TADB running in the given server
	 * @return The DatabaseManager or null if TADB is not running
	 */
	public static DatabaseManager getDatabaseManager(Server Server) {
		Plugin plugin = Server.getPluginManager().getPlugin("TADB");
		if (plugin != null) {
			if (plugin instanceof TADB) {
				TADB tadb = (TADB)plugin;
				return tadb.getDatabaseManager();	
			}
		}
		return null;
	}
	
	private Database connectMySQL(String Hostname, int Port, String Database, String Username, String Password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return new Database(DriverManager.getConnection("jdbc:mysql://" + Hostname + ":" + Port
								+ "/" + Database + "?user=" + Username + "&password=" + Password));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Database connectMySQL() {
		return connectMySQL(
				base.getConfig().getString("mysql.hostname", "127.0.0.1"),
				base.getConfig().getInt("mysql.port", 3306),
				base.getConfig().getString("mysql.database", "bukkit"),
				base.getConfig().getString("mysql.username", "bukkit"),
				base.getConfig().getString("mysql.password", "bukkit"));
	}
	
	private Database connectSQLite(String Path) {
		try {
			Class.forName("org.sqlite.JDBC");
	        return new Database(DriverManager.getConnection("jdbc:sqlite:" + Path));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Database connectSQLite() {
		return connectSQLite(base.getConfig().getString("sqlite.path"));
	}
	
	/**
	 * Creates a Database instance around the database connection configured in config.yml
	 * @return The Database instance
	 */
	public Database getDatabase() {
		String db = base.getConfig().getString("database", "sqlite").trim().toLowerCase();
		if (db.equals("mysql")) {
			return connectMySQL();
		} else if (db.equals("sqlite")) {
			return connectSQLite();
		} else {
			return null;
		}
	}
}