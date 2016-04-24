package me.teamalpha5441.mcplugins.imagemaps;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.teamalpha5441.mcplugins.imagemaps.loader.ImageLoader;
import me.teamalpha5441.mcplugins.imagemaps.loader.LoadedImage;

public class ImgMapCommand implements CommandExecutor {

	private ImageMaps base;

	public ImgMapCommand(ImageMaps base) {
		this.base = base;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (args.length < 2) {
				player.sendMessage(ChatColor.RED + "Not enough arguments");
				return false;
			} else if (args.length > 3) {
				player.sendMessage(ChatColor.RED + "Too much arguments");
				return false;
			} else {
				URL imageURL = null;
				try {
					imageURL = new URL(args[0]);
				} catch (MalformedURLException ex) {
					player.sendMessage(ChatColor.RED + "Malformed image URL");
					return false;
				}

				short maxWidth = 1;
				short maxHeight = 1;
				if (args.length > 2) {
					try {
						String[] sizeParts = args[1].split("x");
						if (sizeParts.length != 2) {
							throw new NumberFormatException();
						} else {
							maxWidth = Short.parseShort(sizeParts[0]);
							maxHeight = Short.parseShort(sizeParts[1]);
						}
					} catch (NumberFormatException ex) {
						player.sendMessage(ChatColor.RED + "Couldn't parse max size");
						return false;
					}
				}
				boolean fill = Boolean.parseBoolean(args[2]);

				try {
					LoadedImage loadedImage = ImageLoader.loadImages(imageURL, maxWidth, maxHeight, fill);
					ItemStack[] maps = base.createMaps(loadedImage);
					player.getInventory().addItem(maps);
				} catch (IOException ex) {
					player.sendMessage(ChatColor.RED + ex.getMessage());
					return true;
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player");
		}
		return true;
	}
}
