package me.teamalpha5441.mcplugins.users;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.teamalpha5441.mcplugins.users.backend.Backend;
import me.teamalpha5441.mcplugins.users.backend.Helper;

public class JoinQuitListener implements Listener {

	private final Users base;
	private final HashMap<UUID, Long> loginTimes;

	public JoinQuitListener(Users base) {
		this.base = base;
		this.loginTimes = new HashMap<UUID, Long>();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		try {
			base.backend.tryCreateUser(player);

			// Refresh user name
			base.backend.usersSetField(player, Backend.FIELD_USERS_NAME, player.getName());

			// Set username prefix
			if (base.prefixManager != null) {
				String prefix = (String)base.backend.usersGetField(player, Backend.FIELD_USERS_PREFIX);
				if (prefix == null) {
					prefix = base.config.Prefixes_DefaultPrefix;
				}
				base.prefixManager.setLowPriorityPrefix(player, prefix);
			}

			// Rule Reminder
			if (base.config.RulesReminder_Enabled) {
				int currentRulesVersion = base.config.RulesReminder_Version;
				int lastAcceptedRulesVersion = (int)base.backend.usersGetField(player, Backend.FIELD_USERS_RULES_VERSION);
				if (lastAcceptedRulesVersion < currentRulesVersion) {
					player.sendMessage(base.config.RulesReminder_Message);
					base.backend.usersSetField(player, Backend.FIELD_USERS_RULES_VERSION, Integer.toString(currentRulesVersion));
				}
			}

			// Mailbox Reminder
			if (base.config.MailboxReminder_Enabled) {
				String strMailboxLocation = (String)base.backend.usersGetField(player, Backend.FIELD_USERS_MAILBOX_LOCATION);
				if (strMailboxLocation != null) {
					Block chestBlock = Helper.locationFromString(strMailboxLocation).getBlock();
					if (chestBlock.getType() == Material.CHEST || chestBlock.getType() == Material.TRAPPED_CHEST) {
						ItemStack[] contents = ((Chest)chestBlock.getState()).getInventory().getContents();
						for (int i = 0; i < contents.length; i++) {
							if (contents[i] != null) {
								player.sendMessage(base.config.MailboxReminder_Message);
								break;
							}
						}
					} else {
						player.sendMessage(Constants.MSG_MAILBOX_LOCATION_INVALID);
					}
				}
			}

			// Save player login time (needed for computing play time)
			loginTimes.put(player.getUniqueId(), Helper.getUnixTime());
		} catch (Exception ex) {
			base.getLogger().log(Level.SEVERE, "Couldn't refresh player data", ex);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {
		Player player = evt.getPlayer();
		try {
			// Save last location
			String locationString = Helper.locationToString(player.getLocation(), true);
			base.backend.usersSetField(player, Backend.FIELD_USERS_LAST_LOCATION, locationString);

			// Save last seen timestamp
			long currentTime = Helper.getUnixTime();
			base.backend.usersSetField(player, Backend.FIELD_USERS_LAST_SEEN, Long.toString(currentTime));

			// Add play time to total play time
			long loginTime = loginTimes.remove(player.getUniqueId());
			long totalPlayTime = (long)base.backend.usersGetField(player, Backend.FIELD_TOTAL_TIME);
			String newTotalPlayTime = Long.toString(totalPlayTime + (currentTime - loginTime));
			base.backend.usersSetField(player, Backend.FIELD_TOTAL_TIME, newTotalPlayTime);
		} catch (Exception ex) {
			base.getLogger().log(Level.SEVERE, "Couldn't refresh player data", ex);
		}
	}
}
