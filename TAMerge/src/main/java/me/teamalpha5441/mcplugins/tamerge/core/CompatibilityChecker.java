package me.teamalpha5441.mcplugins.tamerge.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.teamalpha5441.mcplugins.tamerge.TAMerge;

public class CompatibilityChecker {
	
	private static final Material[] GRP_HELMETS = { Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET };
	private static final Material[] GRP_LEGGINGS = { Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS };
	private static final Material[] GRP_CHESTPLATES = { Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE };
	private static final Material[] GRP_BOOTS = { Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS };
	
	private static final Material[] GRP_SWORDS = { Material.WOOD_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD };
	private static final Material[] GRP_AXES = { Material.WOOD_AXE, Material.STONE_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE };
	private static final Material[] GRP_PICKAXES = { Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE };
	private static final Material[] GRP_SPADES = { Material.WOOD_SPADE, Material.STONE_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE };
	private static final Material[] GRP_HOES = { Material.WOOD_HOE, Material.STONE_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.DIAMOND_HOE };
	
	private static final Material[] GRP_FISHING_RODS = { Material.FISHING_ROD, Material.CARROT_STICK };
	private static final Material[] GRP_UNGROUPED = { Material.SHEARS, Material.FLINT_AND_STEEL, Material.BOW };
	
	private static final Material[][] MGRP_TOOLS = { GRP_HELMETS, GRP_LEGGINGS, GRP_CHESTPLATES, GRP_BOOTS, GRP_SWORDS, GRP_AXES, GRP_PICKAXES, GRP_SPADES, GRP_HOES };
	
	private static Integer getGroupIndex(Material[] grp, Material mtrl) {
		for (int i = 0; i < grp.length; i++) {
			if (grp[i].equals(mtrl)) {
				return i;
			}
		}
		return null;
	}
	
	private static Integer getGroupIndex(Material[][] mgrp, Material mtrl) {
		for (int i = 0; i < mgrp.length; i++) {
			Integer gindex = getGroupIndex(mgrp[i], mtrl);
			if (gindex != null) {
				return i;
			}
		}
		return null;
	}
	
	public static boolean isMergeable(TAMerge base, ItemStack S1, ItemStack S2) {
		Material M1 = S1.getType();
		Material M2 = S2.getType();
		
		// if M1 is a BOOK or ENCHANTED_BOOK, then M2 must be an ENCHANTED_BOOK
		if ((M1.equals(Material.BOOK) || M1.equals(Material.ENCHANTED_BOOK))
			&& M2.equals(Material.ENCHANTED_BOOK)) {
			return true;
		}

		// SHEARS, BOW or FLINT_AND_STEEL can only be merged with itself or ENCHANTED_BOOK
		if (getGroupIndex(GRP_UNGROUPED, M1) != null) {
			if (M2.equals(Material.ENCHANTED_BOOK)) {
				return true;
			} else if (M1.equals(M2)) {
				return true;
			}
		}
		
		// FISHING_ROD and CARROT_STICK can only be merged with itself or ENCHANTED_BOOK,
		// but a FISHING_ROD can be merged into a CARROT_STICK 
		if (getGroupIndex(GRP_FISHING_RODS, M1) != null) {
			if (M2.equals(Material.ENCHANTED_BOOK)) {
				return true;
			} else if (M1.equals(M2)) {
				return true;
			} else if (getGroupIndex(GRP_FISHING_RODS, M2) != null) {
				return M1.equals(Material.CARROT_STICK) && M2.equals(Material.FISHING_ROD);
			}
		}
		
		// if M1 is in a group
		Integer m1gindex = getGroupIndex(MGRP_TOOLS, M1);
		if (m1gindex != null) {
			// check if M2 is an enchanted book
			if (M2.equals(Material.ENCHANTED_BOOK)) {
				return true;
			} else {
				// if M2 is in the same group
				Integer m2index = getGroupIndex(MGRP_TOOLS[m1gindex], M2);
				if (m2index != null) {
					// get the index of M1 in the group
					int m1index = getGroupIndex(MGRP_TOOLS[m1gindex], M1);
					// if M2 is not better then M1
					if (!(m2index > m1index)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
