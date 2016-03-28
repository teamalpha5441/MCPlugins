package me.teamalpha5441.mcplugins.taeggs;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
import org.bukkit.inventory.EquipmentSlot;
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
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
		Player player = evt.getPlayer();
		
		if (evt.getHand() == EquipmentSlot.HAND) {
			Material inHandMaterial = player.getInventory().getItemInMainHand().getType();

			String wandMaterialName = getConfig().getString("wand-item");
			Material wandMaterial = Material.getMaterial(wandMaterialName);
			
			if (inHandMaterial == wandMaterial) {
				if (player.getInventory().getItemInOffHand().getType() != Material.AIR) {
					player.sendMessage(ChatColor.RED + "Your second hand must be empty to capture mobs");
					return;
				}
				
				if (!player.hasPermission("taeggs.capture")) {
					player.sendMessage(ChatColor.RED + "You don't have the permission to capture mobs");
					return;
				}

				int levelCost = getConfig().getInt("level-cost", 3);
				boolean isCreative = player.getGameMode().equals(GameMode.CREATIVE);
				if (player.getLevel() < levelCost && !isCreative) {
					player.sendMessage(ChatColor.RED + "You don't have enough levels to capture mobs (" + levelCost + " needed)");
					return;
				}

				Entity entity = evt.getRightClicked();
				EntityType entityType = entity.getType();
				List<Integer> allowedEggIDs = getConfig().getIntegerList("allowed-egg-ids");
				if (!integerInList(allowedEggIDs, entityType.getTypeId())) {
					player.sendMessage(ChatColor.RED + "You cannot capture this entity");
					return;
				}
				
				Location location = entity.getLocation();
				if (!checkWorldGuard(player, location)) {
					player.sendMessage(ChatColor.RED + "You cannot capture this mob in it's current region");
					return;
				}
				
				SpawnEgg spawnEgg = new SpawnEgg(entityType);
				ItemStack spawnEggStack = spawnEgg.toItemStack(1);
				World world = location.getWorld();

				world.dropItem(location, spawnEggStack);
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
				//TODO Play sound
				if (!isCreative) {
					player.setLevel(player.getLevel() - levelCost);
				}
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
