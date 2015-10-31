package me.teamalpha5441.mcplugins.tadb;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

class SQLCommand implements CommandExecutor {

	private TADB base;
	
	public SQLCommand(TADB base) {
		this.base = base;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!base.getConfig().getBoolean("enable-sql-command", false)) {
			sender.sendMessage(ChatColor.RED + "SQL command is not enabled");
			return true;
		} else if (args.length > 0) {
			String query = stringJoin(Arrays.asList(args), " ");
			try {
				ResultSet result = Database.getDatabase().executeQuery(query);
				ResultSetMetaData rsMeta = result.getMetaData();
				//print columns
				while (result.next()) {
					String line = ChatColor.RED + (ChatColor.BOLD + (result.getRow() + " "));
					for (int i = 1; i < rsMeta.getColumnCount() + 1; i++) {
						if (i > 1) {
							line += ChatColor.RED + " - ";
						}
						line += ChatColor.RESET + result.getString(i);
					}
					sender.sendMessage(line);
				}
				return true;
			} catch (Exception ex) {
				sender.sendMessage(ChatColor.RED + "SQL Error");
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "No query given");
			return false;
		}
	}
	
	public static String stringJoin(Collection<?> s, String delimiter) {
		StringBuilder builder = new StringBuilder();
		Iterator<?> iter = s.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (!iter.hasNext()) {
				break;
			} else {
				builder.append(delimiter);
			}
		}
		return builder.toString();
	}
}