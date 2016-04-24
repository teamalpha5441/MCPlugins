package me.teamalpha5441.mcplugins.imagemaps.loader;

import java.awt.image.BufferedImage;
import java.net.URL;

public class LoadedImage {
	private BufferedImage[] images;
	private URL imageUrl;
	private int mapCountX, mapCountY;

	public LoadedImage(BufferedImage[] images, URL imageUrl, int mapCountX, int mapCountY) {
		this.images = images;
		this.imageUrl = imageUrl;
		this.mapCountX = mapCountX;
		this.mapCountY = mapCountY;
	}

	public BufferedImage[] getImages() {
		return this.images;
	}

	public URL getImageUrl() {
		return this.imageUrl;
	}

	public int getMapCountX() {
		return this.mapCountX;
	}

	public int getMapCountY() {
		return this.mapCountY;
	}
}
