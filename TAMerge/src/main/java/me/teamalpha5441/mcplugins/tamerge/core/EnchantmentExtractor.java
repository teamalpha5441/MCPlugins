package me.teamalpha5441.mcplugins.tamerge.core;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class EnchantmentExtractor {
	
	private static ItemStack createEnchantedBook(Enchantment ench, int level) {
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
		meta.addStoredEnchant(ench, level, true);
		book.setItemMeta(meta);
		return book;
	}
	
	public static ArrayList<ItemStack> extract(ItemStack I) {
		Map<Enchantment, Integer> enchs = Helper.getEnchantments(I);
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for (Map.Entry<Enchantment, Integer> entry : enchs.entrySet()) {
			stacks.add(createEnchantedBook(entry.getKey(), entry.getValue()));
		}
		return stacks;
	}
}