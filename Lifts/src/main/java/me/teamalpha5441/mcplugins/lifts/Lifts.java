package me.teamalpha5441.mcplugins.lifts;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Lifts extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent evt) {
		if (evt.getLine(1).equals(StaticVars.LIFT_STRING)) {
			Player player = evt.getPlayer();
			if (!player.hasPermission(StaticVars.PERM_LIFTS_CREATE)) {
				player.sendMessage(ChatColor.RED + "You don't have the permission to create lifts");
				evt.setCancelled(true);
			} else {
				player.sendMessage(ChatColor.GREEN + "Lift created successfully");
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent evt) {
		Block block = evt.getClickedBlock();
		if (block != null) {
			if (isSign(block)) {
				Sign sign = (Sign)block.getState();
				if (sign.getLine(1).equals(StaticVars.LIFT_STRING)) {
					Player player = evt.getPlayer();
					if (player.hasPermission(StaticVars.PERM_LIFTS_USE)) {
						if (!player.isSneaking()) {
							byte direction = 0;
							if (evt.getAction() == Action.LEFT_CLICK_BLOCK) {
								direction = 1;
							} else if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
								direction = -1;
							} else {
								player.sendMessage(ChatColor.RED + "Invalid action for lift signs");
							}
							if (direction != 0) {
								if (!findLiftSignAndTeleport(player, sign, direction)) {
									player.sendMessage(ChatColor.RED + "Couldn't find other lift signs");
								}
							}
							evt.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}
	
	private boolean findLiftSignAndTeleport(Player player, Sign sign, byte direction) {
		for (int y = sign.getY() + direction; direction > 0 ? y < 256 : y >= 0; y += direction) {
			Block currentBlock = sign.getWorld().getBlockAt(sign.getX(), y, sign.getZ());
			if (isSign(currentBlock)) {
				Sign currentSign = (Sign)currentBlock.getState();
				if (currentSign.getLine(1).equals(StaticVars.LIFT_STRING)) {
					if (allowTeleport(currentSign, player)) {
						Location loc = player.getLocation();
						loc.setY(currentSign.getY() - (currentBlock.getType() == Material.WALL_SIGN ? 1 : 0));
						player.teleport(loc);
						String floorName = currentSign.getLine(2);
						if (floorName.length() > 0) {
							player.sendMessage(ChatColor.GREEN + "Ding Dong! You are now in \"" + floorName + "\"");
						} else {
							player.sendMessage(ChatColor.GREEN + "Ding Dong!");
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isSign(Block block) {
		return block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST;
	}
	
	private boolean allowTeleport(Sign sign, Player player) {
		if (player.hasPermission(StaticVars.PERM_LIFTS_BYPASS)) {
			return true;
		} else if (sign.getLine(0).length() == 0) {
			return true;
		} else {
			//TODO More tests on line 0 (Item name?)
			return true;
		}
	}
}
