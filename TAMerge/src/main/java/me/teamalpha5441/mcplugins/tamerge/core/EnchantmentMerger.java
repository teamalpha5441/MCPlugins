package me.teamalpha5441.mcplugins.tamerge.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantmentMerger {
	
	public static Map<Enchantment, Integer> merge(Map<Enchantment, Integer> E1, Map<Enchantment, Integer> E2, Material Material, Map<Enchantment, Integer> LevelLimits) {
		HashMap<Enchantment, Integer> newEnchantments = new HashMap<Enchantment, Integer>();
		newEnchantments.putAll(E1);
		for (Map.Entry<Enchantment, Integer> entry : E2.entrySet()) {
			Enchantment ench = entry.getKey();
			int level = entry.getValue();
			if (newEnchantments.containsKey(ench)) {
				int actualLevel = newEnchantments.get(ench);
				if (level == actualLevel) {
					Integer maxLevel = LevelLimits.get(ench);
					if (maxLevel != null) {
						if (level < maxLevel) {
							newEnchantments.put(ench, level + 1);
						}
					} else {
						newEnchantments.put(ench, level + 1);
					}
				} else if (level > actualLevel) {
					newEnchantments.put(ench, level);
				}
			} else if (!conflictsWithSet(newEnchantments.keySet(), ench) && canEnchant(ench, Material)) {
				newEnchantments.put(ench, level);
			}
		}
		return newEnchantments;
	}
	
	private static boolean canEnchant(Enchantment E, Material M) {
		ItemStack tmp = new ItemStack(M, 1);
		return E.canEnchantItem(tmp);
	}
	
	private static boolean conflictsWithSet(Set<Enchantment> set, Enchantment ench) {
		for (Enchantment sEnch : set) {
			if (ench.conflictsWith(sEnch)) {
				return true;
			}
		}
		return false;
	}
}