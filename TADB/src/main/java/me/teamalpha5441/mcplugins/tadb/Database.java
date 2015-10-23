package me.teamalpha5441.mcplugins.tadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	private Connection _Connection;
	
	/**
	 * Creates a new Database instance around a given database connection
	 * @param Connection The database connection
	 */
	public Database(Connection Connection) {
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
	public void closeConnection() {
		try {
			_Connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
					return rs.getObject(0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Tests the connection to the database by executing "SELECT 1" and checking the result
	 * @return True if "SELECT 1" returned 1 and no exception occured
	 */
	public boolean testConnection() {
		Object oneObj = executeQueryScalar("SELECT 1");
		if (oneObj != null) {
			if (oneObj instanceof Integer) {
				return (int)oneObj == 1;
			}
		}
		return false;
	}
}