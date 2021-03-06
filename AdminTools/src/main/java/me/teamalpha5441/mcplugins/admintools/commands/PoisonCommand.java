package me.teamalpha5441.mcplugins.admintools.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.teamalpha5441.mcplugins.admintools.PlayerCommand;
import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class PoisonCommand extends PlayerCommand {

	@Override
	public void onPlayerCommand(CommandSender sender, Player player) {
		PotionEffect effect = new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 2);
		player.addPotionEffect(effect);
		if (sender != player) {
			sender.sendMessage(StaticVars.MESSAGE_COMMAND_EXECUTED);
		}
	}
}
