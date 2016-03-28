package me.teamalpha5441.mcplugins.users.backend;

import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;

import me.teamalpha5441.mcplugins.tadb.Database;
import me.teamalpha5441.mcplugins.tadb.DatabaseManager;

public class Backend {

	public static final String FIELD_USERS_UUID = "uuid";
	public static final String FIELD_USERS_NAME = "name";
	public static final String FIELD_USERS_PREFIX = "prefix";
	public static final String FIELD_USERS_RANK = "rank";
	public static final String FIELD_TOTAL_TIME = "total_time";
	public static final String FIELD_USERS_LAST_SEEN = "last_seen";
	public static final String FIELD_USERS_LAST_LOCATION = "last_location";
	public static final String FIELD_USERS_MONEY = "money";
	public static final String FIELD_USERS_HOME_LOCATION = "home";
	public static final String FIELD_USERS_MAILBOX_LOCATION = "mailbox_location";
	public static final String FIELD_USERS_RULES_VERSION = "rules_version";
	public static final String FIELD_USERS_WEB_PASSWORD = "web_pw";

	// private static final String FIELD_TARGETS_ID = "id";
	// private static final String FIELD_TARGETS_TYPE = "type";
	// private static final String FIELD_TARGETS_PLAYER_UUID = "player_uuid";

	private static final String TABLE_PREFIX = "users_";
	private static final String TABLE_USERS = TABLE_PREFIX + "users";
	// private static final String TABLE_TARGETS = TABLE_PREFIX + "targets";
	// private static final String TABLE_TARGET_INHERITANCES = TABLE_PREFIX + "target_inheritances";
	// private static final String TABLE_PERMISSIONS = TABLE_PREFIX + "permissions";
	// private static final String TABLE_METAS = TABLE_PREFIX + "metas";

	private final DatabaseManager databaseManager;
	private final Logger logger;

	public Backend(DatabaseManager databaseManager, Logger logger) {
		this.databaseManager = databaseManager;
		this.logger = logger;
	}

	public boolean userExists(OfflinePlayer player) {
		Database db = this.databaseManager.getDatabase(this.logger);
		String playerUUID = player.getUniqueId().toString();
		try {
			return userExists(db, playerUUID);
		} finally {
			db.close();
		}
	}

	private boolean userExists(Database db, String playerUUID) {
		Object playerCount = db.executeQueryScalar("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + FIELD_USERS_UUID + " = ?", playerUUID);
		return (long)playerCount == 1;
	}

	public void tryCreateUser(OfflinePlayer player) {
		Database db = this.databaseManager.getDatabase(this.logger);
		String playerUUID = player.getUniqueId().toString();
		if (!userExists(db, playerUUID)) {
			db.executeUpdate("INSERT INTO " + TABLE_USERS + " (" + FIELD_USERS_UUID + ") VALUES (?)", playerUUID);
			// db.executeUpdate("INSERT INTO " + TABLE_TARGETS + " (" + FIELD_TARGETS_TYPE + ", " + FIELD_TARGETS_PLAYER_UUID + ") VALUES (0, ?)",	playerUUID);
		}
		db.close();
	}

	public void usersSetField(OfflinePlayer player, String fieldName, String value) {
		Database db = this.databaseManager.getDatabase(this.logger);
		String playerUUID = player.getUniqueId().toString();
		db.executeUpdate("UPDATE " + TABLE_USERS + " SET " + fieldName + " = ? WHERE " + FIELD_USERS_UUID + " = ?", value, playerUUID);
		db.close();
	}

	public Object usersGetField(OfflinePlayer player, String fieldName) {
		Database db = this.databaseManager.getDatabase(this.logger);
		String playerUUID = player.getUniqueId().toString();
		try {
			return db.executeQueryScalar("SELECT " + fieldName + " FROM " + TABLE_USERS + " WHERE " + FIELD_USERS_UUID + " = ?", playerUUID);
		} finally {
			db.close();
		}
	}

	/*
	public int targetFromUser(OfflinePlayer player) {
		Database db = this.databaseManager.getDatabase(this.logger);
		String playerUUID = player.getUniqueId().toString();
		try {
			return (int)(long)db.executeQueryScalar("SELECT " + FIELD_TARGETS_ID + " FROM " + TABLE_TARGETS, playerUUID);
		} finally {
			db.close();
		}
	}
	*/
}
