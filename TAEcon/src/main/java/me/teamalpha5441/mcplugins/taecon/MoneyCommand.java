package me.teamalpha5441.mcplugins.taecon;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

	private final TAEcon base;
	
	public MoneyCommand(TAEcon base) {
		this.base = base;
	}
	
	@SuppressWarnings("deprecation")
	private OfflinePlayer getPlayer(String playerName) {
		return base.getServer().getOfflinePlayer(playerName);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			if (sender instanceof Player) {
				get(sender, (Player)sender);
			} else {
				usage(sender);
			}
		} else if (args[0].equals("pay") && sender instanceof Player) {
			if (args.length == 3) {
				if (sender.hasPermission("taecon.pay")) {
					int amount = parseInt(args[2]);
					OfflinePlayer paidPlayer = getPlayer(args[1]);
					if (base.payPlayer((Player)sender, paidPlayer, amount)) {
						sender.sendMessage(ChatColor.GREEN + "Successfully paid " + formatAmount(amount));
						if (paidPlayer.isOnline()) {
							((Player)paidPlayer).sendMessage(ChatColor.GREEN + "You received " + formatAmount(amount) + " from " + ChatColor.YELLOW + ((Player)sender).getDisplayName());
						}
					} else {
						errorMessage(sender);
					}
				} else {
					noPermMessage(sender);
				}
			} else {
				usage(sender);
			}
		} else if (args[0].equals("get")) {
			if (args.length == 2) {
				if (sender.hasPermission("taecon.admin")) {
					get(sender, getPlayer(args[1]));
				} else {
					noPermMessage(sender);
				}
			} else {
				usage(sender);
			}
		} else if (args[0].equals("set")) {
			if (args.length == 3) {
				if (sender.hasPermission("taecon.admin")) {
					int amount = parseInt(args[2]);
					if (base.setBalance(getPlayer(args[1]), amount)) {
						sender.sendMessage(ChatColor.GREEN + "Successfully set balance to " + formatAmount(amount));
					} else {
						errorMessage(sender);
					}
				} else {
					noPermMessage(sender);
				}
			} else {
				usage(sender);
			}
		} else if (args[0].equals("add") || args[0].equals("give")) {
			if (args.length == 3) {
				if (sender.hasPermission("taecon.admin")) {
					int amount = parseInt(args[2]);
					if (base.addBalance(getPlayer(args[1]), amount)) {
						sender.sendMessage(ChatColor.GREEN + "Successfully raised balance by " + formatAmount(amount));
					} else {
						errorMessage(sender);
					}
				} else {
					noPermMessage(sender);
				}
			} else {
				usage(sender);
			} 
		} else if (args[0].equals("take")) {
			if (args.length == 3) {
				if (sender.hasPermission("taecon.admin")) {
					int amount = parseInt(args[2]);
					if (base.removeBalance(getPlayer(args[1]), amount)) {
						sender.sendMessage(ChatColor.GREEN + "Successfully lowered balance by " + formatAmount(amount));
					} else {
						errorMessage(sender);
					}
				} else {
					noPermMessage(sender);
				}
			} else {
				usage(sender);
			}
		} else if (args[0].equals("reload")) {
			if (sender.hasPermission("taecon.admin")) {
				base.reloadPlugin();
				sender.sendMessage(ChatColor.GREEN + "TAEcon reloaded");
			} else {
				noPermMessage(sender);
			}
		} else {
			usage(sender);
		}
		return true;
	}
	
	private int parseInt(String s) {
		try { 
			return Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	private String formatAmount(int amount) {
		return "" + ChatColor.YELLOW + amount + " " + ChatColor.AQUA + base.getCurrencyName(amount != 1);
	}
	
	private void noPermMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command");
	}
	
	private void errorMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "An error occured");
	}
	
	private void get(CommandSender sender, OfflinePlayer player) {
		int amount = base.getBalance(player);
		sender.sendMessage(ChatColor.GREEN + "You own " + formatAmount(amount));
	}
	
	private void usage(CommandSender sender) {
		if (sender instanceof Player) {
			sender.sendMessage(ChatColor.GREEN + "/money" + ChatColor.RESET + " Gets your account balance");
			if (sender.hasPermission("taecon.pay") || sender.hasPermission("taecon.admin")) {
				sender.sendMessage(ChatColor.GREEN + "/money pay <player> <amount>" + ChatColor.RESET + " Pay another player");
			}
		}
		if (sender.hasPermission("taecon.admin")) {
			sender.sendMessage(ChatColor.YELLOW + "/money get <player>" + ChatColor.RESET + " Gets a players account balance");
			sender.sendMessage(ChatColor.YELLOW + "/money set <player> <amount>" + ChatColor.RESET + " Sets a players account balance");
			sender.sendMessage(ChatColor.YELLOW + "/money add|give <player> <amount>" + ChatColor.RESET + " Gives money to a player");
			sender.sendMessage(ChatColor.YELLOW + "/money take <player> <amount>" + ChatColor.RESET + " Takes money from a player");
			sender.sendMessage(ChatColor.YELLOW + "/money reload" + ChatColor.RESET + " Reloads the config and all accounts");
		}
	}
}