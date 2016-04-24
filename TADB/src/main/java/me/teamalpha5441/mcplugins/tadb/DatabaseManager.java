package me.teamalpha5441.mcplugins.tadb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private HikariDataSource dataSource;

	public DatabaseManager(FileConfiguration config, Logger logger) throws SQLException {
		this.dataSource = new HikariDataSource();
		this.dataSource.setMinimumIdle(2);
		// this.dataSource.setPoolName("DBPOOL");
		this.dataSource.setDataSourceClassName("org.mariadb.jdbc.MySQLDataSource");
		this.dataSource.addDataSourceProperty("serverName", config.getString("mariadb.hostname"));
		this.dataSource.addDataSourceProperty("port", config.getInt("mariadb.port"));
		this.dataSource.addDataSourceProperty("user", config.getString("mariadb.username"));
		this.dataSource.addDataSourceProperty("password", config.getString("mariadb.password"));
		this.dataSource.addDataSourceProperty("databaseName", config.getString("mariadb.database"));

		Database testDatabase = null;
		try {
			testDatabase = new Database(this.dataSource.getConnection(), logger);
			long testResult = (long)testDatabase.executeQueryScalar("SELECT 17");
			if (testResult != 17) {
				throw new SQLException("Database test query failed");
			}
			testDatabase.close();
		} catch (SQLException ex) {
			if (testDatabase != null) {
				try {
					testDatabase.close();
				} finally { }
			}
			dispose();
			throw ex;
		}
	}

	public Database getDatabase(Logger logger) {
		try {
			return new Database(getConnection(), logger);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Database not available");
			return null;
		}
	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	void dispose() {
		if (this.dataSource != null) {
			try {
				this.dataSource.close();
			} finally {
				this.dataSource = null;
			}
		}
	}
}
