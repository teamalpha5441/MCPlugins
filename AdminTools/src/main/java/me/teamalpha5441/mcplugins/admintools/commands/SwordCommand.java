package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class SwordCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		ItemStack itemStack = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 2000, true);
		itemMeta.setDisplayName(ChatColor.DARK_GRAY + "Elucidator");
		itemStack.setItemMeta(itemMeta);
		player.getInventory().addItem(itemStack);
		player.sendMessage(StaticVars.MESSAGE_ITS_DANGEROUS_TO_GO_ALONE);
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}
