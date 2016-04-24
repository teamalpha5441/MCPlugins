package me.teamalpha5441.mcplugins.tamerge.core;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMerger {

	public static ItemStack merge(ItemStack i1, ItemStack i2, Map<Enchantment, Integer> levelLimits) {
		Material m1 = i1.getType();
		Material m2 = i2.getType();
		ItemStack finalItem = new ItemStack(m1, 1);

		//Set durability
		if (!(m1.equals(Material.BOOK) || m1.equals(Material.ENCHANTED_BOOK))) {
			short newDurability = (short)(i1.getDurability() - m2.getMaxDurability() + i2.getDurability());
			if (newDurability > 0) {
				finalItem.setDurability(newDurability);
			}
		}

		//Set enchantments
		Map<Enchantment, Integer> i1enchs = Helper.getEnchantments(i1);
		Map<Enchantment, Integer> i2enchs = Helper.getEnchantments(i2);
		Map<Enchantment, Integer> enchs = EnchantmentMerger.merge(i1enchs, i2enchs, m1, levelLimits);
		Helper.setEnchantments(finalItem, enchs);

		//Set display name
		String newDisplayName = null;
		ItemMeta i1meta = i1.getItemMeta();
		ItemMeta i2meta = i2.getItemMeta();
		if (i1meta.hasDisplayName()) {
			newDisplayName = i1meta.getDisplayName();
		} else if (i2meta.hasDisplayName()) {
			//Ignore enchanted emeralds
			if (!(m2.equals(Material.EMERALD) && i2enchs.size() > 0)) {
				newDisplayName = i2meta.getDisplayName();
			}
		}
		if (newDisplayName != null) {
			ItemMeta finalMeta = finalItem.getItemMeta();
			finalMeta.setDisplayName(newDisplayName);
			finalItem.setItemMeta(finalMeta);
		}

		return finalItem;
	}
}
