package me.teamalpha5441.mcplugins.tasuperuser;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TASuperUser extends JavaPlugin implements Listener {

	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		getCommand("su").setExecutor(new SuCommand(this));
		getCommand("runas").setExecutor(new RunAsCommand());
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent evt) {
		if (getConfig().getBoolean("deop-on-join", true)) {
			evt.getPlayer().setOp(false);
		}
	}
}
