package me.teamalpha5441.mcplugins.tamerge;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import me.teamalpha5441.mcplugins.tamerge.core.Helper;

public class TAMerge extends JavaPlugin {

	public int cfgCostMerge = 0;
	public int cfgCostExtract = 0;
	public HashMap<Enchantment, Integer> levelLimits;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		cfgCostMerge = getConfig().getInt("cost.merge", cfgCostMerge);
		cfgCostExtract = getConfig().getInt("cost.extract", cfgCostExtract);

		levelLimits = new HashMap<Enchantment, Integer>();
		ConfigurationSection levelLimitsSection = getConfig().getConfigurationSection("level_limits");
		Set<String> enchantmentKeys = levelLimitsSection.getKeys(false);
		for (String enchantmentKey : enchantmentKeys) {
			Enchantment enchantment = Helper.getEnchantmentByName(enchantmentKey);
			levelLimits.put(enchantment, levelLimitsSection.getInt(enchantmentKey));
		}

		getCommand("merge").setExecutor(new MergeCommand(this));
		getCommand("extract").setExecutor(new ExtractCommand(this));
	}
}
