package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class FreezeCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		PotionEffect[] effects = new PotionEffect[] {
				new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 7),
				new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 5),
				new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128),
				new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 100)
		};
		for (PotionEffect effect : effects) {
			player.addPotionEffect(effect);
		}
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}