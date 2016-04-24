package me.teamalpha5441.mcplugins.tamerge;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.teamalpha5441.mcplugins.tamerge.core.CompatibilityChecker;
import me.teamalpha5441.mcplugins.tamerge.core.Helper;
import me.teamalpha5441.mcplugins.tamerge.core.ItemMerger;

public class MergeCommand implements CommandExecutor {

	private TAMerge base;

	public MergeCommand(TAMerge base) {
		this.base = base;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Sender must be a player");
		} else if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Not enough arguments");
			return false;
		} else if (args.length > 2) {
			sender.sendMessage(ChatColor.RED + "Too much arguments");
			return false;
		} else {
			int i1index = 0, i2index = 0;
			try {
				i1index = Integer.parseInt(args[0]);
				i2index = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
				sender.sendMessage(ChatColor.RED + "Cannot parse slot number");
				return true;
			}
			if (i1index < 1 || i1index > 9 || i2index < 1 || i2index > 9) {
				sender.sendMessage(ChatColor.RED + "Slot number out of range");
			} else if (i1index == i2index) {
				sender.sendMessage(ChatColor.RED + "Slot numbers are equal");
			} else {
				Player player = (Player)sender;
				boolean isCreative = player.getGameMode().equals(GameMode.CREATIVE);
				if (player.getLevel() < base.cfgCostMerge && !isCreative) {
					player.sendMessage(ChatColor.RED + "Not enough levels, at least " + base.cfgCostMerge + " needed");
				} else {
					ItemStack i1 = Helper.getSlot(player, i1index - 1);
					if (i1 != null) {
						ItemStack i2 = Helper.getSlot(player, i2index - 1);
						if (i2 != null) {
							if (CompatibilityChecker.isMergeable(base, i1, i2)) {
								ItemStack mergedItem = ItemMerger.merge(i1, i2, base.levelLimits);
								player.getInventory().setItem(i1index - 1, mergedItem);
								player.getInventory().clear(i2index - 1);
								player.sendMessage(ChatColor.GREEN + "Items merged");
								if (base.cfgCostMerge > 0 && !isCreative) {
									player.setLevel(player.getLevel() - base.cfgCostMerge);
								}
							} else {
								player.sendMessage(ChatColor.RED + "These items cannot be merged");
							}
						} else {
							player.sendMessage(ChatColor.RED + "No item in slot " + i2index);
						}
					} else {
						player.sendMessage(ChatColor.RED + "No item in slot " + i1index);
					}
				}
			}
		}
		return true;
	}
}
