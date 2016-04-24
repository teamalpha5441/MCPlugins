package me.teamalpha5441.mcplugins.taeggs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class TAEggs extends JavaPlugin implements Listener {

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	@SuppressWarnings("deprecation")
	public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
		Player player = evt.getPlayer();
		Entity entity = evt.getRightClicked();

		int wandMaterialID = getConfig().getInt("wand-item-id", 369);
		Material wandMaterial = Material.getMaterial(wandMaterialID);
		int levelCost = getConfig().getInt("level-cost", 3);
		EntityType entityType = entity.getType();
		Location location = entity.getLocation();
		List<Integer> allowedEggIDs = getConfig().getIntegerList("allowed-egg-ids");

		if (player.getItemInHand().getType().equals(wandMaterial)) {

			if (!player.hasPermission("taeggs.capture")) {
				player.sendMessage(ChatColor.RED + "You don't have the permission to capture mobs");
			} else if (player.getLevel() < levelCost) {
				player.sendMessage(ChatColor.RED + "You don't have enough levels to capture mobs");
			} else if (!integerInList(allowedEggIDs, entityType.getTypeId())) {
				player.sendMessage(ChatColor.RED + "You cannot capture this entity");
			} else if (!checkWorldGuard(player, location)) {
				player.sendMessage(ChatColor.RED + "You cannot capture this mob in it's current region");
			} else {
				SpawnEgg spawnEgg = new SpawnEgg(entityType);
				ItemStack spawnEggStack = spawnEgg.toItemStack(1);
				World world = entity.getWorld();
				world.dropItem(location, spawnEggStack);
				//TODO Play sound
				if (entity instanceof Horse) {
					Horse horse = (Horse)entity;
					HorseInventory horseInv = horse.getInventory();
					ItemStack saddle = horseInv.getSaddle();
					if (saddle != null) {
						world.dropItem(location, saddle);
					}
					ItemStack armor = horseInv.getArmor();
					if (armor != null) {
						world.dropItem(location, armor);
					}
					if (horse.isCarryingChest()) {
						for (ItemStack chestItem : horseInv.getContents()) {
							if (chestItem != null) {
								world.dropItem(location, chestItem);
							}
						}
						world.dropItem(location, new ItemStack(Material.CHEST, 1));
					}
				} else if (entity instanceof Pig) {
					if (((Pig)entity).hasSaddle()) {
						world.dropItem(location, new ItemStack(Material.SADDLE, 1));
					}
				}
				entity.remove();
				player.setLevel(player.getLevel() - levelCost);
			}
		}
	}

	private boolean integerInList(List<Integer> list, int integer) {
		for (Integer listInt : list) {
			if (listInt == integer) {
				return true;
			}
		}
		return false;
	}

	private boolean checkWorldGuard(Player player, Location location) {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if (plugin != null) {
			if (plugin instanceof WorldGuardPlugin) {
				return ((WorldGuardPlugin)plugin).canBuild(player, location);
			}
		}
		return true; //No WorldGuard -> Assume that the player is allowed to use TAEggs
	}
}
