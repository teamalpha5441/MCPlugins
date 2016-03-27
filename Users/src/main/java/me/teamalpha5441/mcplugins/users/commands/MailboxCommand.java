package me.teamalpha5441.mcplugins.users.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teamalpha5441.mcplugins.users.Constants;
import me.teamalpha5441.mcplugins.users.backend.Backend;
import me.teamalpha5441.mcplugins.users.backend.Helper;

public class MailboxCommand implements CommandExecutor {

	private final Backend backend;
	
	public MailboxCommand(Backend backend) {
		this.backend = backend;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (args.length < 1) {
				player.sendMessage(ChatColor.RED + "Player name not provided");
				return false;
			} else if (args.length > 1) {
				player.sendMessage(Constants.MSG_TOO_MUCH_ARGUMENTS);
				return false;
			} else {
				OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
				if (targetPlayer != null) {
					String strMailboxLocation = (String)backend.usersGetField(targetPlayer, Backend.FIELD_USERS_MAILBOX_LOCATION);
					if (strMailboxLocation != null) {
						Block chestBlock = Helper.locationFromString(strMailboxLocation).getBlock();
						if (chestBlock.getType() == Material.CHEST || chestBlock.getType() == Material.TRAPPED_CHEST) {
							Chest chest = (Chest)chestBlock.getState();
							player.openInventory(chest.getInventory());
							if (!chest.update()) {
								player.sendMessage(ChatColor.RED + "Chest update failed, maybe the items are lost");
							}
						} else {
							player.sendMessage(Constants.MSG_MAILBOX_LOCATION_INVALID);
						}
					} else {
						player.sendMessage(ChatColor.RED + "Mailbox location not set");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Player not found");
				}
				return true;
			}
		} else {
			sender.sendMessage(Constants.MSG_MUST_BE_PLAYER);
			return true;
		}
	}
}