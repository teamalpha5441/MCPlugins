package me.teamalpha5441.mcplugins.imagemaps;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
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
		if (args.length < 1) {
			return sendHelpMessage(sender);
		} else {
			String subCommand = args[0].toLowerCase();

			if (subCommand.equals("help")) {
				if (args.length > 1) {
					sender.sendMessage(ChatColor.RED + "Too much arguments");
				}
				return sendHelpMessage(sender);
			} else if (subCommand.equals("new")) {
				if (args.length == 4) {
					URL imageURL = null;
					try {
						imageURL = new URL(args[1]);
					} catch (MalformedURLException ex) {
						sender.sendMessage(ChatColor.RED + "Invalid image URL");
						return sendHelpMessage(sender);
					}

					short maxWidth;
					short maxHeight;
					try {
						String[] sizeParts = args[2].split("x");
						if (sizeParts.length != 2) {
							throw new NumberFormatException();
						} else {
							maxWidth = Short.parseShort(sizeParts[0]);
							maxHeight = Short.parseShort(sizeParts[1]);
						}
					} catch (NumberFormatException ex) {
						sender.sendMessage(ChatColor.RED + "Invalid maximum size");
						return sendHelpMessage(sender);
					}

					boolean fill = Boolean.parseBoolean(args[3]);

					try {
						LoadedImage loadedImage = ImageLoader.loadImage(imageURL, maxWidth, maxHeight, fill);
						short id = base.createMaps(loadedImage);
						sendImageMapInfo(sender, id);
						return true;
					} catch (IOException ex) {
						sender.sendMessage(ChatColor.RED + ex.getMessage());
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Please provide four arguments");
					return sendHelpMessage(sender);
				}
			} else if (subCommand.equals("info")) {
				if (args.length == 2) {
					short id;
					try {
						id = Short.parseShort(args[1]);
					} catch (NumberFormatException ex) {
						sender.sendMessage(ChatColor.RED + "Couldn't parse ID");
						return sendHelpMessage(sender);
					}

					sendImageMapInfo(sender, id);
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Please provide two arguments");
					return sendHelpMessage(sender);
				}
			} else if (subCommand.equals("get")) {
				if (sender instanceof Player) {
					Player player = (Player)sender;
					if (args.length == 3) {
						short id;
						try {
							id = Short.parseShort(args[1]);
						} catch (NumberFormatException ex) {
							sender.sendMessage(ChatColor.RED + "Couldn't parse ID");
							return sendHelpMessage(sender);
						}

						short count;
						try {
							count = Short.parseShort(args[2]);
						} catch (NumberFormatException ex) {
							sender.sendMessage(ChatColor.RED + "Couldn't parse count");
							return sendHelpMessage(sender);
						}

						for (short i = 0; i < count; i++) {
							ItemStack stack = new ItemStack(Material.MAP);
							stack.setDurability((short)(id + i));
							player.getInventory().addItem(stack);
						}
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Please provide three arguments");
						return sendHelpMessage(sender);
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You must be a player");
					return true;
				} 
			} else if (subCommand.equals("delete")) {
					if (args.length == 2) {
						short id;
						try {
							id = Short.parseShort(args[1]);
						} catch (NumberFormatException ex) {
							sender.sendMessage(ChatColor.RED + "Couldn't parse ID");
							return sendHelpMessage(sender);
						}

						try {
							base.deleteMaps(id);
							sender.sendMessage(ChatColor.GREEN + "ImageMap deleted");
						} catch (IOException ex) {
							sender.sendMessage(ChatColor.RED + ex.getMessage());
						}
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Please provide two arguments");
						return sendHelpMessage(sender);
					}
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown subcommand");
				return sendHelpMessage(sender);
			}
		}
	}

	public boolean sendHelpMessage(CommandSender sender) {
		sender.sendMessage("/imgmap help");
		sender.sendMessage("/imgmap new <URL> <max WxH> <Fill>");
		sender.sendMessage("/imgmap info <ID>");
		sender.sendMessage("/imgmap get <ID> <Count>");
		sender.sendMessage("/imgmap delete <ID>");
		return true;
	}

	public void sendImageMapInfo(CommandSender sender, short ID) {
		ConfigurationSection section = base.getConfig().getConfigurationSection("images." + ID);
		if (section != null) {
			String source = section.getString("src");
			int mapCountX = section.getInt("w");
			int mapCountY = section.getInt("h");
			sender.sendMessage(ChatColor.AQUA + "ImageMap ID: " + ChatColor.YELLOW + ID);
			sender.sendMessage(ChatColor.AQUA + "Size: " + ChatColor.YELLOW + mapCountX +
					ChatColor.AQUA + "x" + ChatColor.YELLOW + mapCountY +
					ChatColor.AQUA + " Count: " + ChatColor.YELLOW + (mapCountX * mapCountY));
			sender.sendMessage(ChatColor.AQUA + "Source: " + ChatColor.YELLOW + source);
		} else {
			sender.sendMessage(ChatColor.RED + "ImageMap " + ID + " not found");
		}
	}
}
