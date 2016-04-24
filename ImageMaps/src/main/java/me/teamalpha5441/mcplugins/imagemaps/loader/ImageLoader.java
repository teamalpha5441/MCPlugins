package me.teamalpha5441.mcplugins.imagemaps.loader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageLoader {

	//TODO Background color support
	public static LoadedImage loadImages(URL imageUrl, int maxWidth, int maxHeight, boolean fill) throws IOException {
		BufferedImage fullSizeImage = ImageIO.read(imageUrl);

		// Calculate new image size so the image can fit on maxWidth x maxHeight

		int width = fullSizeImage.getWidth();
		int height = fullSizeImage.getHeight();
		maxWidth *= 128;
		maxHeight *= 128;
		double ratioWidth = (double)maxWidth / width;
		double ratioHeight = (double)maxHeight / height;

		int mapCountX, mapCountY, newWidth, newHeight, offsetX, offsetY;

		double num = fill ? Math.max(ratioWidth, ratioHeight) : Math.min(ratioWidth, ratioHeight);
		newWidth = (int)(width * num + 0.5);
		newHeight = (int)(height * num + 0.5);

		if (!fill) {
			mapCountX = newWidth / 128;
			if (newWidth % 128 > 0) {
				mapCountX++;
			}
			mapCountY = newHeight / 128;
			if (newHeight % 128 > 0) {
				mapCountY++;
			}
		} else {
			mapCountX = maxWidth / 128;
			mapCountY = maxHeight / 128;
		}
		offsetX = (int)((mapCountX * 128 - newWidth) / 2f + 0.5f);
		offsetY = (int)((mapCountY * 128 - newHeight) / 2f + 0.5f);

		// Resize image

		BufferedImage resizedImage = new BufferedImage(mapCountX * 128, mapCountY * 128, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.drawImage(fullSizeImage, offsetX, offsetY, newWidth, newHeight, null);
		graphics.dispose();

		// Cut image in parts (for each map)

		BufferedImage[] imageParts = new BufferedImage[mapCountX * mapCountY];
		for (int mapY = 0; mapY < mapCountY; mapY++) {
			for (int mapX = 0; mapX < mapCountX; mapX++) {
				imageParts[mapY * mapCountX + mapX] = resizedImage.getSubimage(mapX * 128, mapY * 128, 128, 128);
			}
		}

		return new LoadedImage(imageParts, imageUrl, mapCountX, mapCountY);
	}
}
