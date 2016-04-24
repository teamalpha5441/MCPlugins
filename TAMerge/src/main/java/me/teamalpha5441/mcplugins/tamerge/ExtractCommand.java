package me.teamalpha5441.mcplugins.tamerge;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import me.teamalpha5441.mcplugins.tamerge.core.EnchantmentExtractor;
import me.teamalpha5441.mcplugins.tamerge.core.Helper;

public class ExtractCommand implements CommandExecutor {

	private TAMerge base;

	public ExtractCommand(TAMerge base) {
		this.base = base;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Sender must be a player");
		} else if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Not enough arguments");
			return false;
		} else if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Too much arguments");
			return false;
		} else {
			int index = 0;
			try {
				index = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				sender.sendMessage(ChatColor.RED + "Cannot parse slot number");
				return true;
			}
			if (index < 1 || index > 9) {
				sender.sendMessage(ChatColor.RED + "Slot number out of range");
			} else {
				Player player = (Player)sender;
				boolean isCreative = player.getGameMode().equals(GameMode.CREATIVE);
				if (!isCreative && player.getLevel() < base.cfgCostExtract) {
					player.sendMessage(ChatColor.RED + "Not enough levels, at least " + base.cfgCostExtract + " needed");
				} else {
					ItemStack item = Helper.getSlot(player, index - 1);
					if (item != null) {
						Material mItem = item.getType();
						if (mItem.equals(Material.ENCHANTED_BOOK)) {
							EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
							if (meta.getStoredEnchants().size() < 2) {
								player.sendMessage(ChatColor.RED + "Enchanted book only has one enchantment");
								return true;
							}
						} else if (item.getEnchantments().size() < 1) {
							player.sendMessage(ChatColor.RED + "Item is not enchanted");
							return true;
						}
						if (mItem.equals(Material.EMERALD)) {
							player.sendMessage(ChatColor.RED + "You cannot extract enchantments of Magic Crystals");
						} else {
							ArrayList<ItemStack> books = EnchantmentExtractor.extract(item);
							if (item.getType().equals(Material.ENCHANTED_BOOK)) {
								player.getInventory().clear(index - 1);
							} else {
								Helper.removeEnchantments(item);
								player.getInventory().setItem(index - 1, item);
							}
							player.sendMessage(ChatColor.GREEN + "Enchantments extracted");
							Helper.giveOrDropItems(player, books);
							if (base.cfgCostExtract > 0 && !isCreative) {
								player.setLevel(player.getLevel() - base.cfgCostExtract);
							}
						}
					} else {
						player.sendMessage(ChatColor.RED + "No item in slot " + index);
					}
				}
			}
		}
		return true;
	}
}
