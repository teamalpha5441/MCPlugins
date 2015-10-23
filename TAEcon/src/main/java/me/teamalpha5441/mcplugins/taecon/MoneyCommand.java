package me.teamalpha5441.mcplugins.taecon;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

	private final TAEcon base;
	
	public MoneyCommand(TAEcon base) {
		this.base = base;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1 && sender instanceof Player) {
			get(sender, ((Player)sender).getName());
		} else if (args[0].equals("pay") && sender instanceof Player) {
			if (args.length == 3) {
				if (sender.hasPermission("taecon.pay")) {
					int amount = parseInt(args[2]);
					successMessage(sender, base.payPlayer(((Player)sender).getName(), args[1], amount));
				} else {
					noPermMessage(sender);
				}
			} else {
				usage(sender);
			}
		} else if (args[0].equals("get")) {
			if (args.length == 2) {
				if (sender.hasPermission("taecon.admin")) {
					get(sender, args[1]);
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
					successMessage(sender, base.setBalance(args[1], amount));
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
					successMessage(sender, base.addBalance(args[1], amount));
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
					successMessage(sender, base.removeBalance(args[1], amount));
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
	
	private void noPermMessage(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command");
	}
	
	private void successMessage(CommandSender sender, Boolean success) {
		sender.sendMessage(success ? ChatColor.GREEN + "Success" : ChatColor.RED + "ERROR");
	}
	
	private void get(CommandSender sender, String player) {
		int balance = base.getBalance(player);
		String currencyName = base.getCurrencyName(balance != 1);
		sender.sendMessage(balance + " " + ChatColor.YELLOW + currencyName);
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