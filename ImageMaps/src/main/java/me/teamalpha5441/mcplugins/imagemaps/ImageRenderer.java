package me.teamalpha5441.mcplugins.imagemaps;

import java.awt.image.BufferedImage;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageRenderer extends MapRenderer {

	private BufferedImage image;
	private boolean rendered;
	
	public ImageRenderer(BufferedImage image) {
		this.image = image;
		this.rendered = false;
	}
	
	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		if (!this.rendered) {
			canvas.drawImage(0, 0, image);
			this.rendered = true;
		}
	}
}
