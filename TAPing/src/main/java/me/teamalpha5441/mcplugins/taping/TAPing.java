package me.teamalpha5441.mcplugins.taping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.CachedServerIcon;

public class TAPing extends JavaPlugin implements Listener {

	private Random _Random = new Random();
	private ArrayList<CachedServerIcon> _Icons;
	private ArrayList<String> _MOTDs;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		this._MOTDs = loadMOTDs();
		this._Icons = loadIcons();
	}

	private ArrayList<String> loadMOTDs() {
		List<String> motds = getConfig().getStringList("motds");
		if (motds != null) {
			if (motds.size() > 0) {
				ArrayList<String> cMotds = new ArrayList<String>();
				for (String motd : motds) {
					String cMotd = TextHelper.translateColorCodes(motd);
					cMotds.add(cMotd);
				}
				getLogger().log(Level.INFO, "Loaded " + cMotds.size() + " MOTDs");
				return cMotds;
			}
		}
		getLogger().log(Level.WARNING, "No MOTDs loaded");
		return null;
	}

	private ArrayList<CachedServerIcon> loadIcons() {
		File pluginDir = getDataFolder();
		List<String> iconFileNames = getConfig().getStringList("icons");
		if (iconFileNames != null) {
			ArrayList<CachedServerIcon> icons = new ArrayList<CachedServerIcon>();
			for (String fileName : iconFileNames) {
				File iconFile = new File(pluginDir, fileName);
				if (iconFile.exists()) {
					try {
						CachedServerIcon icon = getServer().loadServerIcon(iconFile);
						icons.add(icon);
					} catch (Exception ex) {
						getLogger().log(Level.SEVERE, "Couldn't load icon file " + fileName, ex);
					}
				} else {
					getLogger().log(Level.SEVERE, "Icon file '" + fileName + "' not found");
				}
			}
			if (icons.size() > 0) {
				getLogger().log(Level.INFO, "Loaded " + icons.size() + " icons");
				return icons;
			}
		}
		getLogger().log(Level.WARNING, "No icons loaded");
		return null;
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent evt) {
		if (this._MOTDs != null) {
			int randomIndex = _Random.nextInt(this._MOTDs.size());
			String motd = this._MOTDs.get(randomIndex);
			evt.setMotd(motd);
		}
		if (this._Icons != null) {
			int randomIndex = _Random.nextInt(this._Icons.size());
			CachedServerIcon icon = this._Icons.get(randomIndex);
			evt.setServerIcon(icon);
		}
	}
}
