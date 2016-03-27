package me.teamalpha5441.mcplugins.imagemaps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

public class ImageMaps extends JavaPlugin implements Listener {

	@Override
	public void onLoad() {
		getDataFolder().mkdir();
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void onEnable() {
		File[] imageFiles = getDataFolder().listFiles();
		getLogger().log(Level.INFO, "Loading " + imageFiles.length + " map images...");
		for (File imageFile : imageFiles) {
			String imageFileName = imageFile.getName();
			// extract ID from file name (e.g. mapimg_123.png")
			short mapId = Short.parseShort(imageFileName.substring(7, imageFileName.length() - 4));
			getLogger().log(Level.INFO, "Loading iamge for map " + mapId + "...");
			
			MapView map = Bukkit.getServer().getMap(mapId);
			try {
				BufferedImage image = ImageIO.read(imageFile);
				setMapImage(map, image);
			} catch (IOException ex) {
				ex.printStackTrace();
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
	ItemStack[] createMaps(BufferedImage[] images) throws IOException {
		World world = Bukkit.getWorlds().get(0); // get main world
		ItemStack[] maps = new ItemStack[images.length];
		
		for (int i = 0; i < images.length; i++) {
			MapView map = Bukkit.getServer().createMap(world);
			setMapImage(map, images[i]);
			
			File imageFile = new File(getDataFolder(), "mapimg_" + map.getId() + ".png");
			ImageIO.write(images[i], "PNG", imageFile);
			
			maps[i] = new ItemStack(Material.MAP);
			maps[i].setDurability(map.getId());
		}
		
		return maps;
	}
}
