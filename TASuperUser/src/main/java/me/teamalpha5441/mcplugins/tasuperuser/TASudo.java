package me.teamalpha5441.mcplugins.tasuperuser;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TASudo extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("su").setExecutor(new SuCommand(this));
		getCommand("runas").setExecutor(new RunAsCommand(this));
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public Boolean checkPassword(String password) {
		List<String> cPasswords = getConfig().getStringList("passwords");
		for (String cPassword : cPasswords) {
			if (password.equals(cPassword)) {
				return true;
			}
		}
		return false;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent evt) {
		if (getConfig().getBoolean("deop-on-join", true)) {
			evt.getPlayer().setOp(false);
		}
	}
	
	public void msgAuthenticationFailed(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Authentication failed");
	}

	public void msgPlayerNotFound(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Player not found");
	}
	
	public void msgOnlyPlayerCanExecute(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Only a player can execute this command");
	}
	
	public void msgMustBeOp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You must be op to use this command");
	}
	
	public void msgNowOp(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "You are now op");
	}
	
	public void msgCommandExecuted(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "Command executed");
	}
}