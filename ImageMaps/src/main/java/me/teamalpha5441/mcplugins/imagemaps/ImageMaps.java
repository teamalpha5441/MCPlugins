package me.teamalpha5441.mcplugins.imagemaps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import me.teamalpha5441.mcplugins.imagemaps.loader.LoadedImage;

public class ImageMaps extends JavaPlugin implements Listener {

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onEnable() {
		Set<String> imageKeys = getConfig().getConfigurationSection("images").getKeys(false);
		getLogger().log(Level.INFO, "Loading " + imageKeys.size() + " map images...");

		for (String imageKey : imageKeys) {
			short firstMapId = Short.parseShort(imageKey);
			int mapCountX = getConfig().getInt("images." + firstMapId + ".w");
			int mapCountY = getConfig().getInt("images." + firstMapId + ".h");
			for (short i = 0; i < mapCountX * mapCountY; i++) {
				short mapId = (short)(firstMapId + i);
				MapView map = Bukkit.getServer().getMap((short)(firstMapId + i));
				try {
					File imageFile = new File(getDataFolder(), "image_" + mapId + ".png");
					BufferedImage image = ImageIO.read(imageFile);
					setMapImage(map, image);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		getCommand("imgmap").setExecutor(new ImgMapCommand(this));
	}

	private void setMapImage(MapView map, BufferedImage image) {
		for (MapRenderer r : map.getRenderers()) {
			map.removeRenderer(r);
		}
		map.addRenderer(new ImageRenderer(image));
	}

	@SuppressWarnings("deprecation")
	short createMaps(LoadedImage loadedImage) throws IOException {
		World world = Bukkit.getWorlds().get(0); // get main world
		BufferedImage[] images = loadedImage.getImages();

		// create first map
		MapView firstMap = Bukkit.getServer().createMap(world);

		// maybe use createSection(..)? performance?
		String cfgBase = "images." + firstMap.getId();
		getConfig().set(cfgBase + ".src", loadedImage.getImageSource());
		getConfig().set(cfgBase + ".w", loadedImage.getMapCountX());
		getConfig().set(cfgBase + ".h", loadedImage.getMapCountY());
		saveConfig();

		for (int i = 0; i < images.length; i++) {
			MapView map = i < 1 ? firstMap : Bukkit.getServer().createMap(world);
			setMapImage(map, images[i]);

			File imageFile = new File(getDataFolder(), "image_" + map.getId() + ".png");
			ImageIO.write(images[i], "PNG", imageFile);
		}

		return firstMap.getId();
	}

	void deleteMaps(short ID) throws IOException {
		String cfgBase = "images." + ID;
		int w = getConfig().getInt(cfgBase + ".w");
		int h = getConfig().getInt(cfgBase + ".h");

		getConfig().set(cfgBase, null);
		saveConfig();

		for (int i = 0; i < w * h; i++) {
			int currentID = ID + i;

			File imageFile = new File(getDataFolder(), "image_" + currentID + ".png");
			imageFile.delete();
		}
	}
}
