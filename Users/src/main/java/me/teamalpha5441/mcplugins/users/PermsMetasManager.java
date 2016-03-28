package me.teamalpha5441.mcplugins.users;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class PermsMetasManager {

	private final Users base;
	private final HashMap<UUID, PermissionAttachment> attachments;
	
	public PermsMetasManager(Users base) {
		this.base = base;
		attachments = new HashMap<UUID, PermissionAttachment>();
	}
	
	public void removePermissions(Player player) {
		UUID playerUUID = player.getUniqueId();
		if (attachments.containsKey(playerUUID)) {
			attachments.remove(playerUUID).remove();
		}
	}
	
	public void removeEveryonesPermissions(Player player) {
		for (PermissionAttachment attachment : attachments.values()) {
			attachment.remove();
		}
		attachments.clear();
	}
	
	public void setPermissions(Player player) {
		setPermissions(player, player);
	}
	
	public void setPermissions(Player target, OfflinePlayer source) {
		PermissionAttachment attachment = target.addAttachment(base);
		attachments.put(target.getUniqueId(), attachment);
		
		base.getLogger().log(Level.SEVERE, "Permissions are not yet implemented");
	}
}
