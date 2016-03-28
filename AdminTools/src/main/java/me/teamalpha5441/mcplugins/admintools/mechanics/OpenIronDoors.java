package me.teamalpha5441.mcplugins.admintools.mechanics;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.teamalpha5441.mcplugins.admintools.StaticVars;

public class OpenIronDoors implements Listener {

	@EventHandler (priority = EventPriority.HIGH)
	@SuppressWarnings("deprecation")
	public void onPlayerInteract(PlayerInteractEvent evt) {
		if (!evt.isCancelled() && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = evt.getClickedBlock();
			if (block.getType() == Material.IRON_DOOR_BLOCK || block.getType() == Material.IRON_TRAPDOOR) {
				if (evt.getPlayer().hasPermission(StaticVars.PERM_OPEN_IRON_DOORS)) {
					if (block.getType() == Material.IRON_DOOR_BLOCK) {
						if ((block.getData() & 0x8) > 0) { // is upper block? (0x8 set)
							//Get lower block (contains the open/close bit)
							block = block.getRelative(BlockFace.DOWN);
						}
					}
					// Toggle open/close bit
					block.setData((byte)(block.getData() ^ 0x4));
					// Play sound effect
					Sound doorSound;
					boolean isOpenNow = (block.getData() & 0x4) > 0;
					if (block.getType() == Material.IRON_DOOR_BLOCK) {
						doorSound = isOpenNow ? Sound.BLOCK_IRON_DOOR_OPEN : Sound.BLOCK_IRON_DOOR_CLOSE;
					} else {
						doorSound = isOpenNow ? Sound.BLOCK_IRON_TRAPDOOR_OPEN : Sound.BLOCK_IRON_TRAPDOOR_CLOSE;
					}
					block.getWorld().playSound(block.getLocation(), doorSound, 1f, 1f);
				}
			}
		}
	}
}
