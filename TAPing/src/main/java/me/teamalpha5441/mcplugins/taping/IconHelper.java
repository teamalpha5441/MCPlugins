package me.teamalpha5441.mcplugins.taping;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.CachedServerIcon;

public class IconHelper {

	public static CachedServerIcon loadIconFromFile(Plugin plugin, File file) {
		//TODO Binary file loading
		try {
			return plugin.getServer().loadServerIcon(file);
		} catch (Exception ex) {
			return null;
		}
	}
	
	//TODO Convert image to binary file
}