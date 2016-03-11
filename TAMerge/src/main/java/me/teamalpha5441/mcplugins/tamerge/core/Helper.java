package me.teamalpha5441.mcplugins.tamerge.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class Helper {

	private static final HashMap<String, Enchantment> _EnchantmentNameMap = new HashMap<String, Enchantment>();
	
	static {
		_EnchantmentNameMap.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
		_EnchantmentNameMap.put("fire_protection", Enchantment.PROTECTION_FIRE);
		_EnchantmentNameMap.put("feather_falling", Enchantment.PROTECTION_FALL);
		_EnchantmentNameMap.put("blast_protection", Enchantment.PROTECTION_EXPLOSIONS);
		_EnchantmentNameMap.put("projectile_protection", Enchantment.PROTECTION_PROJECTILE);
		_EnchantmentNameMap.put("respiration", Enchantment.OXYGEN);
		_EnchantmentNameMap.put("aqua_affinity", Enchantment.WATER_WORKER);
		_EnchantmentNameMap.put("thorns", Enchantment.THORNS);
		_EnchantmentNameMap.put("depth_strider", Enchantment.DEPTH_STRIDER);
		_EnchantmentNameMap.put("frost_walker", Enchantment.FROST_WALKER);
		_EnchantmentNameMap.put("sharpness", Enchantment.DAMAGE_ALL);
		_EnchantmentNameMap.put("smite", Enchantment.DAMAGE_UNDEAD);
		_EnchantmentNameMap.put("bane_of_arthropods", Enchantment.DAMAGE_ARTHROPODS);
		_EnchantmentNameMap.put("knockback", Enchantment.KNOCKBACK);
		_EnchantmentNameMap.put("fire_aspect", Enchantment.FIRE_ASPECT);
		_EnchantmentNameMap.put("looting", Enchantment.LOOT_BONUS_MOBS);
		_EnchantmentNameMap.put("efficiency", Enchantment.DIG_SPEED);
		_EnchantmentNameMap.put("silk_touch", Enchantment.SILK_TOUCH);
		_EnchantmentNameMap.put("unbreaking", Enchantment.DURABILITY);
		_EnchantmentNameMap.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
		_EnchantmentNameMap.put("power", Enchantment.ARROW_DAMAGE);
		_EnchantmentNameMap.put("punch", Enchantment.ARROW_KNOCKBACK);
		_EnchantmentNameMap.put("flame", Enchantment.ARROW_FIRE);
		_EnchantmentNameMap.put("infinity", Enchantment.ARROW_INFINITE);
		_EnchantmentNameMap.put("luck_of_the_sea", Enchantment.LUCK);
		_EnchantmentNameMap.put("lure", Enchantment.LURE);
		_EnchantmentNameMap.put("mending", Enchantment.MENDING);
	}
	
	public static void giveOrDropItem(Player player, ItemStack stack) {
		giveOrDropItems(player, new ItemStack[] { stack });
	}
	
	public static void giveOrDropItems(Player player, List<ItemStack> stacks) {
		ItemStack[] array = new ItemStack[stacks.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = stacks.get(i);
		}
		giveOrDropItems(player, array);
	}
	
	public static void giveOrDropItems(Player player, ItemStack[] stacks) {
		HashMap<Integer, ItemStack> notAdded = player.getInventory().addItem(stacks);
		if (notAdded.size() > 0) {
			World world = player.getWorld();
			Location loc = player.getLocation();
			for (ItemStack stack : notAdded.values()) {
				world.dropItem(loc, stack);
			}
			player.sendMessage(ChatColor.RED + "Inventory full, dropped some items");
		}
	}

	public static ItemStack getSlot(Player player, int index) {
		ItemStack stack = player.getInventory().getItem(index);
		if (stack != null) {
			if (!stack.getType().equals(Material.AIR)) {
				if (stack.getAmount() != 0) {
					return stack;
				}	
			}
		}
		return null;
	}

	public static Map<Enchantment, Integer> getEnchantments(ItemStack I) {
		if (I.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta)I.getItemMeta();
			return meta.getStoredEnchants();
		} else {
			return I.getEnchantments();
		}
	}
	
	public static void setEnchantments(ItemStack I, Map<Enchantment, Integer> Enchantments) {
		if (I.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta)I.getItemMeta();
		    for (Map.Entry<Enchantment, Integer> entry : Enchantments.entrySet()) {
		    	meta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
		    }
		    I.setItemMeta(meta);
		} else {
			I.addUnsafeEnchantments(Enchantments);
		}
	}
	
	public static void removeEnchantments(ItemStack I) {
		if (I.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta)I.getItemMeta();
			Set<Enchantment> enchs = meta.getStoredEnchants().keySet();
			for (Enchantment ench : enchs) {
				meta.removeStoredEnchant(ench);
			}
			I.setItemMeta(meta);
		} else {
			Set<Enchantment> enchs = I.getEnchantments().keySet();
			for (Enchantment ench : enchs) {
				I.removeEnchantment(ench);
			}
		}
	}
	
	public static Enchantment getEnchantmentByName(String name) {
		return _EnchantmentNameMap.get(name);
	}
}