package me.teamalpha5441.mcplugins.imagemaps.loader;

import java.awt.image.BufferedImage;

public class LoadedImage {
	private BufferedImage[] images;
	private String imageSource;
	private int mapCountX, mapCountY;

	public LoadedImage(BufferedImage[] images, String imageSource, int mapCountX, int mapCountY) {
		this.images = images;
		this.imageSource = imageSource;
		this.mapCountX = mapCountX;
		this.mapCountY = mapCountY;
	}

	public BufferedImage[] getImages() {
		return this.images;
	}

	public String getImageSource() {
		return this.imageSource;
	}

	public int getMapCountX() {
		return this.mapCountX;
	}

	public int getMapCountY() {
		return this.mapCountY;
	}
}
