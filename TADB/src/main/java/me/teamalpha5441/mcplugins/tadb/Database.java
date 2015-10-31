package me.teamalpha5441.mcplugins.tadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Database {

	public static void testConnection() throws Exception {
		TADB tadb = (TADB)Bukkit.getServer().getPluginManager().getPlugin(TADB.PLUGIN_NAME);
		if (tadb == null) {
			throw new Exception(TADB.PLUGIN_NAME + " not found");
		} else if (!tadb.isEnabled()) {
			throw new Exception(TADB.PLUGIN_NAME + " not enabled");
		}
	}
	
	public static void testConnection(Plugin testingPlugin) {
		try {
			testConnection();
		} catch (Exception ex) {
			testingPlugin.getLogger().log(Level.SEVERE, "Database not available", ex);
			Bukkit.getServer().getPluginManager().disablePlugin(testingPlugin);
		}
	}
	
	public static Database getDatabase() {
		return getDatabase(Bukkit.getLogger());
	}
	
	public static Database getDatabase(Logger logger) {
		try {
			TADB tadb = (TADB)Bukkit.getServer().getPluginManager().getPlugin(TADB.PLUGIN_NAME);
			testConnection();
			return new Database(tadb.dataSource.getConnection(), logger);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Couldn't connect to database", ex);
			return null;
		}
	}
	
	private Connection _Connection;
	private Logger _Logger;
	
	/**
	 * Creates a new Database instance around a given database connection
	 * @param Connection The database connection
	 * @param logger 
	 */
	public Database(Connection Connection, Logger logger) {
		_Connection = Connection;
	}
	
	/**
	 * Gets the inner database connection
	 * @return The inner database connection
	 */
	public Connection getConnection() {
		return _Connection;
	}
	
	/**
	 * Closes the database connection
	 * Do not reuse this Database instance after calling closeConnection()
	 */
	public boolean closeConnection() {
		try {
			_Connection.close();
			return true;
		} catch (SQLException ex) {
			_Logger.log(Level.SEVERE, "Couldn't execute query", ex);
			return false;
		}
	}
	
	/**
	 * Executes the given SQL select query and returns the ResultSet
	 * @param SQL The SQL query
	 * @param Args The arguments for the query
	 * @return The query result as a ResultSet
	 */
	public ResultSet executeQuery(String SQL, Object... Args) {
		try {
			PreparedStatement ps = _Connection.prepareStatement(SQL);
			for (int i = 0; i < Args.length; i++) {
				ps.setObject(i + 1, Args[i]);
			}
			return ps.executeQuery();
		} catch (SQLException ex) {
			_Logger.log(Level.SEVERE, "Couldn't execute query", ex);
			return null;
		}
	}

	/**
	 * Executes the given SQL update/insert query and returns the number of affected rows
	 * @param SQL The SQL query
	 * @param Args The arguments for the query
	 * @return The number of affected rows (-1 on error)
	 */
	public int executeUpdate(String SQL, Object... Args) {
		try {
			PreparedStatement ps = _Connection.prepareStatement(SQL);
			for (int i = 0; i < Args.length; i++) {
				ps.setObject(i + 1, Args[i]);
			}
			return ps.executeUpdate();
		} catch (SQLException ex) {
			_Logger.log(Level.SEVERE, "Couldn't execute query", ex);
			return -1;
		}
	}
	
	/**
	 * Executes the given SQL query and returns the Object in the first row and column of the ResultSet
	 * @param SQL The SQL query
	 * @param Args The arguments for the query
	 * @return The Object in the first row and column of the ResultSet
	 */
	public Object executeQueryScalar(String SQL, Object... Args) {
		ResultSet rs = executeQuery(SQL, Args);
		if (rs != null) {
			try {
				if (rs.next()) {
					return rs.getObject(1);
				}
			} catch (SQLException ex) {
				_Logger.log(Level.SEVERE, "Couldn't execute query", ex);
			}
		}
		return null;
	}
}