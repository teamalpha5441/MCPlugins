package me.teamalpha5441.mcplugins.takickmsg;

import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class TAKickMsg extends JavaPlugin implements Listener {
	private String message;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		message = getConfig().getString("message", ChatColor.RED + "ERROR");
		message = translateColorCodes(message);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent evt) {
		if (evt.getPlayer().isOp()) {
			getLogger().log(Level.INFO, "OP not kicked");
		} else {
			UUID uuid = evt.getPlayer().getUniqueId();
			String name = evt.getPlayer().getName();
			getLogger().log(Level.INFO, "Kicked " + name + "("+ uuid.toString() + ", " + evt.getAddress().getHostAddress() + ")");
			evt.disallow(Result.KICK_OTHER, message);
		}
	}

	public static String translateColorCodes(String string) {
		string = Pattern.compile("(?i)&([0-9A-F])").matcher(string).replaceAll("\u00A7$1");
		string = Pattern.compile("(?i)&([K])").matcher(string).replaceAll("\u00A7$1");
		string = Pattern.compile("(?i)&([L])").matcher(string).replaceAll("\u00A7$1");
		string = Pattern.compile("(?i)&([M])").matcher(string).replaceAll("\u00A7$1");
		string = Pattern.compile("(?i)&([N])").matcher(string).replaceAll("\u00A7$1");
		string = Pattern.compile("(?i)&([O])").matcher(string).replaceAll("\u00A7$1");
		string = Pattern.compile("(?i)&([R])").matcher(string).replaceAll("\u00A7$1");
		return Pattern.compile("(?i)&([S])").matcher(string).replaceAll("&");
	}
}
